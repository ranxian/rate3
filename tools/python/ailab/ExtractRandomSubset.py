import MySQLdb
import os
import shutil

conn_b = MySQLdb.Connect("localhost", "root", "testing", "fingerveindatabase_2010sp-2012sp")
cur_b = conn_b.cursor()
conn_r = MySQLdb.Connect("localhost", "root", "testing", "rate3")
cur_r = conn_r.cursor()

#cur_r.execute('truncate person')
#cur_r.execute('truncate class')
#cur_r.execute('truncate sample')

sql = 'select uuid,name,sex from person_info order by rand()'
cur_b.execute(sql)
class_count=0
total_class_count=2000000
sample_limit = 10000
for person in cur_b.fetchall():
    if class_count>=total_class_count:
        break
    person_uuid = person[0]
    sql = 'select uuid,create_time from class where person_uuid = "%s"' % (person_uuid,)
    cur_b.execute(sql)
    for c in cur_b.fetchall():
        if class_count>=total_class_count:
            break
        sql = 'select count(*) from sample where class_id!=0 and class_uuid = "%s"' % (c[0])
        cur_b.execute(sql)
        count = int(cur_b.fetchone()[0])
            
        #if count<10:
        if False:
            continue
        else:
            #insert person
            sql = ('insert into person (uuid,name,gender) values (unhex(replace("%s","-","")), "%s", "%s")'\
                  % (person_uuid, "", 'MALE' if (person[2]=='M') else 'FEMALE'))            
            try:
                cur_r.execute(sql)
            except Exception,e:
                pass
                #print e            

            #insert class
            sql = 'insert into class (uuid,person_uuid,type,created,import_tag) values (unhex(replace("%s","-","")),unhex(replace("%s","-","")),"FINGERVEIN","%s","20130114test")' \
                  % (c[0], person_uuid, c[1])
            print c[0], class_count
            try:
                class_count = class_count+1
                cur_r.execute(sql)                                
            except Exception,e:
                #print e
                pass

            #insert sample
            sql = 'select uuid,create_time,file from sample where class_id!=0 and class_uuid = "%s" limit %d' % (c[0], sample_limit)
            cur_b.execute(sql)
            for s in cur_b.fetchall():
                file = "bioverify" + "/" + s[2].replace("\\", "/")
                sql = 'insert into sample (uuid,class_uuid,created,file,device_type,import_tag) values (unhex(replace("%s","-","")), unhex(replace("%s","-","")), "%s", "%s", unhex("025081E941D711E286ED00FFF1ECB95C"), "20121209test")' \
                      % (s[0], c[0], s[1], file)
                try:
                    cur_r.execute(sql)
                except Exception, e:
                    #print e
                    pass
    cur_r.execute('commit')
    



###now copy the files
##try:
##    shutil.rmtree('./bioverify')
##except:
##    pass
##sql = 'select file from sample'
##cur_r.execute(sql)
##
##for s in cur_r.fetchall():
##    file = os.path.join(os.path.curdir, s[0])
##    if not os.path.isdir(os.path.dirname(file)):
##        os.makedirs(os.path.dirname(file))
##    ofile = s[0].replace('bioverify', 'G:/FingerVeinImages_Divided_2nd_0.4')
##    if not os.path.exists(ofile):
##        print 'damn'
##    else:
##        shutil.copyfile(ofile, file)
        