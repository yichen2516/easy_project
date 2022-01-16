package online.lbprotocol.easy.jdbc.builder;

import lombok.Getter;
import lombok.var;
import online.lbprotocol.easy.jdbc.context.OrderContext;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
public class DefaultOrderBuilder implements OrderBuilder<DefaultOrderBuilder> {

    @Getter(lazy = true)
    private final OrderContext context = new OrderContext();

    @Override
    public DefaultOrderBuilder orderAsc(String property) {
        getContext().getAscLazy().add(property);
        return this;
    }

    @Override
    public DefaultOrderBuilder orderDesc(String property) {
        getContext().getDescLazy().add(property);
        return this;
    }

    @Override
    public DefaultOrderBuilder orderBy(String property, Sort.Direction direction) {
        if (direction == Sort.Direction.ASC) {
            return orderAsc(property);
        }
        if (direction == Sort.Direction.DESC) {
            return orderDesc(property);
        }
        throw new IllegalArgumentException();
    }

    @Override
    public DefaultOrderBuilder orderBy(String property, String direction) {
        return orderBy(property, Sort.Direction.fromString(direction));
    }

    @Override
    public Pair<String, Map<String, ?>> build() {
        var sb = new StringBuilder();

        boolean hasOrder = false;
        if (getContext().getAsc() != null) {
            getContext().getAsc().forEach(v -> {
                sb.append(v).append(" ASC, ");
            });
            hasOrder = true;
        }

        if (getContext().getDesc() != null) {
            getContext().getDesc().forEach(v -> {
                sb.append(v).append(" DESC, ");
            });
            hasOrder = true;
        }

        if (hasOrder) {
            sb.insert(0, "ORDER BY ");
            sb.delete(sb.length() - 2, sb.length());
        }

        return Pair.of(sb.toString(), Collections.emptyMap());
    }
}
