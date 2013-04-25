# coding:utf8
import MySQLdb
import hashlib
import os
#TODO 要把学生名字给update过来

conn = MySQLdb.connect('localhost', 'root', 'testing', 'rate3')
c = conn.cursor()

#c.execute('delete from sample where device=unhex("0A66B7775EC711E2A73200247E0F739B"')

#check duplicated files
#c.execute("select file from sample where import_tag='newbioverify' group by file having count(*)>1;")
#
#i = 0
#for f in c.fetchall():
#    sql = 'delete from sample where file = "%s" limit 1'
#    c.execute(sql)

#    sql = 'select hex(uuid) from sample where file="%s"' % f
#    c.execute(sql)
#    for r in c.fetchall()[:-1]:
#        sql = 'delete from sample where uuid = unhex("%s")' % r[0]
#        c.execute(sql)
#    i = i+1
#    print i
#
#c.execute('commit')
#
#exit()

selectsql ='select file, hex(uuid) from sample where import_tag="newbioverify" and (md5 is null or hex(md5) = "00000000000000000000000000000000")'
c.execute('select count(*) from (%s) as dt1' % selectsql)
total = int(c.fetchone()[0])
c.execute(selectsql)

i = 0
for r in c.fetchall():
    print "%d/%d" % (i, total)
    f = r[0]
    u = r[1]
    f = os.path.join("/home/ratedev/RATE_ROOT/samples", f)
    if not os.path.exists(f):
        print "shit: ", f
    else:
        md5 = hashlib.md5(open(f,'rb').read()).hexdigest()
#        print md5
        sql = 'update sample set md5=unhex("%s") where uuid = unhex("%s")' % (md5, u)
        c.execute(sql)
    i = i+1

c.execute('commit')

print "%d sample md5 updated"
print "to check: "
print selectsql
