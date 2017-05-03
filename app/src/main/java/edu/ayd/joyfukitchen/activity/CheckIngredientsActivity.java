package edu.ayd.joyfukitchen.activity;

import android.app.SearchManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MyFoodDetailsRecyclerViewAdapter;
import edu.ayd.joyfukitchen.Adapter.MySearchRecentSuggestionAdapter;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;
import edu.ayd.joyfukitchen.provider.MySearchRecentSuggestionsProvider;

/**
 * Created by Administrator on 2017/5/2.
 */

public class CheckIngredientsActivity extends BaseActivity {
    //view
   private RecyclerView rv_food_details;

    //else
    private MySearchRecentSuggestionsProvider mySuggestionProvider;
    private SearchRecentSuggestions suggestions;
    private MySearchRecentSuggestionAdapter mySearchRecentSuggestionAdapter;

    //data
    private List<FoodNutrition> datas = new ArrayList<FoodNutrition>();

    //adapter
    static MyFoodDetailsRecyclerViewAdapter myFoodDetailsRecyclerViewAdapter;


    //Constants
    public static String REQUESTCODE = "request_code";
    //更新数据请求
    public static final int UPDATEDATAS = 0;

    //handler
    private final Handler mHandler = new MyHandler(this);


    private static class MyHandler extends Handler {

        private final WeakReference<CheckIngredientsActivity> mActivity;

        public MyHandler(CheckIngredientsActivity activity) {
            mActivity = new WeakReference<CheckIngredientsActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case UPDATEDATAS : myFoodDetailsRecyclerViewAdapter.notifyDataSetChanged();break;
                default:;break;
            }
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super.setStatusBarTrans();
        setContentView(R.layout.layout_food_details);
        init();
        setListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        setupSearchView(searchItem);
//        setMenuBackground();

        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*search_food.requestFocus();
        //添加提交按钮
        search_food.setSubmitButtonEnabled(true);*/

        //展开(可输入状态)
//        search_food.onActionViewExpanded();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //监听返回键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            backToFoodClassIficationActivity();
        }
        return false;
    }

    private void init() {
        //初始化控件
        rv_food_details = (RecyclerView) findViewById(R.id.rv_food_details);
        //初始化MySearchRecentSuggestionsProvider访问类
        suggestions = new SearchRecentSuggestions(this,
                MySearchRecentSuggestionsProvider.AUTHORITY, MySearchRecentSuggestionsProvider.MODE);

        mySearchRecentSuggestionAdapter = new MySearchRecentSuggestionAdapter(this, null, 0);

        //以下显示食材名
        myFoodDetailsRecyclerViewAdapter = new MyFoodDetailsRecyclerViewAdapter(datas, this);
        rv_food_details.setAdapter(myFoodDetailsRecyclerViewAdapter);
        rv_food_details.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }


    private void setListener() {

    }


    public Cursor getRecentSuggestions(String query) {
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme(ContentResolver.SCHEME_CONTENT)
                .authority(MySearchRecentSuggestionsProvider.AUTHORITY);

        uriBuilder.appendPath(SearchManager.SUGGEST_URI_PATH_QUERY);

        String selection = " ?";
        String[] selArgs = new String[]{query};

        Uri uri = uriBuilder.build();

        return getContentResolver().query(uri, null, selection, selArgs, null);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }


    /**
     * 返回上一个页面
     */
    private void backToFoodClassIficationActivity() {
        Intent intent = new Intent(this, FoodClassIficationActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 存储搜索值
     */
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    MySearchRecentSuggestionsProvider.AUTHORITY, MySearchRecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);
        }
    }


    private void setupSearchView(MenuItem searchItem) {
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);


        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSuggestionsAdapter(mySearchRecentSuggestionAdapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = getRecentSuggestions(newText);
                mySearchRecentSuggestionAdapter.swapCursor(cursor);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(mySearchRecentSuggestionAdapter.getSuggestionText(position), true);
                return true;
            }
        });

        //判断参数FOOD_CLASS_NAME,上一个页面传过来的类型id
        final int food_class_name = getIntent().getIntExtra(REQUESTCODE, 0);
        Log.i("CheckIngredientsActivit", "setupSearchView: food_class_name = "+ food_class_name);
        //如果没有附带数据,则获取焦点 ，自动展开搜索框，弹出输入法
        if (food_class_name == 0) {
            //获取焦点
//            searchView.requestFocus();
            //展开搜索框
            //searchItem.expandActionView();  //这个无效
            searchView.onActionViewExpanded(); //这个有效
        } else {
            //如果有附带id数据,则查询出数据并显示在页面
            new Thread(){
                @Override
                public void run() {
                    FoodNutritionDao foodNutritionDao = new FoodNutritionDao(CheckIngredientsActivity.this);
                    List<FoodNutrition> foodNutritions = foodNutritionDao.showFoodById(food_class_name);
                    datas.clear();
                    datas.addAll(foodNutritions);
                    Log.i("CheckIngredientsActivit", "run: foodNutritions.size="+foodNutritions.size());
                    Message message = mHandler.obtainMessage(CheckIngredientsActivity.UPDATEDATAS);
                    mHandler.sendMessage(message);
                }
            }.start();

        }


    }


    protected void setMenuBackground() {

        getLayoutInflater().setFactory(new LayoutInflater.Factory() {

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {

                if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")) {

                    try { // Ask our inflater to create the view
                        LayoutInflater f = getLayoutInflater();
                        final View view = f.createView(name, null, attrs);
                            /*
                             * The background gets refreshed each time a new item is added the options menu.
                             * So each time Android applies the default background we need to set our own
                             * background. This is done using a thread giving the background change as runnable
                             * object
                             */
                        new Handler().post(new Runnable() {
                            public void run() {
                                view.setBackgroundColor(getResources().getColor(R.color.white));
                            }
                        });
                        return view;
                    } catch (InflateException e) {
                    } catch (ClassNotFoundException e) {
                    }
                }
                return null;
            }
        });
    }
}
