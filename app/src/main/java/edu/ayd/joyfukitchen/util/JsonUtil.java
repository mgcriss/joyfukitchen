package edu.ayd.joyfukitchen.util;

import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

import edu.ayd.joyfukitchen.bean.Menus;

/**
 * Created by tangtang on 2017/4/5 09:49.
 * 从服务器获得json数据，把json转成对象。
 * OkHttp
 */

public class JsonUtil {

    private String url_json = "http://apis.juhe.cn/cook/query.php";
    private OkHttpClient myClient ;
    private Gson gson = new Gson();

  
    public void doGet(View v) throws IOException {

        myClient = new OkHttpClient();
        Request request = new Request.Builder().url(url_json).build();


        Response response=myClient.newCall(request).execute();

        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        Menus menu = gson.fromJson(response.body().charStream(), Menus.class);
        for (Map.Entry<String, MenusFile> entry :menu.files.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().content);
        }

    }


    static class Menus {
        Map<String, MenusFile> files;
    }

    static class MenusFile {
        String content;
    }


}
