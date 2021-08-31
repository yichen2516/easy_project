package online.lbprotocol.easy.jdbc.builder;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
public interface SqlBuilder {
    Pair<String, Map<String, Object>> build();
}
