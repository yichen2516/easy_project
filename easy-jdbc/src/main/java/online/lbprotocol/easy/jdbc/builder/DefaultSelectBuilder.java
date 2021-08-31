package online.lbprotocol.easy.jdbc.builder;

import lombok.Getter;
import lombok.var;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Sort;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
public class DefaultSelectBuilder implements SelectBuilder {

    private final DefaultWhereBuilder whereBuilder = new DefaultWhereBuilder();
    private final DefaultOrderBuilder orderBuilder = new DefaultOrderBuilder();

    private final String tableName;
    private final String[] columns;
    @Getter
    private int pageNumber = 0;
    @Getter
    private int pageSize = 1000;

    public DefaultSelectBuilder(String tableName) {
        this.tableName = tableName;
        this.columns = null;
    }

    public DefaultSelectBuilder(String tableName, String... columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public SelectBuilder where(String property, Object value) {
        whereBuilder.where(property, value);
        return this;
    }

    @Override
    public SelectBuilder where(Map<String, Object> conditions) {
        whereBuilder.where(conditions);
        return this;
    }

    @Override
    public SelectBuilder whereGreaterThan(String property, Object value) {
        whereBuilder.whereGreaterThan(property, value);
        return this;
    }

    @Override
    public SelectBuilder whereLessThan(String property, Object value) {
        whereBuilder.whereLessThan(property, value);
        return this;
    }

    @Override
    public SelectBuilder whereLike(String property, Object value) {
        whereBuilder.whereLike(property, value);
        return this;
    }

    @Override
    public SelectBuilder orderAsc(String property) {
        orderBuilder.orderAsc(property);
        return this;
    }

    @Override
    public SelectBuilder orderDesc(String property) {
        orderBuilder.orderDesc(property);
        return this;
    }

    @Override
    public SelectBuilder orderBy(String property, Sort.Direction direction) {
        orderBuilder.orderBy(property, direction);
        return this;
    }

    @Override
    public SelectBuilder orderBy(String property, String direction) {
        orderBuilder.orderBy(property, direction);
        return this;
    }


    @Override
    public void page(int pageNumber, int pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    @Override
    public Pair<String, Map<String, Object>> build() {
        var params = new HashMap<String, Object>();
        var sb = new StringBuilder("SELECT ");

        if (columns != null && columns.length != 0) {
            sb.append(String.join(", ", columns));
        } else {
            sb.append("*");
        }
        sb.append(" FROM ").append(tableName);

        Pair<String, Map<String, Object>> where = whereBuilder.build();
        if (StringUtils.isNotBlank(where.getLeft())) {
            sb.append(" ").append(where.getLeft());
        }

        Pair<String, Map<String, Object>> order = orderBuilder.build();
        if (StringUtils.isNotBlank(order.getLeft())) {
            sb.append(" ").append(order.getLeft());
        }

        sb.append(" LIMIT ").append(pageSize).append(" OFFSET ").append(pageNumber * pageSize);

        params.putAll(where.getRight());
        params.putAll(order.getRight());

        return Pair.of(sb.toString(), params);
    }


    @Override
    public Pair<String, Map<String, Object>> buildForCount(String idColumn) {
        var params = new HashMap<String, Object>();
        var sb = new StringBuilder("SELECT count(").append(idColumn).append(") FROM ").append(tableName);

        Pair<String, Map<String, Object>> where = whereBuilder.build();
        if (StringUtils.isNotBlank(where.getLeft())) {
            sb.append(" ").append(where.getLeft());
        }

        Pair<String, Map<String, Object>> order = orderBuilder.build();
        if (StringUtils.isNotBlank(order.getLeft())) {
            sb.append(" ").append(order.getLeft());
        }

        params.putAll(where.getRight());
        params.putAll(order.getRight());

        return Pair.of(sb.toString(), params);
    }
}
