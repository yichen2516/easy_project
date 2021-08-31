package online.lbprotocol.easy.jdbc.builder;

import org.springframework.data.domain.Sort;

/**
 * @author yichen for easy_project
 * @since 2021/9/1
 */
public interface OrderBuilder<Self> extends SqlBuilder {

    Self orderAsc(String property);

    Self orderDesc(String property);

    Self orderBy(String property, Sort.Direction direction);

    Self orderBy(String property, String direction);

}
