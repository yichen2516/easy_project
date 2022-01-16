package online.lbprotocol.easy.jdbc.builder;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
class DefaultSetBuilderTest {

    @Test
    void build() {
        Pair<String, Map<String, ?>> build = new DefaultSetBuilder()
                .set("key1", "value1")
                .set("key2", "value2")
                .build();

        assert "SET key1 = :set_key1, key2 = :set_key2".equals(build.getLeft());
        assert build.getRight().size() == 2;
    }
}