package edu.ayd.joyfukitchen.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

import edu.ayd.joyfukitchen.bean.MenuResult;
import edu.ayd.joyfukitchen.bean.RecipeType;

/**
 * Created by tangtang on 2017/4/12 09:11.
 */

public class JsonDataDao {


    /**
     * 根据菜谱名和次数搜索菜谱
     * @param name
     * @param times
     * @return
     * @throws IOException
     */
    public List<MenuResult.ResultBean.DataBean> getMenuAll(String name, Integer times) throws IOException {
        String url_json="http://localhost:8080/recipe/searchRecipeFromName.do?recipeName="+name+"&times="+times;
        OkHttpClient client=new OkHttpClient();
        Request request= new Request.Builder().url(url_json).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result=response.body().string();
        Gson gson =new Gson();
        List<MenuResult.ResultBean.DataBean> menusList=gson.fromJson(result, new TypeToken<List<MenuResult.ResultBean.DataBean>>() {}.getType());
        return menusList;

    }

    /**
     * 根据食谱类型得到食谱子类型列表
     * @param typeName
     * @return
     * @throws IOException
     */
    public List<RecipeType> getMenuTypes(String typeName) throws IOException {
        String url_json="http://localhost:8080/recipe/searchTags.do?recipeTypeName="+typeName;
        OkHttpClient client=new OkHttpClient();
        Request request= new Request.Builder().url(url_json).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result=response.body().string();
        Gson gson =new Gson();
        List<RecipeType> typeList=gson.fromJson(result, new TypeToken<List<RecipeType>>() {}.getType());
        return typeList;
    }

    /**
     *根据食谱类型的id查询食谱
     * @param recipeTypeId
     * @param times
     * @return
     * @throws IOException
     */
    public List<MenuResult.ResultBean.DataBean> getMenuTypeIDALL(String recipeTypeId, Integer times) throws IOException {
        String url_json="http://localhost:8080//recipe/searchRecipeFromTagId.do?recipeTypeId="+recipeTypeId+"&times="+times;
        OkHttpClient client=new OkHttpClient();
        Request request= new Request.Builder().url(url_json).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result=response.body().string();
        Gson gson =new Gson();
        List<MenuResult.ResultBean.DataBean> MenuList=gson.fromJson(result, new TypeToken<List<MenuResult.ResultBean.DataBean>>() {}.getType());
        return MenuList;
    }


    /**
     * 根据食谱的id查询食谱
     * @param recipeId
     * @return
     * @throws IOException
     */
    public MenuResult getMenuIDALL(String recipeId) throws IOException {
        String url_json="http://localhost:8080//recipe/searchRecipeFromRecipeId.do?recipeId="+recipeId;
        OkHttpClient client=new OkHttpClient();
        Request request= new Request.Builder().url(url_json).build();
        Response response = null;

        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result=response.body().string();
        Gson gson =new Gson();
        MenuResult menuResult=gson.fromJson(result,MenuResult.class);
        return menuResult;
    }

}
