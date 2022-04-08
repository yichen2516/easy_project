package online.lbprotocol.easy.jdbc.builder;

import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
@Slf4j
public class DefaultUpdateBuilder implements UpdateBuilder {

    private final DefaultWhereBuilder whereBuilder = new DefaultWhereBuilder();
    private final DefaultSetBuilder setBuilder = new DefaultSetBuilder();

    private final String tableName;

    public DefaultUpdateBuilder(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public DefaultUpdateBuilder set(String property, Object value) {
        setBuilder.set(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder set(Map<String, ?> values) {
        setBuilder.set(values);
        return this;
    }

    @Override
    public DefaultUpdateBuilder setIf(boolean condition, String property, Object value) {
        if (condition) return set(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder where(String property, Object value) {
        whereBuilder.where(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder where(Map<String, ?> conditions) {
        whereBuilder.where(conditions);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereGreaterThan(String property, Object value) {
        whereBuilder.whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereLessThan(String property, Object value) {
        whereBuilder.whereLessThan(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereLike(String property, Object value) {
        whereBuilder.whereLike(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereIn(String property, Object... values) {
        whereBuilder.whereIn(property, values);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereIf(boolean condition, String property, Object value) {
        if (condition) return where(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereGreaterThanIf(boolean condition, String property, Object value) {
        if (condition) return whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereLessThanIf(boolean condition, String property, Object value) {
        if (condition) return whereLessThan(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereLikeIf(boolean condition, String property, Object value) {
        if (condition) return whereLike(property, value);
        return this;
    }

    @Override
    public DefaultUpdateBuilder whereInIf(boolean condition, String property, Object... values) {
        if (condition) return whereIn(property, values);
        return this;
    }


    @Override
    public Pair<String, Map<String, ?>> build() {
        var params = new HashMap<String, Object>();
        var sb = new StringBuilder("UPDATE ")
                .append(tableName);

        Pair<String, Map<String, ?>> set = setBuilder.build();
        sb.append(" ").append(set.getLeft());

        Pair<String, Map<String, ?>> where = whereBuilder.build();
        if (StringUtils.isNotBlank(where.getLeft())) {
            sb.append(" ").append(where.getLeft());
        }

        params.putAll(set.getRight());
        params.putAll(where.getRight());

        log.debug("SQL: {}", sb.toString());
        log.debug("Params: {}", params);
        return Pair.of(sb.toString(), params);
    }
}
