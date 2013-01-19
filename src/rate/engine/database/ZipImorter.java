package rate.engine.database;

import net.lingala.zip4j.core.ZipFile;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import rate.model.SampleEntity;
import rate.model.ClazzEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import rate.util.HibernateUtil;
import rate.util.RateConfig;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-1-17
 * Time: 下午3:03
 */
public class ZipImorter {
    private final Logger logger = Logger.getLogger(ZipImorter.class);
    private final Session session = HibernateUtil.getSession();

    public void main(String importTag, String type, String zipPath) throws Exception {
        ZipFile zipFile = new ZipFile(new File(zipPath));
        String zipFileName = zipFile.getFile().getName();
        String zipFileBaseName = zipFileName.substring(0, zipFileName.length() - 4);
        if (!zipFile.isValidZipFile()) {
            logger.trace("Zip file is not valid");
            return;
        }

        String destDir = RateConfig.getSampleRootDir() + "/" + zipFileBaseName;

        zipFile.extractAll(destDir);
        File sampleDir = new File(destDir);
;
        File[] clazzdirs = sampleDir.listFiles();
        if (clazzdirs == null) {
            logger.trace("No class directory in .zip");
            return;
        }

        session.beginTransaction();

        for (int i = 0; i < clazzdirs.length; i++) {
            ClazzEntity clazz = new ClazzEntity();
            clazz.setImportTag(importTag);
            clazz.setType(type);
            session.save(clazz);

            File[] sampleFiles = clazzdirs[i].listFiles();

            if (sampleFiles == null) {
                logger.trace("No samples in some class");
                return;
            }
            for (int j = 0; j < sampleFiles.length; j++) {
                File sampleFile = sampleFiles[j];
                String samplePath = zipFileBaseName + "/" + sampleFile.getParentFile().getName() + "/" +
                        sampleFile.getName();

                SampleEntity sample = new SampleEntity();
                sample.setFile(samplePath);
                sample.setImportTag(importTag);

                InputStream in = new FileInputStream(sampleFile);
                byte[] md5 = DigestUtils.md5(in);

                sample.setMd5(md5);
                sample.setClazz(clazz);

                session.save(sample);
            }
            session.save(clazz);
        }
        session.getTransaction().commit();
    }

    public static String[] getZipFilePath() throws Exception {
        File zipRoot = new File(RateConfig.getZipRootDir());
        String[] filePaths = zipRoot.list();

        return filePaths;
    }
}
