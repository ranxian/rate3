package rate.engine.benchmark.generator;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.apache.commons.lang3.tuple.Pair;
import org.hibernate.Query;
import org.hibernate.Session;
import rate.model.*;
import rate.util.HibernateUtil;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by XianRan
 * Email: xranthoar@gmail.com
 * Date: 13-3-26
 * Time: 下午4:35
 */
public class SLSBGenerator extends GeneralFVC2006Generator {
    private int B4Frr;
    private int B4Far;

    public BenchmarkEntity generate() throws Exception {
        prepare();

        generateInnerClazz();
    }


}

