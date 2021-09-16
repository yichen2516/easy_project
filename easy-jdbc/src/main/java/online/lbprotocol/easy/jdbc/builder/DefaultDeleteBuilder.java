package online.lbprotocol.easy.jdbc.builder;

import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/16
 */
public class DefaultDeleteBuilder implements DeleteBuilder{
    private final DefaultWhereBuilder whereBuilder = new DefaultWhereBuilder();
    private final String tableName;

    public DefaultDeleteBuilder(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Pair<String, Map<String, Object>> build() {
        var sb = new StringBuilder("DELETE FROM ").append(tableName);

        Pair<String, Map<String, Object>> where = whereBuilder.build();
        if (StringUtils.isNotBlank(where.getLeft())) {
            sb.append(" ").append(where.getLeft());
        }
        return Pair.of(sb.toString(), where.getRight());
    }

    @Override
    public DeleteBuilder where(String property, Object value) {
        whereBuilder.where(property, value);
        return this;
    }

    @Override
    public DeleteBuilder where(Map<String, Object> conditions) {
        whereBuilder.where(conditions);
        return this;
    }

    @Override
    public DeleteBuilder whereGreaterThan(String property, Object value) {
        whereBuilder.whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DeleteBuilder whereLessThan(String property, Object value) {
        whereBuilder.whereLessThan(property, value);
        return this;
    }

    @Override
    public DeleteBuilder whereLike(String property, Object value) {
        whereBuilder.whereLike(property, value);
        return this;
    }

}
