import uuid
import ftplib
import subprocess
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

#TODO when ftp download failed
#TODO maybe we should use multilevel dirs for templates

SERVER='ratedev-server'
WORKER_NUM=4
#WORKER_RATE_ROOT='%s/RATE_ROOT' % (os.path.dirname(os.path.abspath(__file__)),)
WORKER_RATE_ROOT='./RATE_ROOT'
#print WORKER_RATE_ROOT

file_lock = threading.Lock()
dir_lock = threading.Lock()
semphore = threading.Semaphore(WORKER_NUM)

class Worker:
    def __init__(self, host='localhost'):
        self.host = host
        self.uuid = uuid.uuid4().__str__()
        self.WORKER_RATE_ROOT = WORKER_RATE_ROOT
        self.download_ftp = None

#    def __del__(self):
#        self.ch.queue_delete(queue='running-%s'%self.uuid)

    def cleanup(self):
        """
        clean up the directory when a subtask is finished
        """
        pass

    def checkDir(self, dirPath):
        dir_lock.acquire()
        try:
            if not os.path.isdir(dirPath):
                os.makedirs(dirPath)
        except exceptions.OSError, e:
            print e
        dir_lock.release()

    def checkFile(self, relPath):
        tried = 0
        while tried<5:
            absPath = os.path.join(self.WORKER_RATE_ROOT, relPath)
            if os.path.exists(absPath):
                break
            file_lock.acquire()
            if os.path.exists(absPath):
                file_lock.release()
                break
            try:
                self.checkDir(os.path.dirname(absPath))
                print '%s: download [%s] to [%s]' % (self.uuid[:8], relPath, absPath)
                lf = open(absPath, 'wb')
                self.openFTP() # open ftp only if we need it, and close it when self.prepare() is done
                self.download_ftp.retrbinary("RETR " + relPath, lf.write)
                lf.close()
            except Exception, e:
                print(e)
                tried = tried + 1
                print("retry %d times" % tried)
                if os.path.exists(absPath):
                    os.remove(absPath)
            file_lock.release()

    def prepare(self, subtask):
        print "%s: prepare" % self.uuid[:8]
        for f in subtask['files']:
            self.checkFile(f)
        self.closeFTP()

    def doEnroll(self, subtask):
        enrollEXE = os.path.join(WORKER_RATE_ROOT, subtask['enrollEXE'])
        rawResults = []
        ftp = ftplib.FTP('ratedev-server', 'ratedev', 'Biometrics')
        try:
            ftpdir = 'RATE_ROOT/temp/%s/' % subtask['producer_uuid'][-12:]
            ftp.cwd(ftpdir)
        except ftplib.error_perm, e:
            if e.message.startswith('550'):
                ftp.mkd(ftpdir)
                ftp.cwd(ftpdir)

        for tinytask in subtask['tinytasks']:
            u = tinytask['uuid']
            rawResult = { 'uuid': u,'result':'failed' }
            f = tinytask['file']
            absImagePath = os.path.join(WORKER_RATE_ROOT, f)
            absTemplatePath = os.path.join(WORKER_RATE_ROOT,'temp',subtask['producer_uuid'][-12:],u[-12:-10], "%s.t" % u[-10:])
            self.checkDir(os.path.dirname(absTemplatePath))
            cmd = "%s %s %s" % (enrollEXE, absImagePath, absTemplatePath)
#            cmdlogfile = open('./cmd.log', 'a')
#            print>>cmdlogfile, cmd
#            cmdlogfile.close()
            try:
                p = subprocess.Popen(cmd.split(' '))
                returncode = p.wait()
#                print returncode
                if returncode == 0 and os.path.exists(absTemplatePath):
                    template_file = open(absTemplatePath, 'rb')
                    try:
                        ftp.mkd(u[-12:-10])
                    except:
                        pass
                    ftp.storbinary('STOR ' + "%s/%s.t" % (u[-12:-10], u[-10:]), template_file)
                    template_file.close()
                    rawResult['result'] = 'ok'
            except Exception, e:
                print e
                rawResult['result'] = 'failed'
            rawResults.append(rawResult)
        result = {}
        result['results'] = rawResults
        return result

    def doMatch(self, subtask):
        rawResults = []
        matchEXE = os.path.join(WORKER_RATE_ROOT, subtask['matchEXE'])
        for tinytask in subtask['tinytasks']:
            u1 = tinytask['uuid1']
            u2 = tinytask['uuid2']
            f1 = tinytask['file1']
            f2 = tinytask['file2']
            rawResult = {}
            rawResult['uuid1'] = u1
            rawResult['uuid2'] = u2
            rawResult['match_type'] = tinytask['match_type']
            rawResult['result'] = 'failed'
            cmd = "%s %s %s" % (matchEXE, f1, f2)
            cmdlogfile = open('./cmd.log', 'a')
            print>>cmdlogfile, cmd
            cmdlogfile.close()
            try:
                p = subprocess.Popen(cmd.split(' '), stdout=subprocess.PIPE)
                returncode = p.wait()
                if returncode != 0:
                    rawResult['result'] = 'failed'
                    print 'returncode: %d' % p.returncode
                else:
                    for line in p.stdout:
                        print line
                    score = float(p.stdout[0].strip())
                    rawResult['result'] = 'ok'
                    rawResult['score'] = score
            except Exception, e:
                print e
                rawResult['result'] = 'failed'
            rawResults.append(rawResult)


        result = {}
        result['results'] = rawResults
        return result

    def openFTP(self):
        if not self.download_ftp:
            self.download_ftp = ftplib.FTP('ratedev-server', 'ratedev', 'Biometrics')
            self.download_ftp.cwd('RATE_ROOT')

    def closeFTP(self):
        if self.download_ftp:
            self.download_ftp.quit()
            self.download_ftp = None

    def doWork(self, ch, method, properties, body):
        subtask = pickle.loads(body)
        self.prepare(subtask)

        try:
            semphore.acquire()
            # do the job
            if subtask['type'] == 'enroll':
                print "%s: enroll begin" % self.uuid[:8]
                result = self.doEnroll(subtask)
                print "%s: enroll finished" % self.uuid[:8]
            elif subtask['type'] == 'match':
                print "%s: match" % self.uuid[:8]
                result = self.doMatch(subtask)

            # put result back
            result['subtask_uuid'] = subtask['subtask_uuid']
            result['type'] = subtask['type']
            result_queue = 'results-%s-%s' % (subtask['type'], subtask['producer_uuid'])
            self.ch.queue_declare(queue=result_queue)
            self.ch.basic_publish(exchange='', routing_key=result_queue, body=pickle.dumps(result))
            self.ch.basic_ack(delivery_tag=method.delivery_tag)

        except Exception, e:
            print e
        finally:
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
                print e
                pass
            except socket.error, e:
                print "[",os.getpid(),"]", e

def proc():
    while True:
        try:
            w = Worker('%s' % (SERVER, ))
            w.start()
        except Exception, e:
            pass

if __name__=='__main__':
    ts = []
    for i in range(WORKER_NUM*2):
        t = threading.Thread(target=proc)
        t.start()
        ts.append(t)
    for t in ts:
        t.join()
