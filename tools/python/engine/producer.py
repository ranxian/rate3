#coding:utf8
import os
import pika
import pickle
import time
import uuid

ENROLL_BLOCK_SIZE = 20
MATCH_BLOCK_SIZE = 100

PRODUCER_RATE_ROOT='/Volumes/RATE_ROOT/'

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
        f = open(self.benchmark_file_path, 'r')
        lines = f.readlines()
        f.close()
        matches = [ lines[i*3:i*3+3] for i in range(len(lines)/3) ]
        uuid_files = {}
        for match in matches:
            (u1,u2) = match[0].strip().split(' ')[:2]
            if u1 not in uuid_files.keys():
                uuid_files[u1] = match[1].strip()
            if u2 not in uuid_files.keys():
                uuid_files[u2] = match[2].strip()
        print "%d matches" % len(matches)
        print "%d enrolls" % len(uuid_files)

        # enroll all
        l = []
        for (u,f) in uuid_files.items():
            t = {'uuid':u, 'file': os.path.join('samples',f) }
            l.append(t)
            if len(l)==ENROLL_BLOCK_SIZE:
                self.submitEnrollBlock(l)
                l = []
        if len(l)!=0:
            self.submitEnrollBlock(l)
            l = []

        self.waitForEnrollResults()
        print "enroll finished, failed %d" % len(self.failed_enroll_uuids)

        # match all
        l = []
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
        if len(l)!=0:
            self.submitMatchBlock(l)
            l = []

        self.waitForMatchResults()


#        self.generateResults()

#        self.cleanUp()


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
        print "enroll result [%s] [%d/%d]" % (result['subtask_uuid'][:8], len(self.enroll_finished_subtask_uuids), len(self.enroll_subtask_uuids))
        self.ch.basic_ack(delivery_tag=method.delivery_tag)
        if len(self.enroll_finished_subtask_uuids)==len(self.enroll_subtask_uuids):
            self.ch.stop_consuming()
        for rawResult in result['results']:
            print>>self.enroll_result_file, "E %s %s" % (rawResult['uuid'], rawResult['result'])
            if rawResult['result']=='failed':
                self.failed_enroll_uuids.add(rawResult['uuid'])
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
                print>>self.match_result_file, 'M %s %s %s ok %s' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'], rawResult['score'])
            elif rawResult['result'] == 'failed':
                print>>self.match_result_file, 'M %s %s %s failed' % (rawResult['uuid1'], rawResult['uuid2'], rawResult['match_type'])
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

