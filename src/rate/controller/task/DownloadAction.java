package rate.controller.task;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import rate.model.SampleEntity;
import rate.util.DebugUtil;
import rate.util.RateConfig;

import java.io.*;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: xianran
 * Date: 13-5-7
 * Time: PM4:56
 * To change this template use File | Settings | File Templates.
 */
public class DownloadAction extends TaskActionBase{
    private HashMap<String, SampleEntity> map = new HashMap<String, SampleEntity>();
    private static final Logger logger = Logger.getLogger(TaskActionBase.class);
    private File result;
    private File tarFile;
    SampleEntity getSampleFromUuid(String uuid) {
        return (SampleEntity)session.createQuery("from SampleEntity where uuid=:uuid")
                .setParameter("uuid", uuid).list().get(0);
    }

    public void prepairFiles(String resultFilePath, File baseDir) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(resultFilePath));
        int cnt = 0;
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            String[] sp = line.split(" ");
            if (!map.containsKey(sp[0]))
                map.put(sp[0], getSampleFromUuid(sp[0]));
            if (!map.containsKey(sp[1]))
                map.put(sp[1], getSampleFromUuid(sp[1]));


            SampleEntity sample1 = map.get(sp[0]);
            SampleEntity sample2 = map.get(sp[1]);

            File dir = new File(FilenameUtils.concat(baseDir.getAbsolutePath(), sp[2]+" "+sp[0]+" "+sp[1]));
            dir.mkdir();

            FileUtils.copyFileToDirectory(new File(sample1.getFilePath()), dir);
            FileUtils.copyFileToDirectory(new File(sample2.getFilePath()), dir);
            cnt++;
            if (cnt == 20) break;
        }

        reader.close();
    }

    public void genGResult() throws Exception {
        File baseDir = new File(FilenameUtils.concat(result.getAbsolutePath(), "genuine"));
        prepairFiles(generalTask.getGenuineFilePath(), baseDir);
    }

    public void genIResult() throws Exception {
        File baseDir = new File(FilenameUtils.concat(result.getAbsolutePath(), "imposter"));
        prepairFiles(generalTask.getRevImposterPath(), baseDir);
    }

    public InputStream getInputStream() throws Exception {

        return new FileInputStream(tarFile);

    }

    public String getFilename() {
        return tarFile.getName();
    }

    public String execute() throws Exception {
        result = new File(RateConfig.getTempRootDir()+File.separator+task.getUuid());
        tarFile = new File(result.getAbsolutePath()+".zip");
        if (tarFile.exists()) return SUCCESS;

        if (!result.exists()) {
            result.mkdir();

            genGResult();
            genIResult();
        }


        String dirName = result.getAbsolutePath();

        String cmd = "zip -1 -rv " + result.getName() + ".zip "+ result.getName();
        logger.debug("Run zip command " + cmd);
        Process process = Runtime.getRuntime().exec(cmd, null, new File(RateConfig.getTempRootDir()));
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        while (true) {
//            String line  = reader.readLine();
//            if (line == null) break;
//            logger.debug(line);
//        }

        process.waitFor();
//        reader.close();

        return SUCCESS;
    }
}
