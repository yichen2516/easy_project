package online.lbprotocol.easy.jdbc.builder;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/16
 */
@Slf4j
public class DefaultDeleteBuilder implements DeleteBuilder {
    private final DefaultWhereBuilder whereBuilder = new DefaultWhereBuilder();
    private final String tableName;

    public DefaultDeleteBuilder(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Pair<String, Map<String, ?>> build() {
        var sb = new StringBuilder("DELETE FROM ").append(tableName);

        Pair<String, Map<String, ?>> where = whereBuilder.build();
        if (StringUtils.isNotBlank(where.getLeft())) {
            sb.append(" ").append(where.getLeft());
        }
        log.debug("SQL: {}", sb.toString());
        return Pair.of(sb.toString(), where.getRight());
    }

    @Override
    public DefaultDeleteBuilder where(String property, Object value) {
        whereBuilder.where(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder where(Map<String, ?> conditions) {
        whereBuilder.where(conditions);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereGreaterThan(String property, Object value) {
        whereBuilder.whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereLessThan(String property, Object value) {
        whereBuilder.whereLessThan(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereLike(String property, Object value) {
        whereBuilder.whereLike(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereIn(String property, Object... values) {
        whereBuilder.whereIn(property, values);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereIf(boolean condition, String property, Object value) {
        if (condition) return where(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereGreaterThanIf(boolean condition, String property, Object value) {
        if (condition) return whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereLessThanIf(boolean condition, String property, Object value) {
        if (condition) return whereLessThan(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereLikeIf(boolean condition, String property, Object value) {
        if (condition) return whereLike(property, value);
        return this;
    }

    @Override
    public DefaultDeleteBuilder whereInIf(boolean condition, String property, Object... values) {
        if (condition) return whereIn(property, values);
        return this;
    }

}
