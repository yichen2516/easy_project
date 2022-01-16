package online.lbprotocol.easy.jdbc.builder;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
class DefaultWhereBuilderTest {

    @Test
    void build() {

        Pair<String, Map<String, ?>> build = new DefaultWhereBuilder()
                .where("equal_1", "equal_value_1")
                .where("equal_2", "equal_value_2")
                .whereLessThan("less_1", "less_value_2")
                .whereLike("like_1", "like_value_1")
                .whereLike("like_2", "like_value_2")
                .build();

        assert "WHERE equal_2 = :weq_equal_2 AND equal_1 = :weq_equal_1 AND less_1 < :wls_less_1 AND like_1 LIKE :wlk_like_1 AND like_2 LIKE :wlk_like_2".equals(build.getLeft());

        assert build.getRight().size() == 5;

        assert "".equals(new DefaultWhereBuilder().build().getLeft());
    }
}