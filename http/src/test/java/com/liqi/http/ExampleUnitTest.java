package com.liqi.http;

import com.liqi.service.LQApiProvider;
import com.liqi.service.LQRequest;
import com.liqi.service.LQResponse;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        /*OkHttpClient client = new OkHttpClient();
//        OkHttpRequest request = new OkHttpRequest(client,HttpMethod.GET,"http://www.lenovo.com");
        OkHttpRequest request = new OkHttpRequest(client,HttpMethod.POST,"http://localhost:8080/HttpServer/HelloServlet");

        request.getBody().write("username=liqi&password=abcdefg".getBytes());

        HttpResponse response = request.execute();

        String content = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()));
        while ((content = reader.readLine()) != null){
            System.out.println(content);
        }
        response.close();*/






        System.out.println("ccc");



        Map<String, String> map = new HashMap<>();
        map.put("username", "liqi");
        map.put("password", "abcdefg");

        LQApiProvider.helloWorld("http://192.168.1.3:8080/HttpServer/HelloServlet", map, new LQResponse<String>() {

            @Override
            public void success(LQRequest request, String data) {
                System.out.println("aa");
            }

            @Override
            public void fail(int errorCode, String errorMsg) {
                System.out.println("bb");
            }
        });
    }
}