package com.liqi.http;

import com.liqi.HttpRequestProvider;
import com.liqi.LQClient;
import com.liqi.ResultResponse;
import com.liqi.annotation.Field;
import com.liqi.annotation.Header;
import com.liqi.annotation.POST;
import com.liqi.service.Callback;
import com.liqi.service.HttpCall;
import com.liqi.service.LQApiProvider;
import com.liqi.service.LQRequest;
import com.liqi.service.LQResponse;
import com.liqi.service.Request;
import com.liqi.service.Response;
import com.liqi.service.convert.Convert;
import com.liqi.service.convert.JsonConvert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    interface Api{
        @Header("Cache-control: max-age=64000")
        @POST("HttpServer/HelloServlet")
        void fetch(@Field("username") String uname, @Field("password") int ps,Callback<User> callback);
    }


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



        /*Map<String, String> map = new HashMap<>();
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
        });*/


//        Request request = new Request.Builder()
//                .url("http://192.168.1.4:8080/HttpServer/HelloServlet")
//                .addFormParam("username","2222")
//                .addFormParam("password","3333")
//                .httpMethod(HttpMethod.POST)
//                .build();

//        List<Convert> converts = new ArrayList<>();
//        converts.add(new JsonConvert());

        LQClient client = new LQClient.Builder().url("http://192.168.1.4:8080/")
                .executor(Executors.newCachedThreadPool())
                .builder();

        Api api = client.create(Api.class);
        api.fetch("myname", 1234567, new Callback<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                System.out.print(response.body());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });


        /*HttpCall httpCall = new HttpCall(request,client, new Callback<User>() {
            @Override
            public void onSuccess(Response<User> response) {
                System.out.print(response.body());
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
        httpCall.invoke().get();*/
    }
}