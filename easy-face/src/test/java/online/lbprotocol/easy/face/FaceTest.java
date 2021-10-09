package online.lbprotocol.easy.face;

import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceSimilar;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author yichen for easy_project
 * @since 2021/10/9
 */
@SpringBootTest
public class FaceTest {

    @SneakyThrows
    @Test
    public void test() {
        FaceUtils.prepareLibFiles();
        FaceEngine faceEngine = FaceUtils.getFaceEngine();
        FaceUtils.initImageFaceEngine(faceEngine);
        FaceFeature faceFeature = FaceUtils.extractFaceFeature(faceEngine, "/home/yichen/下载/1.jpeg");
        System.out.println(faceFeature);
        FaceSimilar faceSimilar = FaceUtils.compareFaceFeature(faceEngine,
                "/home/yichen/下载/1.jpeg",
                "/home/yichen/下载/2.jpeg");
        System.out.println(faceSimilar.getScore());
    }
}
