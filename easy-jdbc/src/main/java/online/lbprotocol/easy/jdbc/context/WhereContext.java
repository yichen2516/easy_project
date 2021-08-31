package online.lbprotocol.easy.jdbc.context;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

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
}
