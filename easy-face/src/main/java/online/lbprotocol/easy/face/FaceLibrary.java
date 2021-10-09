package online.lbprotocol.easy.face;

import com.arcsoft.face.FaceEngine;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yichen for arcface-test
 * @since 2021/10/9
 */
@Slf4j
public final class FaceLibrary {

    private static final String LIB_PATH;
    private static final String[] LIBS = new String[]{"libarcsoft_face.so", "libarcsoft_face_engine.so", "libarcsoft_face_engine_jni.so"};

    static {
        LIB_PATH = System.getProperty("user.home") + "/face_recognition_so";
    }

    public static void prepareLibFiles() throws IOException {
        File libDir = new File(LIB_PATH);
        if (!libDir.isDirectory()) {
            log.info(libDir.getAbsolutePath() + " is not a directory. Delete it.");
            libDir.delete();
        }
        if (!libDir.exists()) {
            log.info(libDir.getAbsolutePath() + " does not exist. Create it.");
            libDir.mkdirs();
        }
        for (String libName : LIBS) {
            File libFile = new File(LIB_PATH + "/" + libName);
            if (libFile.exists()) {
                log.info(libFile.getAbsolutePath() + " exists. Delete it and rewrite.");
                libFile.delete();
            }
            log.info("Writing " + libFile.getAbsolutePath());

            InputStream classFileStream = FaceLibrary.class.getClassLoader()
                    .getResourceAsStream("libs/LINUX64/" + libName);
            FileOutputStream fos = new FileOutputStream(LIB_PATH + "/" + libName);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = classFileStream.read(bytes)) > 0) {
                fos.write(bytes, 0, length);
            }

            classFileStream.close();
            fos.close();
        }

        log.info("Face library write complete!");
    }

    public static void getFaceEngine() {
        FaceEngine faceEngine = new FaceEngine(LIB_PATH);
        int i = faceEngine.activeOnline("", "");
        System.out.println(i);
    }
}
