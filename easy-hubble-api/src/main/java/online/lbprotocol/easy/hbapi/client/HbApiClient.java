package online.lbprotocol.easy.hbapi.client;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.SneakyThrows;
import lombok.val;
import okhttp3.MediaType;
import online.lbprotocol.easy.hbapi.model.HbResponse;
import online.lbprotocol.easy.hbapi.model.TokenResponse;
import online.lbprotocol.easy.hbapi.model.response.*;
import online.lbprotocol.easy.http.HttpClient;
import online.lbprotocol.easy.http.HttpResponse;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yichen for lfkg
 * @since 2021/9/24
 */
public final class HbApiClient {

    public static final String BASE_URL = "http://bigdata.hbzyai.com/api";

    @SneakyThrows
    public static HbResponse<TokenResponse> getToken(String username, String password) {
        String path = "/auth/oauth/token?grant_type=password";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic dGVzdDp0ZXN0");
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> body = new HashMap<>();
        body.put("username", username);
        body.put("password", password);
        body.put("scope", "server");
        val response = HttpClient.getDefaultClient().post(BASE_URL + path, header, HttpClient.toQueryString(body));
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), TokenResponse.class));
    }

    @SneakyThrows
    public static HbResponse<TokenResponse> refreshToken(String refreshToken) {
        String path = "/auth/oauth/token?grant_type=refresh_token";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic dGVzdDp0ZXN0");
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, String> body = new HashMap<>();
        body.put("refresh_token", refreshToken);
        body.put("scope", "server");
        val response = HttpClient.getDefaultClient().post(BASE_URL + path, header, HttpClient.toQueryString(body));
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), TokenResponse.class));
    }

    /**
     * ????????????
     *
     * @param order      ??????(??????0;??????1)
     * @param orderField ????????????(?????????HOT_NUM;????????????URL_TIME)
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<ArticleRecord>>> getAllHotNews(String token, String order, String orderField, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/all";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("order", order);
        query.put("orderField", orderField);
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<ArticleRecord>>>() {
        }.getType()));
    }

    /**
     * ????????????
     *
     * @param order      ??????(??????0;??????1)
     * @param orderField ????????????(?????????HOT_NUM;????????????URL_TIME)
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     * @param province   ???????????????"??????"???"??????"
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<ArticleRecord>>> getAreaHotNews(String token, String order, String orderField, int pageNumber, int pageSize, String province) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/area";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("order", order);
        query.put("orderField", orderField);
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        query.put("province", province);
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<ArticleRecord>>>() {
        }.getType()));
    }

    /**
     * ????????????
     *
     * @param order      ??????(??????0;??????1)
     * @param orderField ????????????(?????????HOT_NUM;????????????URL_TIME)
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     * @param category   ??????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<ArticleRecord>>> getCategoryHotNews(String token, String order, String orderField, int pageNumber, int pageSize, String category) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/category";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("order", order);
        query.put("orderField", orderField);
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        query.put("category", category);
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<ArticleRecord>>>() {
        }.getType()));
    }

    /**
     * ????????????
     *
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<SocialMediaRecord>>> getWeiboHotNews(String token, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/weibo";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<SocialMediaRecord>>>() {
        }.getType()));
    }

    /**
     * ????????????
     *
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<SocialMediaRecord>>> getWeixinHotNews(String token, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/weixin";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<SocialMediaRecord>>>() {
        }.getType()));
    }

    /**
     * ???????????????
     *
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<VideoRecord>>> getVideoMediaHotNews(String token, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/video";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<VideoRecord>>>() {
        }.getType()));
    }

    /**
     * ????????????
     *
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     * @return
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<VideoRecord>>> getDouyinHotNews(String token, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/hotNews/douyin";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<VideoRecord>>>() {
        }.getType()));
    }

    /**
     * ???????????????
     *
     * @param content    ????????????
     * @param startTime  ????????????
     * @param endTime    ????????????
     * @param searchType ????????????(1?????? 0??????)
     * @param order      ????????????(????????????1???????????????0)
     * @param pageNumber ?????? ??????1
     * @param pageSize   ????????????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<ListResponseData<DefaultNewsRecord>>> searchNewsByKeyword(String token, String area, String content, String startTime, String endTime, int searchType, int order, int pageNumber, int pageSize) {
        pageNumber = Math.max(pageNumber, 1);
        String path = "/news/center/searchByKeyWord";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        if (area != null) {
            query.put("area", area);
        }
        query.put("content", content);
        query.put("startTime", startTime);
        query.put("endTime", endTime);
        query.put("searchType", searchType + "");
        query.put("order", order + "");
        query.put("pageNo", pageNumber + "");
        query.put("pageSize", pageSize + "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<ListResponseData<DefaultNewsRecord>>>() {
        }.getType()));
    }

    /**
     * ??????????????????
     *
     * @param token
     * @param sid
     * @return
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<DefaultNewsRecord>> getNewsDetail(String token, String sid) {
        String path = "/news/news/detail";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("hbSid", sid);
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<DefaultNewsRecord>>() {
        }.getType()));
    }

    /**
     * nlp??????
     *
     * @param content ??????
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<NlpResponseData>> analysisNLP(String token, String content) {
        String path = "/news/nlp/analysis";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("content", content);
        val response = HttpClient.getDefaultClient().post(BASE_URL + path, header, MediaType.get("application/json"), toJson(query));
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<NlpResponseData>>() {
        }.getType()));
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<String>> checkText(String token, String content) {
        String path = "/news/nlp/checkText";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("content", content);
        val response = HttpClient.getDefaultClient().post(BASE_URL + path, header, MediaType.get("application/json"), toJson(query));
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<String>>() {
        }.getType()));
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<List<MatrixData>>> getMatrixWeixin(String token) {
        return getMatrix(token, "963c5858-b4a6-4175-a343-f6b15a4de55d", "wechat");
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<List<MatrixData>>> getMatrixDouyin(String token) {
        return getMatrix(token, "2365e3b0-205e-48bd-b9e4-bc41d39b854f", "tiktok");
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<List<MatrixData>>> getMatrixToutiao(String token) {
        return getMatrix(token, "f323a70f-b936-401f-b705-c053c4f9d5e8", "headline");
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<List<MatrixData>>> getMatrixWeibo(String token) {
        return getMatrix(token, "ae2ed6b9-e618-4450-aa58-ebe17a9a8e81", "weibo");
    }

    @SneakyThrows
    public static HbResponse<DefaultResponse<List<MatrixData>>> getMatrix(String token, String matrixId, String mediaType) {
        String path = "/cas/rank/day/media/wechat/list";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("matrixId", matrixId);
        query.put("mediaType", mediaType);
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<List<MatrixData>>>() {
        }.getType()));
    }


    /**
     * @param token
     * @param startTime
     * @param endTime
     * @param type      "web"???"app"
     * @return
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<List<TraditionalRankData>>> getTraditionalKeywords(String token, String startTime, String endTime, String type) {
        String path = "/cas/v2/spread/traditional/keywords/rank";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("startTime", startTime);
        query.put("endTime", endTime);
        query.put("type", type);
        query.put("uSource", "");
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<List<TraditionalRankData>>>() {
        }.getType()));
    }


    /**
     * @param token
     * @param startTime
     * @param endTime
     * @param type      "web"???"app"
     * @return
     */
    @SneakyThrows
    public static HbResponse<DefaultResponse<List<TraditionalRankData>>> getTraditionalIndustry(String token, String startTime, String endTime, String type) {
        String path = "/cas/v2/spread/traditional/industry/rank";
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        Map<String, String> query = new HashMap<>();
        query.put("startTime", startTime);
        query.put("endTime", endTime);
        query.put("type", type);
        HttpResponse response = HttpClient.getDefaultClient().get(BASE_URL + path + "?" + HttpClient.toQueryString(query), header);
        if (response.isError()) {
            return new HbResponse<>(response);
        }
        return new HbResponse<>(response, fromJson(response.getResponseBody(), new TypeToken<DefaultResponse<List<TraditionalRankData>>>() {
        }.getType()));
    }


    private static String toJson(Object obj) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(obj);
    }

    private static <T> T fromJson(String json, Class<T> t) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().fromJson(json, t);
    }

    private static <T> T fromJson(String json, Type t) {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().fromJson(json, t);
    }
}
