package online.lbprotocol.easy.face;

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
        FaceEngineInitializer.prepareLibFiles();
        FaceEngineInitializer.getFaceEngine();
    }
}
