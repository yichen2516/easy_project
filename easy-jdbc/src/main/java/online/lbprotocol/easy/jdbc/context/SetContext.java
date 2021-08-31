package online.lbprotocol.easy.jdbc.context;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
@Getter
public class SetContext {

    private Map<String, Object> values;

    public Map<String, Object> getValuesLazy() {
        if (values == null) {
            values = new HashMap<>();
        }
        return values;
    }
}
