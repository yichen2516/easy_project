package online.lbprotocol.easy.http;

import lombok.SneakyThrows;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.net.URLDecoder;

/**
 * @author YICHEN
 * @since 2018/11/21 3:09 PM
 * <p>
 * Singularity Sky Technologies Limited.
 */
public class HttpResponse {

    private String method;
    private String requestUrl;
    private String requestContentType;
    private Headers requestHeaders;
    private String requestBody;
    private Headers responseHeaders;
    private String responseBody;
    private String responseMessage;
    private int code;

    @SneakyThrows
    public HttpResponse(Request request, String requestBody, Response response) {
        this.method = request.method();
        RequestBody body = request.body();
        if (body != null && body.contentType() != null) {
            this.requestContentType = body.contentType().toString();
        }
        this.requestUrl = response.request().url().toString();
        this.requestHeaders = request.headers();
        this.requestBody = requestBody;
        this.responseHeaders = response.headers();
        if (response.body() != null) {
            this.responseBody = response.body().string();
        }
        this.responseMessage = response.message();
        this.code = response.code();
    }

    public Headers getRequestHeaders() {
        return requestHeaders;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public Headers getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public int getCode() {
        return code;
    }

    public boolean isError() {
        return !isSuccess();
    }

    public boolean isSuccess() {
        return getCode() == 200;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestContentType() {
        return requestContentType;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    @SneakyThrows
    @Override
    public String toString() {

        return "HttpResponse{" +
                "\nmethod:\n" +
                "=========\n" +
                method +
                "\n\nrequestUrl:\n" +
                "===============\n" +
                URLDecoder.decode(requestUrl, "UTF-8") +
                "\n\nrequestHeaders:\n" +
                "===================\n" +
                requestHeaders +
                "\n\nrequestContentType:\n" +
                "=======================\n" +
                requestContentType +
                "\n\nrequsetBody:\n" +
                "================\n" +
                requestBody +
                "\n\nresponseHeaders:\n" +
                "====================\n" +
                responseHeaders +
                "\n\nresponseMessage:\n" +
                "====================\n" +
                responseMessage +
                "\n\nresponseBody:\n" +
                "=================\n" +
                responseBody +
                "\n\n}";
    }
}
