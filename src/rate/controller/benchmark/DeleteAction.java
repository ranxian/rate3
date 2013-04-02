package rate.controller.benchmark;

import org.apache.commons.io.FileUtils;

import java.io.File;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-4-2
 * Time: 下午2:19
 */
public class DeleteAction extends BenchmarkActionBase {
    public String execute() throws Exception {
        FileUtils.forceDelete(new File(benchmark.dirPath()));
        session.beginTransaction();
        session.delete(benchmark);
        session.getTransaction().commit();
        return SUCCESS;
    }
}
