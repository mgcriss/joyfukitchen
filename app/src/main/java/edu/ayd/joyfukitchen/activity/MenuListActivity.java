package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import edu.ayd.joyfukitchen.Adapter.MenuLlistViewAdapter;
import edu.ayd.joyfukitchen.bean.MenuResult;
import edu.ayd.joyfukitchen.dao.JsonDataDao;
import edu.ayd.joyfukitchen.util.EmptyUtils;

public class MenuListActivity extends BaseActivity {
    private ListView listView;
    private MenuLlistViewAdapter adapter;
    private List<MenuResult.ResultBean.DataBean> datalist ;
    private JsonDataDao jsonDataDao;
    private Handler handler = null;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        getSupportActionBar().hide();
        setStatusBarTrans();
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
        //设置适配器
        adapter = new MenuLlistViewAdapter(MenuListActivity.this,datalist);
        listView.setAdapter(adapter);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        Toast.makeText(getApplicationContext(),id+"传过来的ID",Toast.LENGTH_SHORT).show();
        new Fooasyc().execute();

    }





    class Fooasyc extends AsyncTask {
        @Override
        protected Object doInBackground(Object[] objects) {
            try {
                if(EmptyUtils.isNotEmpty(datalist)) {
                    datalist.clear();
                }
                datalist.addAll(jsonDataDao.getMenuTypeIDALL(id,0));
                Log.i("data", "doInBackground: +++"+datalist);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Object result) {

            adapter.notifyDataSetChanged();
        }
    }



}

