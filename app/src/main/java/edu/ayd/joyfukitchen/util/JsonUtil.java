package edu.ayd.joyfukitchen.util;

import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangtang on 2017/4/5 09:49.
 * 从服务器获得json数据，把json转成对象。
 * OkHttp
 */

public class JsonUtil {

    private OkHttpClient myClient ;
    private Gson gson = new Gson();


    public void doGet(View v , String url_json) throws IOException {

        myClient = new OkHttpClient();
        Request request = new Request.Builder().url(url_json).build();


        Response response=myClient.newCall(request).execute();



    }


    static class Menus {
        Map<String, MenuResult> files;
    }

    static class MenuResult {
        String content;
    }


}
