from producer import RateProducer
import sys

# python engine_run.py ratedev-server /Volumes/RATE_ROOT/benchmarks/501764ba-5fa1-4c55-9374-e29a1e73c10b/benchmark.txt /Volumes/RATE_ROOT/temp/tasks/yyk-test/result.txt algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/353e25ba-f15a-4add-9e50-6b8ec0e2cdeb/enroll.exe algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/353e25ba-f15a-4add-9e50-6b8ec0e2cdeb/match.exe 1000 52428800

# python engine_run.py ratedev-server /Volumes/RATE_ROOT/benchmarks/340c80d3-4c62-427c-adca-034fd67cff81/benchmark.txt /Volumes/RATE_ROOT/temp/tasks/yyk-test/result.txt algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/f51173ec-b2ca-4c7f-aebe-a48a6eadf199/enroll.exe algorithms/01fc27c3-2284-448d-bc04-01e3de59951d/f51173ec-b2ca-4c7f-aebe-a48a6eadf199/match.exe 1000 52428800

if __name__=='__main__':
    usage = """
    %s host benchmark result enrollexe matchexe timelimit memlimit
    host is the host where queue server is on
    benchmark and result are absolute path
    enrollexe and matchexe are relative path from RATE_ROOT (i.e. algorithms/algorithm-uuid/version-uuid/enroll.exe)
    timelimit in ms
    memlimit in byte
    """ % (sys.argv[0])
    if len(sys.argv)!=8:
        print usage
        exit()

    p = RateProducer(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6], sys.argv[7])
    p.solve()
