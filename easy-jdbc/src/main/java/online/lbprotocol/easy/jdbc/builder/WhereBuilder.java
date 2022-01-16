package online.lbprotocol.easy.jdbc.builder;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/8/31
 */
public interface WhereBuilder<Self> extends SqlBuilder {

    Self where(String property, Object value);

    Self where(Map<String, ?> conditions);

    Self whereGreaterThan(String property, Object value);

    Self whereLessThan(String property, Object value);

    Self whereLike(String property, Object value);

    Self whereIf(boolean condition, String property, Object value);

    Self whereGreaterThanIf(boolean condition, String property, Object value);

    Self whereLessThanIf(boolean condition, String property, Object value);

    Self whereLikeIf(boolean condition, String property, Object value);


}
