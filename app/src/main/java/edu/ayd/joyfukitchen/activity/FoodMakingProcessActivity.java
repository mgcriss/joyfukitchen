package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import edu.ayd.joyfukitchen.Adapter.FoodMakingProcessListViewAdapter;
import edu.ayd.joyfukitchen.bean.MenuResult;
import edu.ayd.joyfukitchen.dao.JsonDataDao;

/*
*点击那个菜，跳到制作过程
* */
public class FoodMakingProcessActivity extends AppCompatActivity {

    private MenuResult data;
    private JsonDataDao jsonDataDao;
    private String id;
    private FoodMakingProcessListViewAdapter adapter;
    private TextView fmp_title,fmp_ingredients;
    private ImageView fmp_titleimg;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_making_process);
        init();
    }

    private void init() {
        fmp_title = (TextView) findViewById(R.id.fmp_title);
        fmp_ingredients = (TextView) findViewById(R.id.fmp_ingredients);
        fmp_titleimg = (ImageView) findViewById(R.id.fmp_titleimg);
        listView = (ListView) findViewById(R.id.fmp_listView);
        data = new MenuResult();
        jsonDataDao = new JsonDataDao();
        Intent intent = getIntent();
        id = intent.getStringExtra("scid");
        new foodAsyncTask().execute();
    }

    class foodAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                data = jsonDataDao.getMenuIDALL(id);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object result) {
            //设置界面，我还没有完成，帮我写一下
            if (data != null) {
                fmp_title.setText(data.getResult().getData().get(0).getTitle());
                fmp_ingredients.setText(data.getResult().getData().get(0).getIngredients());
                Picasso.with(getApplicationContext()).load(data.getResult().getData().get(0).getAlbums().get(0)).into(fmp_titleimg);
                adapter = new FoodMakingProcessListViewAdapter(getApplicationContext(),data);
                listView.setAdapter(adapter);
            }

        }
    }
}
