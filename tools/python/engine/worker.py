import uuid
import shutil
import urllib2
from multiprocessing import Process
import threading
import time
import os
import socket
import pika
from pika.exceptions import AMQPConnectionError
import pickle

SERVER='ratedev-server'
WORKER_NUM=5
WORKER_RATE_ROOT='./RATE_ROOT'
SERVER_RATE_ROOT='ftp://ratedev:Biometrics@%s/RATE_ROOT/' % (SERVER,)

semphore = threading.Semaphore(WORKER_NUM)

class Worker:
    def __init__(self, host='localhost'):
        self.host = host
        self.uuid = uuid.uuid4().__str__()
        self.WORKER_RATE_ROOT = WORKER_RATE_ROOT

#    def __del__(self):
#        self.ch.queue_delete(queue='running-%s'%self.uuid)

    def cleanup(self):
        """
        clean up the directory when a subtask is finished
        """
        pass

    def checkFile(self, relPath):
        tried = 0
        while tried<5:
            try:
                absPath = os.path.join(self.WORKER_RATE_ROOT, relPath)
                if os.path.exists(absPath):
                    return
                if not os.path.isdir(os.path.dirname(absPath)):
                    os.makedirs(os.path.dirname(absPath))
                remotePath = SERVER_RATE_ROOT+relPath
                print '%s: download [%s] to [%s]' % (self.uuid[:8], remotePath, absPath)
                rf = urllib2.urlopen(remotePath)
                lf = open(absPath, 'w')
                shutil.copyfileobj(rf, lf)
                rf.close()
                lf.close()
                break
            except Exception, e:
                print(e)
                tried = tried + 1
                print("retry %d times" % tried)
                if os.path.exists(absPath):
                    os.remove(absPath)

    def prepare(self, subtask):
        print "%s: prepare" % self.uuid[:8]
        for f in subtask['files']:
            self.checkFile(f)

    def doEnroll(self, subtask):
        time.sleep(3)
        result = {}
        result['subtask_uuid'] = subtask['subtask_uuid']
        return result

    def doMatch(self, subtask):
        time.sleep(1)
        result = {}
        result['subtask_uuid'] = subtask['subtask_uuid']
        return result

    def doWork(self, ch, method, properties, body):
        subtask = pickle.loads(body)
        self.prepare(subtask)

        semphore.acquire()

        # do the job
        if subtask['type'] == 'enroll':
            print "%s: enroll" % self.uuid[:8]
            result = self.doEnroll(subtask)
        elif subtask['type'] == 'match':
            print "%s: match" % self.uuid[:8]
            result = self.doMatch(subtask)

        # put result back
        result_queue = 'results-%s-%s' % (subtask['type'], subtask['producer_uuid'])
        self.ch.queue_declare(queue=result_queue)
        self.ch.basic_publish(exchange='', routing_key=result_queue, body=pickle.dumps(result))
        self.ch.basic_ack(delivery_tag=method.delivery_tag)

        semphore.release()

    def start(self):
        while True:
            try:
                self.conn = pika.BlockingConnection(pika.ConnectionParameters(self.host))
                self.ch = self.conn.channel()
                print "[%d] [%s]" % (os.getpid(), self.uuid[:8]), 'queue server connected'
                self.ch.queue_declare(queue = 'jobs')
                self.ch.basic_qos(prefetch_count=1)
                self.ch.basic_consume(self.doWork, queue='jobs')
                self.ch.start_consuming()
            except AMQPConnectionError, e:
                pass
            except socket.error, e:
                print "[",os.getpid(),"]", e

def proc():
    while True:
        try:
            w = Worker('%s' % (SERVER, ))
            w.start()
        except Exception, e:
            print(e)
            pass

if __name__=='__main__':
    ts = []
    for i in range(WORKER_NUM*2):
        t = threading.Thread(target=proc)
        t.start()
        ts.append(t)
    for t in ts:
        t.join()
