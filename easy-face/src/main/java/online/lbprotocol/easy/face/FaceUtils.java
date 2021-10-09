package online.lbprotocol.easy.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * @author yichen for arcface-test
 * @since 2021/10/9
 */
@Slf4j
public final class FaceUtils {

    private static final String APP_ID = "55x9ni5FC51J77jdaZRzE3bXuhaeora4z6R7wahfsqkt";
    private static final String SDK_KEY = "8NAvG4VFr6FcSo9Zs7nApxfhXGWrSfeX1cPcV64zYD4k";

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

            InputStream classFileStream = FaceUtils.class.getClassLoader()
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

    public static synchronized FaceEngine getFaceEngine() {
        FaceEngine fe = new FaceEngine(LIB_PATH);
        int errorCode = fe.activeOnline(APP_ID, SDK_KEY);
        if (errorCode != ErrorInfo.MOK.getValue() &&
                errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            log.error("Face engine online active failed with code " + errorCode);
            return null;
        }
        return fe;
    }

    public static FaceEngine initImageFaceEngine(FaceEngine faceEngine) {
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);

        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);

        //初始化引擎
        int errorCode = faceEngine.init(engineConfiguration);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Face engine init failed.");
            return null;
        }
        return faceEngine;
    }

    public static Pair<ImageInfo, List<FaceInfo>> detectFaces(FaceEngine faceEngine, String imageUrl) {
        ImageInfo imageInfo = getImageInfo(imageUrl);
        List<FaceInfo> faceInfoList = new ArrayList<>();
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Detect face error " + errorCode);
            return null;
        }
        return new Pair<>(imageInfo, faceInfoList);
    }

    public static FaceFeature extractFaceFeature(FaceEngine faceEngine, String imageUrl) {
        Pair<ImageInfo, List<FaceInfo>> faceResult = detectFaces(faceEngine, imageUrl);
        if (faceResult == null) {
            return null;
        }
        ImageInfo imageInfo = faceResult.left;
        List<FaceInfo> faceInfoList = faceResult.right;
        FaceFeature faceFeature = new FaceFeature();
        int errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Detect face error " + errorCode);
            return null;
        }
        return faceFeature;
    }

    public static FaceSimilar compareFaceFeature(FaceEngine faceEngine, String sourceImageUrl, String targetImageUrl) {
        FaceFeature f1 = extractFaceFeature(faceEngine, sourceImageUrl);
        if (f1 == null) {
            return null;
        }
        FaceFeature f2 = extractFaceFeature(faceEngine, targetImageUrl);
        if (f2 == null) {
            return null;
        }
        FaceSimilar faceSimilar = new FaceSimilar();
        int errorCode = faceEngine.compareFaceFeature(f1, f2, faceSimilar);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Detect face error " + errorCode);
            return null;
        }
        return faceSimilar;
    }

    public static FaceSimilar compareFaceFeature(FaceEngine faceEngine, FaceFeature sourceFeature, FaceFeature targetFeature) {
        FaceSimilar faceSimilar = new FaceSimilar();
        int errorCode = faceEngine.compareFaceFeature(sourceFeature, targetFeature, faceSimilar);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Detect face error " + errorCode);
            return null;
        }
        return faceSimilar;
    }

    public static ImageInfo getImageInfo(String imageUrl) {
        return getRGBData(new File(imageUrl));
    }
}
