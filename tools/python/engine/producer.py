#coding:utf8
import logging
logging.basicConfig()
from pika.adapters.tornado_connection import TornadoConnection
import shutil
import threading
import os
import pika
import pickle
import time
import uuid
import ConfigParser

config = ConfigParser.ConfigParser()
config.readfp(open('%s/producer.conf' % os.path.dirname(__file__), 'r'))

ENROLL_BLOCK_SIZE = config.getint('rate-server', 'ENROLL_BLOCK_SIZE')
MATCH_BLOCK_SIZE = config.getint('rate-server', 'MATCH_BLOCK_SIZE')
PRODUCER_RATE_ROOT = config.get('rate-server', 'PRODUCER_RATE_ROOT')
MAX_WORKING_ENROLL_SUBTASKS = 100000
MAX_WORKING_MATCH_SUBTASKS = 100000

class RateProducer:
    def __init__(self, host, benchmark_file_dir, result_file_dir, algorithm_version_dir, timelimit, memlimit):
        self.host = host
        self.benchmark_file_path = "/".join((benchmark_file_dir, 'benchmark.txt'))
        self.enrollEXE = "/".join((algorithm_version_dir, 'enroll.exe'))
        self.matchEXE = "/".join((algorithm_version_dir, 'match.exe'))
        self.timelimit = timelimit
        self.memlimit = memlimit

#        self.conn = TornadoConnection(pika.ConnectionParameters(host))
        self.conn = pika.BlockingConnection(pika.ConnectionParameters(self.host))
        self.ch = self.conn.channel()
        print "queue server connected"
        self.results = {}
        self.uuid = uuid.uuid4().__str__()
        self.enroll_subtask_uuids = []
        self.match_subtask_uuids = []
        self.finished_enroll_subtask_uuids = []
        self.finished_match_subtask_uuids = []
        self.result_file_dir = result_file_dir
        if not os.path.isdir(result_file_dir):
            os.makedirs(result_file_dir)
        self.enroll_result_file = open("/".join((result_file_dir, 'enroll_result.txt')), 'w')
        self.match_result_file = open("/".join((result_file_dir, 'match_result.txt')), 'w')
        self.finished_enroll_uuids = set()
#        self.finished_match_uuids = set()
        self.failed_enroll_uuids = set()
        self.failed_match_uuids = set()
        self.submitting_enroll = False
        self.submitting_match = False
        self.enroll_lock = threading.Lock() # should be locked whether submitting or receiving result
        self.match_lock = threading.Lock()
        self.enroll_result_qname = 'results-enroll-%s' % (self.uuid,)
        self.match_result_qname = 'results-match-%s' % (self.uuid,)
        self.enroll_uuids = set()

    def submitEnrollBlock(self, l):
        subtask = self.genSubtask(l, 'enroll')
        files = []
        files.append(self.enrollEXE)
        files.append(self.matchEXE)
        for i in l:
            files.append(i['file'])
        subtask['files'] = files
        self.enroll_subtask_uuids.append(subtask['subtask_uuid'])
        self.submit(subtask)

    def submitMatchBlock(self, l):
        subtask = self.genSubtask(l, 'match')
        files = []
        files.append(self.enrollEXE)
        files.append(self.matchEXE)
        for i in l:
            files.append(i['file1'])
            files.append(i['file2'])
        subtask['files'] = files
        self.match_subtask_uuids.append(subtask['subtask_uuid'])
        self.submit(subtask)

    def genSubtask(self, tinytasks, taskType):
        subtask = {
                'timelimit'     : self.timelimit,
                'memlimit'      : self.memlimit,
                'tinytasks'     : tinytasks,
                'producer_uuid' : self.uuid,
                'enrollEXE'     : self.enrollEXE,
                'matchEXE'      : self.matchEXE,
                'type'          : taskType,
               }
        subtask_uuid = uuid.uuid4().__str__()
        subtask['subtask_uuid'] = subtask_uuid
        return subtask

    def prepare(self):
        print "preparing queues"
        self.ch.queue_declare(queue='jobs', durable=False, exclusive=False, auto_delete=False)
        # prepare dirs
        print "preparing dirs on server"
        os.makedirs("/".join((PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:])))
        for i in range(16*16):
            tdir = str(hex(i+256))[-2:]
            os.mkdir("/".join((PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:], tdir)))
        print "prepare finished"

    def doEnroll(self):
        self.submitting_enroll = True
        i = 0 # i j is for counting in case to print messages with i%xxx=0
        j = 0
        l = []
        benchmarkf = open(self.benchmark_file_path, 'r')
        self.count_of_matches = 0
        with self.enroll_lock:
            enroll_result_thread = threading.Thread(target=self.waitForEnrollResults)
            enroll_result_thread.start()
        wait = False
        while True:
            if wait == True:
                time.sleep(5)
                wait = False
            with self.enroll_lock:
                working_enroll_subtasks = len(self.enroll_subtask_uuids)-len(self.finished_enroll_subtask_uuids)
                if working_enroll_subtasks >= MAX_WORKING_ENROLL_SUBTASKS:
                    print "%d enroll queue full, wait for 5 sec" % (working_enroll_subtasks, )
                    wait = True
                    continue
                a = benchmarkf.readline().strip()
                if len(a)==0:
                    break
                self.count_of_matches += 1
                b = benchmarkf.readline().strip()
                c = benchmarkf.readline().strip()
                match = [a,b,c]
                us = match[0].strip().split(' ')[:2]
                for u in us:
                    if u not in self.enroll_uuids:
                        self.enroll_uuids.add(u)
                        f = "/".join(('samples', match[us.index(u)+1].strip()))
                        t = {'uuid':u, 'file': f }
                        #print>>enroll_log_file, u, f
                        l.append(t)
                        if len(l)==ENROLL_BLOCK_SIZE:
                            self.submitEnrollBlock(l)
                            l = []
                            j = j+1
                            if j%10 == 0:
                                print "[%d*%d=%d] enrolls has been submitted" % (j, ENROLL_BLOCK_SIZE, j * ENROLL_BLOCK_SIZE)
                i=i+1
                if i%1000==0:
                    print "[%d] matches analyzed" % (i)

        with self.enroll_lock:
            if len(l)!=0:
                self.submitEnrollBlock(l)
                l = []
                benchmarkf.close()
                #enroll_log_file.close()
            if len(self.finished_enroll_subtask_uuids)==len(self.enroll_subtask_uuids):
                print "enroll workers finished before producer reach this line"
                self.enroll_result_ch.stop_consuming()
            self.submitting_enroll = False

        print "%d matches" % self.count_of_matches
        print "%d enrolls" % len(self.enroll_uuids)
        print "all enrolls submitted, waiting for all results"
        enroll_result_thread.join()
        print "enroll finished, failed %d" % len(self.failed_enroll_uuids)

    def doMatch(self):
        self.submitting_match = True
        l = []
        i = 0
        self.submitted_match_count = 0
        self.failed_match_count = 0
        benchmarkf = open(self.benchmark_file_path, 'r')
        with self.match_lock:
            match_result_thread = threading.Thread(target=self.waitForMatchResults)
            match_result_thread.start()
        wait = False
        while True:
            if wait == True:
                time.sleep(5)
                wait = False
            with self.match_lock:
                working_match_subtasks = len(self.match_subtask_uuids)-len(self.finished_match_subtask_uuids)
                if working_match_subtasks >= MAX_WORKING_MATCH_SUBTASKS:
                    print "%d match queue full, wait for 5 sec" % (working_match_subtasks, )
                    wait = True
                    continue

                a = benchmarkf.readline().strip()
                if len(a)==0:
                    break
                b = benchmarkf.readline().strip()
                c = benchmarkf.readline().strip()
                match = [a,b,c]
                (u1,u2, gOrI) = match[0].strip().split(' ')[:3]
                if u1 in self.failed_enroll_uuids or u2 in self.failed_enroll_uuids:
                    continue
                f1 = 'temp/%s/%s/%s.t' % (self.uuid[-12:], u1[-12:-10], u1[-10:])
                f2 = 'temp/%s/%s/%s.t' % (self.uuid[-12:], u2[-12:-10], u2[-10:])
                t = { 'uuid1':u1, 'uuid2':u2, 'file1':f1, 'file2':f2, 'match_type':gOrI }
                l.append(t)
                self.submitted_match_count += 1
                if len(l) == MATCH_BLOCK_SIZE:
                    self.submitMatchBlock(l)
                    l = []
                    i = i+1
                    if i%10 == 0:
                        print "[%d*%d/%d=%d%%] matches has been submitted" % (i, MATCH_BLOCK_SIZE, self.count_of_matches, i*MATCH_BLOCK_SIZE*100/self.count_of_matches)

        with self.match_lock:
            if len(l)!=0:
                self.submitMatchBlock(l)
                l = []
            if len(self.match_subtask_uuids) == len(self.finished_match_subtask_uuids):
                print "match workers finished before producer reach this line"
                self.match_result_ch.stop_consuming()
            self.submitting_match = False
        benchmarkf.close()

        print "%d matches" % self.count_of_matches
        print "all matches submitted, waiting for all results"
        match_result_thread.join()
        print "match finished, failed %d" % self.failed_match_count

    def solve(self):
        self.prepare()
        self.doEnroll()
        self.doMatch()
#        self.generateResults()
        self.cleanUp()

    def cleanUp(self):
        print "cleaning up"
        try:
            temp_dir = "/".join((PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:]))
            if os.path.exists(temp_dir):
                shutil.rmtree(temp_dir)
        except Exception, e:
            print e

        self.ch.exchange_declare(exchange='jobs-cleanup-exchange', type='fanout')
        cleanup_dir = "/".join(('temp', self.uuid[-12:]))
        self.ch.basic_publish(exchange='jobs-cleanup-exchange', routing_key='', body=pickle.dumps(cleanup_dir))

    def submit(self, subtask):
        if subtask==None:
            return
        for fpath in subtask['files']:
            fpath = "/".join((PRODUCER_RATE_ROOT, fpath))
            if not os.path.exists(fpath):
                raise Exception("file does not exists: %s" % fpath)
        self.ch.basic_publish(exchange='', routing_key='jobs', body=pickle.dumps(subtask))

    def enrollCallBack(self, ch, method, properties, body):
#        print "enrollCallBack called"
        with self.enroll_lock:
#            print "enrollCallBack runs"
            result = pickle.loads(body)
            self.finished_enroll_subtask_uuids.append(result['subtask_uuid'])
            ch.basic_ack(delivery_tag=method.delivery_tag)
            for rawResult in result['results']:
                self.finished_enroll_uuids.add(rawResult['uuid'])
                print>>self.enroll_result_file, "%s %s" % (rawResult['uuid'], rawResult['result'])
                if rawResult['result']=='failed':
                    self.failed_enroll_uuids.add(rawResult['uuid'])
            print "enroll result [%s] finished [%d/%d] failed/total [%d/%d]" % (result['subtask_uuid'][:8], len(self.finished_enroll_subtask_uuids), len(self.enroll_subtask_uuids), len(self.failed_enroll_uuids), len(self.enroll_uuids))
            self.enroll_result_file.flush()
            if (not self.submitting_enroll) and len(self.finished_enroll_subtask_uuids)==len(self.enroll_subtask_uuids):
#                print "enrollCallBack stop consuming"
                ch.stop_consuming()

    def matchCallBack(self, ch, method, properties, body):
        with self.match_lock:
            result = pickle.loads(body)
            self.finished_match_subtask_uuids.append(result['subtask_uuid'])
            ch.basic_ack(delivery_tag=method.delivery_tag)
            for rawResult in result['results']:
                if rawResult['result'] == 'ok':
                    print>>self.match_result_file, '%s %s %s ok %s' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'], rawResult['score'])
                elif rawResult['result'] == 'failed':
                    print>>self.match_result_file, '%s %s %s failed' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'])
                    self.failed_match_count += 1
            print "match result [%s] finished [%d/%d] failed [%d/%d]" % (result['subtask_uuid'][:8], len(self.finished_match_subtask_uuids), len(self.match_subtask_uuids), self.failed_match_count, self.submitted_match_count)
            self.match_result_file.flush()
            if (not self.submitting_match) and len(self.finished_match_subtask_uuids)==len(self.match_subtask_uuids):
                ch.stop_consuming()

    def waitForEnrollResults(self):
        print 'waiting for enroll results'
        conn = pika.BlockingConnection(pika.ConnectionParameters(self.host))
        ch = conn.channel()
        self.enroll_result_ch = ch
        ch.queue_declare(queue=self.enroll_result_qname, durable=False, exclusive=False, auto_delete=False)
        ch.basic_consume(self.enrollCallBack, queue=self.enroll_result_qname)
        ch.start_consuming()
        ch.queue_delete(queue=self.enroll_result_qname)
#        conn.close()

    def waitForMatchResults(self):
        print 'waiting for match results'
        conn = pika.BlockingConnection(pika.ConnectionParameters(self.host))
        ch = conn.channel()
        self.match_result_ch = ch
        ch.queue_declare(queue=self.match_result_qname, durable=False, exclusive=False, auto_delete=False)
        ch.basic_consume(self.matchCallBack, queue=self.match_result_qname)
        ch.start_consuming()
        ch.queue_delete(queue=self.match_result_qname)
#        conn.close()
