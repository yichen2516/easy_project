package online.lbprotocol.easy.hbapi.model.response;

import lombok.Getter;
import lombok.ToString;

/**
 * @author yichen for lfkg
 * @since 2021/9/24
 */
@Getter
@ToString
public class DefaultResponse<DATA_TYPE> {

    private String msg;
    private int code;
    private DATA_TYPE data;

}
