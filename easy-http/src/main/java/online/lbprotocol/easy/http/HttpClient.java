package online.lbprotocol.easy.http;

import okhttp3.*;
import org.apache.commons.collections.MapUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author YICHEN
 * @since 2018/11/21 3:08 PM
 * <p>
 * Singularity Sky Technologies Limited.
 */
public class HttpClient {

    public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/69.0.3497.100 Safari/537.36";
    private static final ReentrantLock lock = new ReentrantLock();
    public static int CONNECT_TIME_OUT_IN_SECONDS = 10;
    public static int READ_TIME_OUT_IN_SECONDS = 10;
    public static int WRITE_TIME_OUT_IN_SECONDS = 10;
    private static HttpClient defaultClient;
    private OkHttpClient client;

    public HttpClient() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT_IN_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    public static HttpClient getDefaultClient() {
        if (defaultClient == null) {
            lock.lock();
            try {
                if (defaultClient == null) {
                    defaultClient = new HttpClient();
                }
            } finally {
                lock.unlock();
            }
        }
        return defaultClient;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        client.dispatcher().executorService().shutdownNow();
    }

    public HttpResponse put(String url, Map<String, String> headers, String body) throws Throwable {
        return put(url, headers, MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), body);
    }

    public HttpResponse put(String url, Map<String, String> headers, MediaType mediaType, String body) throws Throwable {
        RequestBody requestBody = RequestBody.create(body, mediaType);
        Request.Builder builder = new Request.Builder().url(url).put(requestBody);
        builder.addHeader("User-Agent", USER_AGENT);

        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();

        return doRequest(request, body);
    }

    private HttpResponse doRequest(Request request, String body) throws IOException {
        try (Response response = client.newCall(request).execute()) {
            return new HttpResponse(request, body, response);
        }
    }

    public HttpResponse post(String url, Map<String, String> headers, String body) throws Throwable {
        return post(url, headers, MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), body);
    }

    public HttpResponse post(String url, Map<String, String> headers, MediaType mediaType, String body) throws Throwable {
        RequestBody requestBody = RequestBody.create(body, mediaType);
        Request.Builder builder = new Request.Builder().url(url).post(requestBody);
        builder.addHeader("User-Agent", USER_AGENT);

        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();

        return doRequest(request, body);
    }

    public HttpResponse delete(String url, Map<String, String> headers, String body) throws Throwable {
        return delete(url, headers, MediaType.parse("application/x-www-form-urlencoded"), body);
    }

    public HttpResponse delete(String url, Map<String, String> headers, MediaType mediaType, String body) throws Throwable {
        RequestBody requestBody = RequestBody.create(body, mediaType);
        Request.Builder builder = new Request.Builder().url(url).delete(requestBody);
        builder.addHeader("User-Agent", USER_AGENT);

        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();

        return doRequest(request, body);
    }

    public HttpResponse get(String url) throws Throwable {
        return get(url, null);
    }

    public HttpResponse get(String url, Map<String, String> headers) throws Throwable {
        Request.Builder builder = new Request.Builder().url(url).get();
        builder.addHeader("User-Agent", USER_AGENT);

        if (MapUtils.isNotEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        Request request = builder.build();

        return doRequest(request, "");
    }

    public static String toQueryString(Map<String, String> query) {
        return query.entrySet().stream().map(e -> {
            try {
                return e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                throw new Error(ex);
            }
        }).collect(Collectors.joining("&"));
    }
}
