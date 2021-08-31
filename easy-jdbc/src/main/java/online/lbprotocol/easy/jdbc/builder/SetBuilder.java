package online.lbprotocol.easy.jdbc.builder;

import java.util.Map;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
public interface SetBuilder<Self> extends SqlBuilder {

    Self set(String property, Object value);

    Self set(Map<String, Object> values);

}
