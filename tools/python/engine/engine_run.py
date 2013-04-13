from producer import RateProducer
import traceback
import sys

# super big
# python engine_run.py ratedev-server ~/Desktop/ ~/Desktop algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/f51173ec-b2ca-4c7f-aebe-a48a6eadf199/ 1000 52428800

# big
# python engine_run.py ratedev-server /Volumes/RATE_ROOT/benchmarks/501764ba-5fa1-4c55-9374-e29a1e73c10b /Volumes/RATE_ROOT/temp/tasks/yyk-test-big/ algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/f51173ec-b2ca-4c7f-aebe-a48a6eadf199/ 1000 52428800

# small
# python engine_run.py ratedev-server /Volumes/RATE_ROOT/benchmarks/340c80d3-4c62-427c-adca-034fd67cff81/ /Volumes/RATE_ROOT/temp/tasks/yyk-test/ algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/f51173ec-b2ca-4c7f-aebe-a48a6eadf199/ 1000 52428800

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
