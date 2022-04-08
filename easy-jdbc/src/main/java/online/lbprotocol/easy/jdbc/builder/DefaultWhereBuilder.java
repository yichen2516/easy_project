package online.lbprotocol.easy.jdbc.builder;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import online.lbprotocol.easy.jdbc.context.WhereContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
@Slf4j
public class DefaultWhereBuilder implements WhereBuilder<DefaultWhereBuilder> {

    @Getter(lazy = true)
    private final WhereContext context = new WhereContext();

    @Override
    public DefaultWhereBuilder where(String property, Object value) {
        getContext().getEqualsLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder where(Map<String, ?> conditions) {
        getContext().getEqualsLazy().putAll(conditions);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereGreaterThan(String property, Object value) {
        getContext().getGreaterLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereLessThan(String property, Object value) {
        getContext().getLessLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereLike(String property, Object value) {
        getContext().getLikeLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereIn(String property, Object... values) {
        Set<Object> set = getContext().getInLazy().getOrDefault(property, new HashSet<>());
        set.addAll(Arrays.asList(values));
        getContext().getInLazy().put(property, set);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereIf(boolean condition, String property, Object value) {
        if (condition) return where(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereGreaterThanIf(boolean condition, String property, Object value) {
        if (condition) return whereGreaterThan(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereLessThanIf(boolean condition, String property, Object value) {
        if (condition) return whereLessThan(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereLikeIf(boolean condition, String property, Object value) {
        if (condition) return whereLike(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder whereInIf(boolean condition, String property, Object... values) {
        if (condition) return whereIn(property, values);
        return this;
    }

    @Override
    public Pair<String, Map<String, ?>> build() {
        var params = new HashMap<String, Object>();
        var sb = new StringBuilder();

        if (getContext().getEquals() != null) {
            getContext().getEquals().forEach((k, v) -> {
                sb.append(k).append(" = ").append(":weq_").append(k).append(" AND ");
                params.put("weq_" + k, v);
            });
        }

        if (getContext().getGreater() != null) {
            getContext().getGreater().forEach((k, v) -> {
                sb.append(k).append(" > ").append(":wgr_").append(k).append(" AND ");
                params.put("wgr_" + k, v);
            });
        }
        if (getContext().getLess() != null) {
            getContext().getLess().forEach((k, v) -> {
                sb.append(k).append(" < ").append(":wls_").append(k).append(" AND ");
                params.put("wls_" + k, v);
            });
        }
        if (getContext().getLike() != null) {
            getContext().getLike().forEach((k, v) -> {
                sb.append(k).append(" LIKE ").append(":wlk_").append(k).append(" AND ");
                params.put("wlk_" + k, v);
            });
        }
        if (getContext().getIn() != null) {
            getContext().getIn().forEach((k, v) -> {
                if (v.isEmpty()) return;
                sb.append(k).append(" IN (");
                var array = v.toArray();
                var sqlItemArray = new ArrayList<String>();
                for (int i = 0; i < array.length; i++) {
                    sqlItemArray.add(":win_" + k + "_" + i);
                    Object o = array[i];
                    params.put("win_" + k + "_" + i, o);
                }
                sb.append(String.join(",", sqlItemArray)).append(") AND ");
            });
        }

        if (!params.isEmpty()) {
            sb.insert(0, "WHERE ");
            sb.delete(sb.length() - 5, sb.length());
        }

        log.debug("SQL: {}", sb.toString());
        log.debug("Params: {}", params);
        return Pair.of(sb.toString(), params);
    }
}
