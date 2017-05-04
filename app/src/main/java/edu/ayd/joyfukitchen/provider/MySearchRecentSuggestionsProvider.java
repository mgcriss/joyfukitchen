package edu.ayd.joyfukitchen.provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by Administrator on 2017/5/1.
 * 用于保存和查询搜索记录。
 */

public class MySearchRecentSuggestionsProvider extends SearchRecentSuggestionsProvider {


    public final static String AUTHORITY = "edu.ayd.joyfukitchen.provider.MySearchRecentSuggestionsProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public MySearchRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
