package online.lbprotocol.easy.jdbc.builder;

import lombok.Getter;
import lombok.var;
import online.lbprotocol.easy.jdbc.context.SetContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
public class DefaultSetBuilder implements SetBuilder<DefaultSetBuilder> {

    @Getter(lazy = true)
    private final SetContext context = new SetContext();

    @Override
    public DefaultSetBuilder set(String property, Object value) {
        getContext().getValuesLazy().put(property, value);
        return this;
    }

    @Override
    public DefaultSetBuilder set(Map<String, Object> values) {
        getContext().getValuesLazy().putAll(values);
        return this;
    }

    @Override
    public DefaultSetBuilder setIf(boolean condition, String property, Object value) {
        if (condition) return set(property, value);
        return this;
    }

    @Override
    public Pair<String, Map<String, Object>> build() {
        if (getContext().getValues() == null || getContext().getValues().isEmpty()) {
            throw new IllegalArgumentException("Update values must not be empty.");
        }
        var params = new HashMap<String, Object>();
        var sb = new StringBuilder();

        if (getContext().getValues() != null) {
            getContext().getValues().forEach((k, v) -> {
                sb.append(k).append(" = ").append(":set_").append(k).append(", ");
                params.put("set_" + k, v);
            });
        }

        if (!params.isEmpty()) {
            sb.insert(0, "SET ");
            sb.delete(sb.length() - 2, sb.length());
        }

        return Pair.of(sb.toString(), params);
    }
}
