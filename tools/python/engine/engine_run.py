import sys
import os
sys.path.append(os.path.dirname(__file__))
from producer import RateProducer
import traceback

if __name__=='__main__':
    usage = """
    %s host benchmark_dir result_dir algorithm_version_dir timelimit memlimit
    host is the host where queue server is on
    benchmark and result can be absolute or releative path
    algorithm_version_dir must be relative path from RATE_ROOT (i.e. algorithms/algorithm-uuid/version-uuid)
    timelimit in ms
    memlimit in byte
    """ % (sys.argv[0])
    if len(sys.argv)!=7:
        print usage
        exit()

    try:
        p = RateProducer(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6])
        p.solve()
    except Exception, e:
        print e
        traceback.print_exc()
