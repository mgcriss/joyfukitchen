package edu.ayd.joyfukitchen.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ayd.joyfukitchen.bean.RecipeType;
import edu.ayd.joyfukitchen.dao.JsonDataDao;

public class MenuClassificationActivity extends BaseActivity {
    private String toolsList[] = null;
    private int toolsImages[];
    private TextView toolsTextViews[];
    private View views[];
    private LinearLayout toolsLayout;
    private LayoutInflater layoutInflater;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter simpleAdapter;
    private GridView gridView;
    private String[] iconName;
    private String[] id;
    private Handler handler = null;

    private JsonDataDao jsonDataDao;
    private List<RecipeType> munutype;
    List<RecipeType.ListBean> list = null;
    private String TAG = "Menu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //隐藏标题栏
        getSupportActionBar().hide();
        setStatusBarTrans();
        setContentView(R.layout.activity_menu_classification);
        munutype = new ArrayList<RecipeType>();
        jsonDataDao= new JsonDataDao();

        init();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void init() {
        data_list = new ArrayList<Map<String, Object>>();
        gridView = (GridView) findViewById(R.id.nev_gridview);

        toolsLayout = (LinearLayout) findViewById(R.id.tools);
        layoutInflater = LayoutInflater.from(this);
        handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    munutype = jsonDataDao.getMenuTypes(null);
                    toolsList = new String[munutype.size()];
                    for (int i = 0; i < munutype.size(); i++) {
                        toolsList[i] = munutype.get(i).getName();
                        if (i==0) {
                            //iconName[i] =
                            list = new ArrayList<RecipeType.ListBean>();
                            list.addAll(munutype.get(i).getList());
                        }
                    }
                    iconName = new String[list.size()];
                    id = new String[iconName.length];
                    for (int i = 0; i < list.size(); i++) {
                        iconName[i] =  list.get(i).getName();
                        id[i] = list.get(i).getId();
                        Log.i("ssssss", "onClick: "+ iconName[i]);
                    }

                    if (iconName != null) {
                        handler.post(runnableUi);
                    }

                    if (toolsList != null) {
                        handler.post(runnable1);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("test+++++++++++++++id",id[i]);
                Toast.makeText(getApplicationContext(),id[i]+"",Toast.LENGTH_LONG).show();
                Log.i("test++++++++++iconName",iconName[i]);
                Toast.makeText(getApplicationContext(),iconName[i],Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(getApplicationContext(),MenuListActivity.class);
                intent1.putExtra("id",id[i]);
                startActivity(intent1);
            }
        });
    }

    Runnable runnable1 =  new Runnable() {
        @Override
        public void run() {
            showToolsView();
        }
    };

    Runnable runnableUi =  new Runnable() {
        @Override
        public void run() {
            gridviewSetData(iconName);
        }
    };

    Runnable runnablegridview =  new Runnable() {
        @Override
        public void run() {
            data_list.clear();
            simpleAdapter.notifyDataSetChanged();
            gridviewSetData(iconName);
        }
    };

    /**
     * 动态生成items中的textview
     */
    private void showToolsView() {
        if (toolsList != null) {
            toolsTextViews = new TextView[toolsList.length];
            views = new View[toolsList.length];
            for (int i = 0; i < toolsList.length; i++) {
                View view = layoutInflater.inflate(R.layout.item_nev_layout,null);
                view.setId(i);
                view.setOnClickListener(toolsitemListener);
                ImageView imageView = (ImageView)view.findViewById(R.id.nev_img);
                TextView textView = (TextView) view.findViewById(R.id.nev_text);
                imageView.setImageResource(R.mipmap.no_selected_meats);
                textView.setText(toolsList[i]);
                toolsLayout.addView(view);
                toolsTextViews[i] = textView;
                views[i] = view;
            }
            changeTextColor(0);
        }
    }

    private View.OnClickListener toolsitemListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int n = view.getId();

            Log.i("sssdfffsdfsaf",n+"");
            changeTextColor(n);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        munutype = jsonDataDao.getMenuTypes(null);
                        Log.i("menutype", "run: menutype"+munutype);

                        list = new ArrayList<RecipeType.ListBean>();

                        for (int i = 0; i < munutype.size(); i++) {
                            //if("菜系".equals(munutype.get(i).getName()))

                            int num = n+10001;
                            String str = num+"";

                            if(str.equals(munutype.get(i).getParentId()))
                                list.addAll(munutype.get(i).getList());
                        }

                        iconName = new String[list.size()];
                        id = new String[iconName.length];
                        for (int i = 0; i < list.size(); i++) {
                            iconName[i] =  list.get(i).getName();
                            id[i] = list.get(i).getId();
                            Log.i("ssssss", "onClick: "+ iconName[i]);
                        }

                        if (iconName != null) {
                            handler.post(runnablegridview);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();




        }
    };

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                views[i].setBackgroundResource(android.R.color.transparent);
                toolsTextViews[i].setTextColor(0xff999999);
            }
        }
        views[id].setBackgroundResource(R.color.backgroundColor);
        toolsTextViews[id].setTextColor(0xffffffff);
    }

    /**
     * arr :数据源
     * @param arr
     */
    private void gridviewSetData(String[] arr){
        try {
            getData(arr);
            String[] from = {"image", "text"};
            int[] to = {R.id.nev_img, R.id.nev_text};
            simpleAdapter = new SimpleAdapter(this, data_list, R.layout.item_nev_layout, from, to);
            gridView.setAdapter(simpleAdapter);
        }catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "gridviewSetData: ", e );
        }
    }

    public List<Map<String, Object>> getData(String [] arr){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<arr.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", R.mipmap.no_selected_eggs);
            Log.i("数据", "getData: "+arr[i]);
            map.put("text", arr[i]);
            data_list.add(map);
        }
        return data_list;
    }
}
