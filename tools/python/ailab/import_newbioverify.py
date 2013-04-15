import MySQLdb
import hashlib
import os

conn = MySQLdb.connect('localhost', 'root', 'testing', 'rate3')
c = conn.cursor()

#check duplicated files
c.execute("select file from sample where import_tag='newbioverify' group by file having count(*)>1;")

i = 0
for f in c.fetchall():
    sql = 'delete from sample where file = "%s" limit 1'
    c.execute(sql)

#    sql = 'select hex(uuid) from sample where file="%s"' % f
#    c.execute(sql)
#    for r in c.fetchall()[:-1]:
#        sql = 'delete from sample where uuid = unhex("%s")' % r[0]
#        c.execute(sql)
    i = i+1
    print i

c.execute('commit')

exit()

c.execute('select file, hex(uuid) from sample where import_tag="newbioverify" and hex(md5) = "00000000000000000000000000000000"')

for r in c.fetchall():
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

c.execute('commit')