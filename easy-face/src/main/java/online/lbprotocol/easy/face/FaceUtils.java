package online.lbprotocol.easy.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.CompareModel;
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
import java.util.Base64;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;

/**
 * The type Face utils.
 *
 * @author yichen for arcface-test
 * @since 2021 /10/9
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

    /**
     * 将链接库文件拷贝到用户根目录
     *
     * @throws IOException the io exception
     */
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

    /**
     * 构建并联网激活一个FaceEngine
     * <p>
     * 如果要多线程调用，确保每个线程激活一个新的FaceEngine。激活后需要初始化引擎{@link #initImageFaceEngine(FaceEngine)}
     *
     * @return the face engine
     */
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

    /**
     * 初始化图像处理引擎
     *
     * @param faceEngine the face engine
     * @return the face engine
     */
    public static FaceEngine initImageFaceEngine(FaceEngine faceEngine) {
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_0_ONLY);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(32);

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

    /**
     * 从图像中查询人脸信息
     *
     * @param faceEngine the face engine
     * @param imageUrl   the image url
     * @return the pair
     */
    public static Pair<ImageInfo, List<FaceInfo>> detectFaces(FaceEngine faceEngine, String imageUrl) {
        ImageInfo imageInfo = getImageInfo(imageUrl);
        List<FaceInfo> faceInfoList = new ArrayList<>();
        int errorCode = faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Detect face error " + errorCode + ". " + imageUrl);
            return null;
        }
        return new Pair<>(imageInfo, faceInfoList);
    }

    /**
     * 从图像中提取人脸特征
     *
     * @param faceEngine the face engine
     * @param imageUrl   the image url
     * @return the face feature
     */
    public static FaceFeatureResult extractFaceFeature(FaceEngine faceEngine, String imageUrl) {
        Pair<ImageInfo, List<FaceInfo>> faceResult = detectFaces(faceEngine, imageUrl);
        if (faceResult == null) {
            return null;
        }

        ImageInfo imageInfo = faceResult.left;
        List<FaceInfo> faceInfoList = faceResult.right;
        if (faceInfoList == null || faceInfoList.isEmpty()) {
            return null;
        }

        List<FaceFeature> features = new ArrayList<>();
        List<AgeInfo> ages = new ArrayList<>();
        List<GenderInfo> genders = new ArrayList<>();

        for (FaceInfo faceInfo : faceInfoList) {
            FaceFeature faceFeature = new FaceFeature();
            int errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfo, faceFeature);
            if (errorCode != ErrorInfo.MOK.getValue()) {
                log.error("Extract face error " + errorCode + ". " + imageUrl);
                return null;
            }
            features.add(faceFeature);
        }

        FunctionConfiguration configuration = new FunctionConfiguration();
        configuration.setSupportAge(true);
        configuration.setSupportFace3dAngle(true);
        configuration.setSupportGender(true);
        configuration.setSupportLiveness(true);
        int processErrorCode = faceEngine.process(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList, configuration);
        if (processErrorCode != ErrorInfo.MOK.getValue()) {
            log.error("Failed to process face." + imageUrl);
            return null;
        }

        faceEngine.getAge(ages);
        faceEngine.getGender(genders);

        return new FaceFeatureResult(features, ages, genders);
    }

    /**
     * 从图像中提取人脸特征并编码成 Base64 字符串，方便存储
     *
     * @return the string
     */
    public static String faceFeatureToBase64(FaceFeature faceFeature) {
        return Base64.getEncoder().encodeToString(faceFeature.getFeatureData());
    }

    /**
     * 从 Base64 编码的人脸特征构造一个特征对象
     *
     * @param base64 the base 64
     * @return the face feature
     */
    public static FaceFeature decodeFaceFeatureFromBase64(String base64) {
        return new FaceFeature(Base64.getDecoder().decode(base64));
    }

    /**
     * 比对两个特征的人脸相似度，建议评分 > 0.66 的认为是同一人
     *
     * @param faceEngine    the face engine
     * @param sourceFeature the source feature
     * @param targetFeature the target feature
     * @return the face similar
     */
    public static FaceSimilar compareFaceFeature(FaceEngine faceEngine, FaceFeature sourceFeature, FaceFeature targetFeature) {
        FaceSimilar faceSimilar = new FaceSimilar();
        int errorCode = faceEngine.compareFaceFeature(sourceFeature, targetFeature, CompareModel.LIFE_PHOTO, faceSimilar);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            log.error("Compare face error " + errorCode + ". ");
            return null;
        }
        return faceSimilar;
    }

    /**
     * @param imageUrl the image url
     * @return the image info
     */
    private static ImageInfo getImageInfo(String imageUrl) {
        return getRGBData(new File(imageUrl));
    }
}
