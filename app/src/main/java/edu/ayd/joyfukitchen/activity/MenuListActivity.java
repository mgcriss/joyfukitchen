package edu.ayd.joyfukitchen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MenuLlistViewAdapter;
import edu.ayd.joyfukitchen.bean.MenuResult;
import edu.ayd.joyfukitchen.dao.JsonDataDao;

public class MenuListActivity extends Activity {
    private ListView listView;
    private MenuLlistViewAdapter adapter;
    private List<MenuResult.ResultBean.DataBean> datalist ;
    private JsonDataDao jsonDataDao;
    private Handler handler = null;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);
        init();


    }

    private void init() {
        jsonDataDao = new JsonDataDao();
        listView = (ListView) findViewById(R.id.menu_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = datalist.get(i).getId();
                Intent intent = new Intent(getApplicationContext(),FoodMakingProcessActivity.class);
                intent.putExtra("scid",id);
                startActivity(intent);
            }
        });
       Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Toast.makeText(getApplicationContext(),id+"传过来的ID",Toast.LENGTH_LONG).show();
        new Fooasyc().execute();

    }





    class Fooasyc extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                datalist = jsonDataDao.getMenuTypeIDALL(id,0);
                adapter = new MenuLlistViewAdapter(MenuListActivity.this,datalist);
                Log.i("data", "doInBackground: +++"+datalist);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object result) {

            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }



}

