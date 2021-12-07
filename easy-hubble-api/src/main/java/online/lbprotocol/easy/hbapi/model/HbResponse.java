package online.lbprotocol.easy.hbapi.model;

import lombok.Getter;
import lombok.ToString;
import online.lbprotocol.easy.http.HttpResponse;

/**
 * @author yichen for lfkg
 * @since 2021/9/24
 */
@Getter
@ToString
public class HbResponse<T> {
    private T data;
    private HttpResponse httpResponse;

    public HbResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    public HbResponse(HttpResponse httpResponse, T data) {
        this.data = data;
        this.httpResponse = httpResponse;
    }
}
