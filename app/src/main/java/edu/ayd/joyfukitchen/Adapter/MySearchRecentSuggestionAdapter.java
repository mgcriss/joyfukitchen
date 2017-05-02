package edu.ayd.joyfukitchen.Adapter;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.ayd.joyfukitchen.activity.R;

/**
 * Created by Administrator on 2017/5/1.
 */

public class MySearchRecentSuggestionAdapter extends CursorAdapter {

    //构造器
    public MySearchRecentSuggestionAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public MySearchRecentSuggestionAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_suggest, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.historyText.setText(
                cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1)));
    }

    public String getSuggestionText(int position) {
        if (position >= 0 && position < getCursor().getCount()) {
            Cursor cursor = getCursor();
            cursor.moveToPosition(position);
            return cursor.getString(cursor.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));
        }
        return null;
    }


    //ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView historyText;

        public ViewHolder(View view) {
            super(view);
            historyText = (TextView) view.findViewById(R.id.search_item_text);

        }
    }
}
