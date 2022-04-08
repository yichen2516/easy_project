package online.lbprotocol.easy.jdbc.context;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
@Getter
public class WhereContext {

    private Map<String, Object> equals;
    private Map<String, Object> greater;
    private Map<String, Object> less;
    private Map<String, Object> like;
    private Map<String, Set<Object>> in;

    public Map<String, Object> getEqualsLazy() {
        if (equals == null) {
            equals = new HashMap<>();
        }
        return equals;
    }

    public Map<String, Object> getGreaterLazy() {
        if (greater == null) {
            greater = new HashMap<>();
        }
        return greater;
    }

    public Map<String, Object> getLessLazy() {
        if (less == null) {
            less = new HashMap<>();
        }
        return less;
    }

    public Map<String, Object> getLikeLazy() {
        if (like == null) {
            like = new HashMap<>();
        }
        return like;
    }

    public Map<String, Set<Object>> getInLazy() {
        if (in == null) {
            in = new HashMap<>();
        }
        return in;
    }
}
