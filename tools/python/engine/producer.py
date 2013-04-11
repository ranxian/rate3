#coding:utf8
import shutil
import os
import pika
import pickle
import time
import uuid

ENROLL_BLOCK_SIZE = 50
MATCH_BLOCK_SIZE = 1000

PRODUCER_RATE_ROOT='/Volumes/ratedev-home/RATE_ROOT/'

class RateProducer:
    def __init__(self, host, benchmark_file_dir, result_file_dir, algorithm_version_dir, timelimit, memlimit):
        self.benchmark_file_path = os.path.join(benchmark_file_dir, 'benchmark.txt')
        self.enrollEXE = os.path.join(algorithm_version_dir, 'enroll.exe')
        self.matchEXE = os.path.join(algorithm_version_dir, 'match.exe')
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
        if not os.path.isdir(result_file_dir):
            os.makedirs(result_file_dir)
        self.enroll_result_file = open(os.path.join(result_file_dir, 'enroll_result.txt'), 'w')
        self.match_result_file = open(os.path.join(result_file_dir, 'match_result.txt'), 'w')
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
        os.makedirs(os.path.join(PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:]))
        for i in range(16*16):
            tdir = str(hex(i+256))[-2:]
            os.mkdir(os.path.join(PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:], tdir))
        f = open(self.benchmark_file_path, 'r')
        lines = f.readlines()
        f.close()
        print "benchmark.txt read into memory"
        matches = [ lines[i*3:i*3+3] for i in range(len(lines)/3) ]
        print "benchmark.txt splitted"
        uuid_files = {}
        i = 0
        for match in matches:
            (u1,u2) = match[0].strip().split(' ')[:2]
            if u1 not in uuid_files.keys():
                uuid_files[u1] = match[1].strip()
            if u2 not in uuid_files.keys():
                uuid_files[u2] = match[2].strip()
            i=i+1
            if i%1000==0:
                print "[%d/%d] matches analyzed" % (i, len(matches))
        print "%d matches" % len(matches)
        print "%d enrolls" % len(uuid_files)

        # enroll all
        l = []
        i = 0
        for (u,f) in uuid_files.items():
            t = {'uuid':u, 'file': os.path.join('samples',f) }
            l.append(t)
            if len(l)==ENROLL_BLOCK_SIZE:
                self.submitEnrollBlock(l)
                l = []
                i = i+1
                if i%10 == 0:
                    print "[%d*%d/%d] enrolls has been submitted" % (i, ENROLL_BLOCK_SIZE, len(uuid_files))
        if len(l)!=0:
            self.submitEnrollBlock(l)
            l = []
        self.enroll_uuids = uuid_files.keys()
        uuid_files = None # does python release memory when I do this?

        self.waitForEnrollResults()
        print "enroll finished, failed %d" % len(self.failed_enroll_uuids)

        # match all
        l = []
        i = 0
        for match in matches:
            (u1,u2, gOrI) = match[0].strip().split(' ')[:3]
            if u1 in self.failed_enroll_uuids or u2 in self.failed_enroll_uuids:
                continue
            f1 = 'temp/%s/%s/%s.t' % (self.uuid[-12:], u1[-12:-10], u1[-10:])
            f2 = 'temp/%s/%s/%s.t' % (self.uuid[-12:], u2[-12:-10], u2[-10:])
            t = { 'uuid1':u1, 'uuid2':u2, 'file1':f1, 'file2':f2, 'match_type':gOrI }
            l.append(t)
            if len(l) == MATCH_BLOCK_SIZE:
                self.submitMatchBlock(l)
                l = []
                i = i+1
                if i%10 == 0:
                    print "[%d*%d/%d] matches has been submitted" % (i, MATCH_BLOCK_SIZE, len(matches))
        if len(l)!=0:
            self.submitMatchBlock(l)
            l = []

        if len(self.match_subtask_uuids)!=0:
            self.waitForMatchResults()

#        self.generateResults()

        self.cleanUp()

    def cleanUp(self):
        print "cleaning up"
        try:
            temp_dir = os.path.join(PRODUCER_RATE_ROOT, 'temp', self.uuid[-12:])
            if os.path.exists(temp_dir):
                shutil.rmtree(temp_dir)
        except Exception, e:
            print e

        self.ch.exchange_declare(exchange='jobs-cleanup-exchange', type='fanout')
        cleanup_dir = os.path.join('temp', self.uuid[-12:])
        self.ch.basic_publish(exchange='jobs-cleanup-exchange', routing_key='', body=pickle.dumps(cleanup_dir))

    def submit(self, subtask):
        if subtask==None:
            return
        for fpath in subtask['files']:
            fpath = os.path.join(PRODUCER_RATE_ROOT, fpath)
            if not os.path.exists(fpath):
                raise Exception("file does not exists: %s" % fpath)
        self.ch.queue_declare(queue='jobs')
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
        print "enroll result [%s] subtask finished/total [%d/%d] enroll failed/total [%d/%d]" % (result['subtask_uuid'][:8], len(self.enroll_finished_subtask_uuids), len(self.enroll_subtask_uuids), len(self.failed_enroll_uuids), len(self.enroll_uuids))
        self.enroll_result_file.flush()

    def matchCallBack(self, ch, method, properties, body):
        result = pickle.loads(body)
        self.match_finished_subtask_uuids.append(result['subtask_uuid'])
        print "match result [%s] [%d/%d]" % (result['subtask_uuid'][:8], len(self.match_finished_subtask_uuids), len(self.match_subtask_uuids))
        self.ch.basic_ack(delivery_tag=method.delivery_tag)
        if len(self.match_finished_subtask_uuids)==len(self.match_subtask_uuids):
            self.ch.stop_consuming()
        for rawResult in result['results']:
            if rawResult['result'] == 'ok':
                print>>self.match_result_file, '%s %s %s ok %s' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'], rawResult['score'])
            elif rawResult['result'] == 'failed':
                print>>self.match_result_file, '%s %s %s failed' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'])
        self.match_result_file.flush()

    def waitForEnrollResults(self):
        print 'waiting for enroll results'
        qname = 'results-enroll-%s' % (self.uuid,)
        self.ch.queue_declare(queue=qname)
        self.ch.basic_qos(prefetch_count=1)
        self.ch.basic_consume(self.enrollCallBack, queue=qname)
        self.ch.start_consuming()
        self.ch.queue_delete(queue=qname)

    def waitForMatchResults(self):
        print 'waiting for match results'
        qname = 'results-match-%s' % (self.uuid,)
        self.ch.queue_declare(queue=qname)
        self.ch.basic_qos(prefetch_count=1)
        self.ch.basic_consume(self.matchCallBack, queue=qname)
        self.ch.start_consuming()
        self.ch.queue_delete(queue=qname)

