package online.lbprotocol.easy.face;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.toolkit.ImageInfo;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author yichen for easy_project
 * @since 2021/10/9
 */
@SpringBootTest
public class FaceTest {

    @Test
    public void t1() {
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        faceEngine = FaceUtils.initImageFaceEngine(faceEngine);
        FaceSimilar faceSimilar = FaceUtils.compareFaceFeature(faceEngine,
                "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/test/4.jpg",
                "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/test/3.jpg");
        System.out.println(faceSimilar.getScore());
    }

    @Test
    public void foo() throws UnsupportedEncodingException {
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        faceEngine = FaceUtils.initImageFaceEngine(faceEngine);
        FaceFeature f1 = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m30.jpeg");
        FaceFeature f2 = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m31.jpeg");
        String s1 = Base64.getEncoder().encodeToString(f1.getFeatureData());
        System.out.println(s1);
        String s2 = Base64.getEncoder().encodeToString(f2.getFeatureData());
        System.out.println(s2);
        FaceSimilar faceSimilar = FaceUtils.compareFaceFeature(faceEngine, new FaceFeature(Base64.getDecoder().decode(s1)), new FaceFeature(Base64.getDecoder().decode(s2)));
        System.out.println(faceSimilar.getScore());
    }

    @SneakyThrows
    @Test
    public void test() {
        // > 0.66
        FaceUtils.prepareLibFiles();
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        FaceUtils.initImageFaceEngine(faceEngine);
        List<Pair<Float, String>> result = new ArrayList<>();
        Set<Integer> errors = new HashSet<>();
        for (int i = 1; i < 100; i++) {
            if (errors.contains(i)) continue;

            FaceFeature f1 = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m" + i + ".jpeg");
            if (f1 == null) {
                errors.add(i);
                continue;
            }

            for (int j = 1; j < 100; j++) {
                if (i == j) continue;
                if (errors.contains(j)) continue;

                FaceFeature f2 = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m" + j + ".jpeg");
                if (f2 == null) {
                    errors.add(j);
                    continue;
                }

                FaceSimilar faceSimilar = FaceUtils.compareFaceFeature(faceEngine,
                        f1,
                        f2);

                if (faceSimilar == null) {
                    result.add(new Pair<>(-1.f, i + " : " + j));
                } else {
                    result.add(new Pair<>(faceSimilar.getScore(), i + " : " + j));
                }
            }
        }
        result.sort((o1, o2) -> Float.compare(o1.getLeft(), o2.getLeft()));

        for (Pair<Float, String> p : result) {
            System.out.println(p.right + " = " + p.left);
        }

//        Pair<ImageInfo, List<FaceInfo>> imageInfoListPair = FaceUtils.detectFaces(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m3.jpg");
//        System.out.println(imageInfoListPair);
//        FaceFeature faceFeature = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/nf.jpg");
//        System.out.println(faceFeature);
//        FaceSimilar faceSimilar = FaceUtils.compareFaceFeature(faceEngine,
//                "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m1.jpg",
//                "/home/yichen/Developer/easy_project/easy-face/src/test/java/online/lbprotocol/easy/face/m2.jpg");
//        System.out.println(faceSimilar.getScore());
    }
}
