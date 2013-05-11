package rate.engine.CLI;

import org.hibernate.Session;
import rate.util.DebugUtil;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-10
 * Time: AM10:52
 * To change this template use File | Settings | File Templates.
 */
public class RateCLI {
    private static Session session;

    public static void main(String[] args) {
        while (true) {
            // cmd looks like:
            // ----------- View -----------
            // list:     > list views
            // Generate: > generate view importTag 2013Spring
            // delete:   > delete view <uuid>
            // desc:     > desc view <uuid> [new_desc]
            // ----------- Benchmark ------
            // list:     > list benchmarks
            // Generate: > generate benchmark SLSB 100 5
            // delete    > delete benchmark <uuid>
            // desc:     > desc benchmark <uuid> [new_desc]
            // ----------- Algorithm ------
            // List:     > list algorithms
            // Create:   > create algorithm <name>
            // Upload:   > upload version to <algorithm_uuid> <enroll.exe> <match.exe>
            // desc:     > desc algorithm <uuid> [new_desc]
            //           > desc version <uuid> [new_desc]
            // ----------- Task -----------
            // Run:      > run task <benchmark_uuid> <algorithm_uuid>
            // List:     > list tasks [-f] [--after <time_after>] [--before <time_before>] [--author <author_name>]
            //              [--algm <algorithm_uuid>] [--bm <benchmark_uuid>]
            // Desc:     > desc <task_uuid>
            // Detail:   > detail <task_uuid>
            // Delete:   > delete <task_uuid>

            // Generate benchmark: generate benchmark slsb -c 100 -s 5
            //
            // cmd looks like generate benchmark slsb -c 100 -s 5
            String line = "list views";
            String[] cmds = line.split(" ");
            for (int i = 0; i < cmds.length; i++) {
                cmds[i] = cmds[i].toLowerCase();
            }
            if (cmds[0].equals("list")) {
                if (cmds[1].matches("view.*")) {
                    ViewUtils.list();
                }
            }
            break;
        }
    }

    public static String rateReadline() {
        System.out.print("rate> ");
        return System.console().readLine();
    }

    public static void ratePrintln(String msg) {
        System.out.println("--> " + msg);
    }
}
