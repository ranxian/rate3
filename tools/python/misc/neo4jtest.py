from bulbs.neo4jserver.graph import Graph
import json

g = Graph()

match_result_file="/home/ratedev/rate3/tools/python/misc/betweenpeople/match_result.txt"
mf = open(match_result_file, 'r')

i = 0
while True:
    l = mf.readline()
    if len(l)==0:
        break
    l = l.strip()
    if l.find('ok')>=0:
        (u1, u2, match_type, result, score) = l.split(' ')
    elif l.find('failed')>=0:
        (u1, u2, match_type, result) = l.split(' ')
        score = 0
    v1 = g.vertices.get_or_create("name", u1)
    v2 = g.vertices.get_or_create("name", u2)
    result = json.dumps([match_type, result, score])
    g.edges.create(v1, result, v2)
    i = i + 1
#    if i%1000==0:
    print i
