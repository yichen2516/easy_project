package online.lbprotocol.easy.jdbc.builder;

import lombok.Getter;
import lombok.var;
import online.lbprotocol.easy.jdbc.context.WhereContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
public class DefaultWhereBuilder implements WhereBuilder<DefaultWhereBuilder> {

    @Getter(lazy = true)
    private final WhereContext context = new WhereContext();

    @Override
    public DefaultWhereBuilder where(String property, Object value) {
        getContext().getEqualsLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultWhereBuilder where(Map<String, Object> conditions) {
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
    public Pair<String, Map<String, Object>> build() {
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

        if (!params.isEmpty()) {
            sb.insert(0, "WHERE ");
            sb.delete(sb.length() - 5, sb.length());
        }


        return Pair.of(sb.toString(), params);
    }
}
