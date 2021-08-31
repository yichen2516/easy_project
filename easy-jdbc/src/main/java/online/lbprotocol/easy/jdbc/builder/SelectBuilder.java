package online.lbprotocol.easy.jdbc.builder;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/8/1
 */
public interface SelectBuilder extends WhereBuilder<SelectBuilder>, OrderBuilder<SelectBuilder> {

    void page(int pageNumber, int pageSize);

    Pair<String, Map<String, Object>> buildForCount(String idColumn);
}
