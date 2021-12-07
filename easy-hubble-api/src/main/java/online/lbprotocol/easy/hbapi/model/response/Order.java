package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

/**
 * @author yichen for lfkg
 * @since 2021/9/24
 */
@Getter
@ToString
public class Order {
    private boolean asc;
    private String column;
}
