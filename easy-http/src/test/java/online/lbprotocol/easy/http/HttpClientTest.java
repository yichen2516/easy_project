package online.lbprotocol.easy.http;


import java.util.ArrayList;
import java.util.List;

/**
 * @author yichen for easy_project
 * @since 2021/10/12
 */
public class HttpClientTest {

    public static void main(String[] args) {

        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            l.add(i);
        }

        l.parallelStream().forEach(i -> {

            try {
                HttpResponse httpResponse = HttpClient.getDefaultClient().get("http://127.0.0.1:8080");
            } catch (Throwable e) {
                e.printStackTrace();
            }


        });
    }
}