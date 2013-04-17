#coding:utf8
import shutil
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

class RateProducer:
    def __init__(self, host, benchmark_file_dir, result_file_dir, algorithm_version_dir, timelimit, memlimit):
        self.benchmark_file_path = "/".join((benchmark_file_dir, 'benchmark.txt'))
        self.enrollEXE = "/".join((algorithm_version_dir, 'enroll.exe'))
        self.matchEXE = "/".join((algorithm_version_dir, 'match.exe'))
        self.timelimit = timelimit
        self.memlimit = memlimit

        self.conn = pika.BlockingConnection(pika.ConnectionParameters(host))
        self.ch = self.conn.channel()
        print "queue server connected"
        self.results = {}
        self.uuid = uuid.uuid4().__str__()
        self.enroll_subtask_uuids = []
        self.match_subtask_uuids = []
        self.enroll_finished_subtask_uuids = []
        self.match_finished_subtask_uuids = []
        self.result_file_dir = result_file_dir
        if not os.path.isdir(result_file_dir):
            os.makedirs(result_file_dir)
        self.enroll_result_file = open("/".join((result_file_dir, 'enroll_result.txt')), 'w')
        self.match_result_file = open("/".join((result_file_dir, 'match_result.txt')), 'w')
        self.failed_enroll_uuids = set()
        self.failed_match_uuids = set()

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

    def solve(self):
        # prepare dirs
        print "preparing dirs on server"
        os.makedirs("/".join((PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:])))
        for i in range(16*16):
            tdir = str(hex(i+256))[-2:]
            os.mkdir("/".join((PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:], tdir)))
        print "reading benchmark.txt"
#        lines = f.readlines()
#        f.close()
#        print "benchmark.txt read into memory"
#        matches = [ lines[i*3:i*3+3] for i in range(len(lines)/3) ]
#        print "benchmark.txt splitted"

        self.enroll_uuids = set()
        i = 0
        j = 0
        l = []
        #enroll_log_file = open(os.path.join(self.result_file_dir, 'benchmark.enroll.log'), 'w')
        benchmarkf = open(self.benchmark_file_path, 'r')
        self.countOfMatches = 0
        while True:
            a = benchmarkf.readline().strip()
            if len(a)==0:
                break
            self.countOfMatches += 1
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
        benchmarkf.close()
        #enroll_log_file.close()

        if len(l)!=0:
            self.submitEnrollBlock(l)
            l = []

        print "%d matches" % self.countOfMatches
        print "%d enrolls" % len(self.enroll_uuids)

        self.waitForEnrollResults()
        print "enroll finished, failed %d" % len(self.failed_enroll_uuids)

        # match all
        l = []
        i = 0
        self.match_count = 0
        self.failed_match_count = 0
        benchmarkf = open(self.benchmark_file_path, 'r')
        while True:
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
            self.match_count += 1
            if len(l) == MATCH_BLOCK_SIZE:
                self.submitMatchBlock(l)
                l = []
                i = i+1
                if i%10 == 0:
                    print "[%d*%d/%d=%d%%] matches has been submitted" % (i, MATCH_BLOCK_SIZE, self.countOfMatches, i*MATCH_BLOCK_SIZE*100/self.countOfMatches)
        if len(l)!=0:
            self.submitMatchBlock(l)
            l = []
        benchmarkf.close()

        if len(self.match_subtask_uuids)!=0:
            self.waitForMatchResults()

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
        self.ch.queue_declare(queue='jobs', durable=False, exclusive=False, auto_delete=False)
        self.ch.basic_publish(exchange='', routing_key='jobs', body=pickle.dumps(subtask))

    def enrollCallBack(self, ch, method, properties, body):
        result = pickle.loads(body)
        self.enroll_finished_subtask_uuids.append(result['subtask_uuid'])
        self.ch.basic_ack(delivery_tag=method.delivery_tag)
        if len(self.enroll_finished_subtask_uuids)==len(self.enroll_subtask_uuids):
            self.ch.stop_consuming()
        for rawResult in result['results']:
            print>>self.enroll_result_file, "%s %s" % (rawResult['uuid'], rawResult['result'])
            if rawResult['result']=='failed':
                self.failed_enroll_uuids.add(rawResult['uuid'])
        print "enroll result [%s] finished [%d/%d] failed/total [%d/%d]" % (result['subtask_uuid'][:8], len(self.enroll_finished_subtask_uuids), len(self.enroll_subtask_uuids), len(self.failed_enroll_uuids), len(self.enroll_uuids))
        self.enroll_result_file.flush()

    def matchCallBack(self, ch, method, properties, body):
        result = pickle.loads(body)
        self.match_finished_subtask_uuids.append(result['subtask_uuid'])
        self.ch.basic_ack(delivery_tag=method.delivery_tag)
        if len(self.match_finished_subtask_uuids)==len(self.match_subtask_uuids):
            self.ch.stop_consuming()
        for rawResult in result['results']:
            if rawResult['result'] == 'ok':
                print>>self.match_result_file, '%s %s %s ok %s' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'], rawResult['score'])
            elif rawResult['result'] == 'failed':
                print>>self.match_result_file, '%s %s %s failed' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'])
                self.failed_match_count += 1
        print "match result [%s] finished [%d/%d] failed [%d/%d]" % (result['subtask_uuid'][:8], len(self.match_finished_subtask_uuids), len(self.match_subtask_uuids), self.failed_match_count, self.match_count)
        self.match_result_file.flush()

    def waitForEnrollResults(self):
        print 'waiting for enroll results'
        qname = 'results-enroll-%s' % (self.uuid,)
        self.ch.queue_declare(queue=qname, durable=False, exclusive=False, auto_delete=False)
        self.ch.basic_qos(prefetch_count=1)
        self.ch.basic_consume(self.enrollCallBack, queue=qname)
        self.ch.start_consuming()
        self.ch.queue_delete(queue=qname)

    def waitForMatchResults(self):
        print 'waiting for match results'
        qname = 'results-match-%s' % (self.uuid,)
        self.ch.queue_declare(queue=qname, durable=False, exclusive=False, auto_delete=False)
        self.ch.basic_qos(prefetch_count=1)
        self.ch.basic_consume(self.matchCallBack, queue=qname)
        self.ch.start_consuming()
        self.ch.queue_delete(queue=qname)

