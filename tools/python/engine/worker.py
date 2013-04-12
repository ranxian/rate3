import uuid
import traceback
import time
import ftplib
import subprocess
import shutil
import urllib2
import multiprocessing
from multiprocessing import Process
import threading
import time
import os
import socket
import pika
from pika.exceptions import AMQPConnectionError
import pickle

SERVER='ratedev-server'
WORKER_NUM=8
WORKER_RATE_ROOT=os.path.join('.', 'RATE_ROOT')

class Worker:
    def __init__(self, host, file_lock, dir_lock, ftp_mkd_lock, clean_lock, semaphore, process_lock, CURRENT_WORKER_NUM):
        self.host = host

        self.file_lock = file_lock
        self.dir_lock = dir_lock
        self.ftp_mkd_lock = ftp_mkd_lock
        self.clean_lock = clean_lock
        self.semaphore = semaphore
        self.process_lock = process_lock
        self.CURRENT_WORKER_NUM = CURRENT_WORKER_NUM

        #self.uuid = uuid.uuid4().__str__()
        self.WORKER_RATE_ROOT = WORKER_RATE_ROOT
        self.download_ftp = None

        process_lock.acquire()
        self.worker_num = CURRENT_WORKER_NUM.value
        CURRENT_WORKER_NUM.value = CURRENT_WORKER_NUM.value + 1
        process_lock.release()

#    def __del__(self):
#        self.ch.queue_delete(queue='running-%s'%self.uuid)

    def cleanup(self):
        """
        clean up the directory when a subtask is finished
        """
        pass

    def checkDir(self, dirPath):
        self.dir_lock.acquire()
        try:
            if not os.path.isdir(dirPath):
                os.makedirs(dirPath)
        except exceptions.OSError, e:
            print e
        self.dir_lock.release()

    def checkFile(self, relPath):
        tried = 0
        while tried<5:
            absPath = os.path.join(self.WORKER_RATE_ROOT, relPath)
            if os.path.exists(absPath):
                break
            self.file_lock.acquire()
            if os.path.exists(absPath):
                self.file_lock.release()
                break
            try:
                self.checkDir(os.path.dirname(absPath))
                #print '%s: download [%s]' % (self.uuid[:8], relPath)
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
            self.file_lock.release()

    def prepare(self, subtask):
        print "%s: prepare" % str(self.worker_num)
        for f in subtask['files']:
            self.checkFile(f)
        self.closeFTP()
        print "%s: prepare finished" % str(self.worker_num)

    def doEnroll(self, subtask):
        enrollEXE = os.path.join(WORKER_RATE_ROOT, subtask['enrollEXE'])
        timelimit = subtask['timelimit']
        memlimit = subtask['memlimit']
        rawResults = []
        ftp = ftplib.FTP('ratedev-server', 'ratedev', 'Biometrics')
        ftpdir = 'RATE_ROOT/temp/%s/' % subtask['producer_uuid'][-12:]
        try:
            ftp.cwd(ftpdir)
        except ftplib.error_perm, e:
            if e.message.startswith('550'):
                try:
                    #self.ftp_mkd_lock.acquire()
                    ftp.cwd(ftpdir)
                except ftplib.error_perm, e2:
                    #ftp.mkd(ftpdir)
                    ftp.cwd(ftpdir)
                finally:
                    #self.ftp_mkd_lock.release()
                    pass

        for tinytask in subtask['tinytasks']:
            u = tinytask['uuid']
            rawResult = { 'uuid': u,'result':'failed' }
            f = tinytask['file']
            absImagePath = os.path.join(WORKER_RATE_ROOT, f).replace('/', os.path.sep)
            absTemplatePath = os.path.join(WORKER_RATE_ROOT,'temp',subtask['producer_uuid'][-12:],u[-12:-10], "%s.t" % u[-10:]).replace('/', os.path.sep)
            self.checkDir(os.path.dirname(absTemplatePath))
            cmd = '.\\rate_run.exe %s %s %s %s %s' % (str(timelimit), str(memlimit), enrollEXE, absImagePath, absTemplatePath)
            cmdlogfile = open('./enrollcmd.log', 'a')
            print>>cmdlogfile, cmd
            cmdlogfile.close()
            try:
                p = subprocess.Popen(cmd.split(' '), stdout=subprocess.PIPE, stderr=open(os.devnull, "w"))
                returncode = p.wait()
#                print returncode
                if returncode == 0 and os.path.exists(absTemplatePath):
                    template_file = open(absTemplatePath, 'rb')
                    tried = 0
                    while tried<5:
                        try:
                            pass
                            #ftp.mkd(u[-12:-10])
                        except:
                            pass
                        try:
                            ftp.storbinary('STOR ' + "%s/%s.t" % (u[-12:-10], u[-10:]), template_file)
                            break
                        except Exception, e:
                            print e
                            traceback.print_exc()
                            tried = tried + 1
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
        timelimit = subtask['timelimit']
        memlimit = subtask['memlimit']
        matchEXE = os.path.join(WORKER_RATE_ROOT, subtask['matchEXE']).replace('/', os.path.sep)
        for tinytask in subtask['tinytasks']:
            u1 = tinytask['uuid1']
            u2 = tinytask['uuid2']
            f1 = tinytask['file1'].replace('/', os.path.sep)
            f2 = tinytask['file2'].replace('/', os.path.sep)
            f1 = os.path.join(WORKER_RATE_ROOT, f1)
            f2 = os.path.join(WORKER_RATE_ROOT, f2)
            rawResult = {}
            rawResult['uuid1'] = u1
            rawResult['uuid2'] = u2
            rawResult['match_type'] = tinytask['match_type']
            rawResult['result'] = 'failed'
            cmd = '.\\rate_run.exe %s %s %s %s %s' % (str(timelimit), str(memlimit), matchEXE, f1, f2)
            cmdlogfile = open('./matchcmd.log', 'a')
            print>>cmdlogfile, cmd
            cmdlogfile.close()
            try:
                p = subprocess.Popen(cmd.split(' '), stdout=subprocess.PIPE, stderr=open(os.devnull, "w"))
                returncode = p.wait()
                if returncode != 0:
                    rawResult['result'] = 'failed'
                    print 'returncode: %d' % p.returncode
                else:
                    score = p.stdout.readline().strip()
#                    print "score string: %s" % score
                    score = float(score)
#                    print "score float: %f" % score
                    rawResult['result'] = 'ok'
                    rawResult['score'] = str(score)
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
            self.semaphore.acquire()
            # do the job
            if subtask['type'] == 'enroll':
                print "%s: enroll begin" % str(self.worker_num)
                result = self.doEnroll(subtask)
                print "%s: enroll finished" % str(self.worker_num)
            elif subtask['type'] == 'match':
                print "%s: match begin" % str(self.worker_num)
                result = self.doMatch(subtask)
                print "%s: match finished" % str(self.worker_num)

            # put result back
            result['subtask_uuid'] = subtask['subtask_uuid']
            result['type'] = subtask['type']
            result_queue = 'results-%s-%s' % (subtask['type'], subtask['producer_uuid'])
            #print 'result_queue:', result_queue
            self.ch.queue_declare(queue=result_queue)
            self.ch.basic_publish(exchange='', routing_key=result_queue, body=pickle.dumps(result))
            self.ch.basic_ack(delivery_tag=method.delivery_tag)

        except Exception, e:
            traceback.print_exc()
            raise
        finally:
            self.semaphore.release()

    def doClean(self, ch, method, properties, body):
        cleanpath = pickle.loads(body)
        cleanpath = os.path.join(WORKER_RATE_ROOT, cleanpath)
        if not os.path.exists(cleanpath):
            return

        try:
            self.clean_lock.acquire()
            if not os.path.exists(cleanpath):
                return
            print "%s: clean: %s" % (str(self.worker_num), cleanpath)
            shutil.rmtree(cleanpath)
        except Exception, e:
            print e
        finally:
            self.clean_lock.release()

    def start(self):
        while True:
            try:
                self.conn = pika.BlockingConnection(pika.ConnectionParameters(self.host))
                self.ch = self.conn.channel()
                print "[%d] [%s]" % (os.getpid(), str(self.worker_num)), 'queue server connected'
                self.ch.basic_qos(prefetch_count=1)

                self.ch.exchange_declare(exchange='jobs-cleanup-exchange', type='fanout')
                my_cleanup_queue_name = self.ch.queue_declare(exclusive=True).method.queue
                self.ch.queue_bind(exchange='jobs-cleanup-exchange', queue=my_cleanup_queue_name)
                self.ch.basic_consume(self.doClean, queue=my_cleanup_queue_name, no_ack=True)

                self.ch.queue_declare(queue = 'jobs')
                self.ch.basic_consume(self.doWork, queue='jobs')

                self.ch.start_consuming()
            except AMQPConnectionError, e:
                print 'AMQPConnectionError: ', e
                pass
            except socket.error, e:
                print "socket error: [", os.getpid(),"]", e
            time.sleep(1)

def proc(file_lock, dir_lock, ftp_mkd_lock, clean_lock, semaphore, process_lock, CURRENT_WORKER_NUM):
    while True:
        try:
            w = Worker('%s' % (SERVER, ), file_lock, dir_lock, ftp_mkd_lock, clean_lock, semaphore, process_lock, CURRENT_WORKER_NUM)
            w.start()
        except Exception, e:
            print e
            pass

if __name__=='__main__':
    multiprocessing.freeze_support()

    file_lock = multiprocessing.Lock()
    dir_lock = multiprocessing.Lock()
    ftp_mkd_lock = multiprocessing.Lock()
    clean_lock = multiprocessing.Lock()
    semaphore = multiprocessing.Semaphore(WORKER_NUM)
    process_lock = multiprocessing.Lock()
    CURRENT_WORKER_NUM=multiprocessing.Value("i")
    CURRENT_WORKER_NUM.value = 1

    process_args = []
    process_args.append(file_lock)
    process_args.append(dir_lock)
    process_args.append(ftp_mkd_lock)
    process_args.append(clean_lock)
    process_args.append(semaphore)
    process_args.append(process_lock)
    process_args.append(CURRENT_WORKER_NUM)

    ts = []
    for i in range(WORKER_NUM*3):
        #t = threading.Thread(target=proc)
        t = Process(target=proc, args=process_args)
        t.start()
        ts.append(t)
    for t in ts:
        t.join()
