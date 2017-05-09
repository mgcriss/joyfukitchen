package edu.ayd.joyfukitchen.activity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.loonggg.weekcalendar.view.WeekCalendar;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.ayd.joyfukitchen.Adapter.ElementRecycleViewAdapter;
import edu.ayd.joyfukitchen.Adapter.MyRecordsRecyclerViewAdapter;
import edu.ayd.joyfukitchen.bean.FoodNutrition;
import edu.ayd.joyfukitchen.bean.FoodNutritrion_sub;
import edu.ayd.joyfukitchen.bean.OnceRecord;
import edu.ayd.joyfukitchen.bean.User;
import edu.ayd.joyfukitchen.bean.WeightRecord;
import edu.ayd.joyfukitchen.dao.FoodNutritionDao;
import edu.ayd.joyfukitchen.dao.OnceRecordDao;
import edu.ayd.joyfukitchen.service.BluetoothService;
import edu.ayd.joyfukitchen.util.EmptyUtils;
import edu.ayd.joyfukitchen.util.ToastUtil;
import edu.ayd.joyfukitchen.util.WeightUtil;
import edu.ayd.joyfukitchen.view.DiyTableView;

import static android.content.ContentValues.TAG;

public class MainActivity extends BaseActivity {

    //Manager
    private PowerManager.WakeLock mWakeLock;
    private DecimalFormat decimalFormat;

    //requestCODE
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 1;

    //view
    private WeekCalendar weekCalendar;
    private TextView tv_ke;
    private DiyTableView tb_liang;
    private DiyTableView tb_anshi;
    private DiyTableView tb_bang;
    private DiyTableView tb_haosheng;
    private BarChart chart;
    private RecyclerView rv_element;
    private RecyclerView rv_food;
    private LinearLayout menu;
    private LinearLayout ingredients;
    private LinearLayout personalData;
    private LinearLayout show_user_name;
    private TextView username;




    //圆圈中间的textView
    private TextView unit_ke;
    //中间circleProgressBar
    private CircularProgressBar circleProgressBar_index;
    private SimpleDateFormat dateFormat;

    private LinearLayout weight_weight;
    //下一步
    private Button btn_next;

    //data
    //设置recycleView测试数据
    private List<FoodNutritrion_sub> foodNutritrionSubs = new ArrayList<FoodNutritrion_sub>();
    //推荐摄入量
    private List<Float> data1 = new ArrayList<Float>();
    //实际摄入量
    private List<Float> data2 = new ArrayList<Float>();
    //关注元素
    private List<String> element = new ArrayList<String>();
    //x轴元素名
    private List<String> xValue = new ArrayList<String>();
    //Method
    private List<Method> methods = new ArrayList<Method>();
    //数据
    private Map<String, List<FoodNutritrion_sub>> charDatas = new HashMap<String, List<FoodNutritrion_sub>>();


    //获取的首页要显示的数据
    private Collection<WeightRecord> weightRecords = null;
    //
    private List<OnceRecord> onceRecords = new ArrayList<OnceRecord>();


    private static final int UPDATECHARTDATAS = 7;
    private static final int UPDATERECYCLER1DATAS = 8;


    /*蓝牙所需*/
    private BluetoothAdapter.LeScanCallback lazyCallback;
    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress = null;
    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics = new ArrayList();
    private BluetoothGattCharacteristic mNotifyCharacteristic;
    private BluetoothService mBluetoothLeService;
    private static final int REQUEST_ENABLE_BT = 0;
    //默认动画时长
    private int animationDuration = 2500; // 2500ms = 2,5s

    //Adapter
    private ElementRecycleViewAdapter elementRecycleViewAdapter;
    private MyRecordsRecyclerViewAdapter myRecordsRecyclerViewAdapter;


    //handler
    private final Handler mHandler = new MyHandler(this);

    private class MyHandler extends Handler {

        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<MainActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                //更新图表数据
                case UPDATECHARTDATAS: {
                    setChartData(data1, data2);
                    changeElementData();
                }
                ;
                break;
                case UPDATERECYCLER1DATAS: {
                    elementRecycleViewAdapter.notifyDataSetChanged();
                    Log.i(TAG, "handleMessage: 更新下面数据: " + (onceRecords == null ? 0 : onceRecords.size()));
                    myRecordsRecyclerViewAdapter.notifyDataSetChanged();
                }
                ;
                break;

                default:
                    ;
                    break;
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_index);
        //隐藏标题栏
        getSupportActionBar().hide();
        setStatusBarTrans();
        //打开蓝牙
        BluetoothManager bluetoothManager = (BluetoothManager) MainActivity.this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        //判断是否有权限,判断dangerous permission
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)

            //请求权限
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
        //判断是否需要 向用户解释，为什么要申请该权限
        ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_CONTACTS);


        init();

        //菜单页面跳转
        //TODO
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, );
//                startActivity(intent);
//            }
//        });

        //食材页面跳转
        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodClassIficationActivity.class);
                startActivity(intent);
            }
        });

        View.OnClickListener clickPerson = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = ((MyApplication) getApplication()).getUser();
                if(EmptyUtils.isEmpty(user)) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(MainActivity.this, PersonCenterActivity.class);
                    startActivity(intent);
                }
            }
        };

        //个人信息页面跳转
        personalData.setOnClickListener(clickPerson);
        //头部头像用户名点击跳转
        show_user_name.setOnClickListener(clickPerson);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {

        }
    }


    //初始化
    public void init() {
        weekCalendar = (WeekCalendar) findViewById(R.id.week_calendar);
        tv_ke = (TextView) findViewById(R.id.unit_ke);
        tb_bang = (DiyTableView) findViewById(R.id.unit_bang);
        tb_liang = (DiyTableView) findViewById(R.id.unit_liang);
        tb_anshi = (DiyTableView) findViewById(R.id.unit_anshi);
        tb_haosheng = (DiyTableView) findViewById(R.id.unit_haosheng);
        chart = (BarChart) findViewById(R.id.barchart);
        rv_element = (RecyclerView) findViewById(R.id.rv_element_content);
        rv_food = (RecyclerView) findViewById(R.id.rv_food_material_history);
        unit_ke = (TextView) findViewById(R.id.unit_ke);
        circleProgressBar_index = (CircularProgressBar) findViewById(R.id.circleProgressBar_index);
        btn_next = (Button) findViewById(R.id.btn_next);
        weight_weight = (LinearLayout) findViewById(R.id.weight_weight);
        menu = (LinearLayout) findViewById(R.id.menu);
        ingredients = (LinearLayout) findViewById(R.id.ingredients);
        personalData = (LinearLayout) findViewById(R.id.personalData);
        show_user_name = (LinearLayout) findViewById(R.id.show_user_name);
        username = (TextView) findViewById(R.id.username);

        decimalFormat = new DecimalFormat("##.00");
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        //获取屏幕亮度管理
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

        //日历设置点击事件
        weekCalendar.setOnDateClickListener(new WeekCalendar.OnDateClickListener() {
            @Override
            public void onDateClick(String s) {
                //s为“yyyy-MM-dd”格式的日期字符串
                ToastUtil.show(MainActivity.this, s);
                String format = dateFormat.format(Calendar.getInstance().getTime());
                //如果日期不相等,则隐藏称重
                if (!s.equalsIgnoreCase(format)) {
                    weight_weight.setVisibility(View.GONE);
                } else {
                    weight_weight.setVisibility(View.VISIBLE);
                }
                queryAndSetData(s);
            }
        });


        //查询保存的User信息
        User user = ((MyApplication) this.getApplication()).getUser();
        //如果有数据则用用户关注的数据,没有则用默认的
        if(EmptyUtils.isNotEmpty(user)){

        }

        //再次判断如果为空则添加默认数据
        if(EmptyUtils.isEmpty(element)) {
            //设置图标x轴的默认数据
            element.add("Df");
            element.add("Fat");
            element.add("Protein");
        }



        //设置RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_element.setLayoutManager(linearLayoutManager);
        elementRecycleViewAdapter = new ElementRecycleViewAdapter(this, foodNutritrionSubs);
        rv_element.setAdapter(elementRecycleViewAdapter);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_food.setLayoutManager(linearLayoutManager1);
        myRecordsRecyclerViewAdapter = new MyRecordsRecyclerViewAdapter(this, onceRecords);
        myRecordsRecyclerViewAdapter.setOnItemClickListener(new MyRecordsRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //TODO
                Intent intent = new Intent(MainActivity.this, RecordDetailsActivity.class);
                ToastUtil.show(MainActivity.this,"点击了记录" + position);
                intent.putExtra("data", (onceRecords == null ? null : onceRecords.get(position)));
                startActivity(intent);
            }
        });
        rv_food.setAdapter(myRecordsRecyclerViewAdapter);


        //X轴的测试数据
//        List<Float> data1 = new ArrayList<Float>();
//        data1.add(1500f);
//        data1.add(1200f);
//        data1.add(500f);
//        List<Float> data2 = new ArrayList<Float>();
//        data2.add(200f);
//        data2.add(1200f);
//        data2.add(800f);

        //设置下一步事件
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodClassIficationActivity.class);
                intent.putExtra("weight", unit_ke.getText().toString());
                startActivity(intent);
            }
        });

        initBarChar();
        queryXDATA();
        setXDATA(xValue);

        setChartData(data1, data2);


//        queryAndSetData(dateFormat.format(new Date()));


    }

    /**
     * 根据关注元素查询x轴的显示名
     */
    private void queryXDATA() {

        //反射获取get方法
        Class<? extends FoodNutrition> foodNutritionClass = FoodNutrition.class;
        for (String s : element) {
            try {
                Method method = foodNutritionClass.getMethod("get" + s);
                methods.add(method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }


        for (int i = 0; i < element.size(); i++) {
            Field field = null;
            String name = null;
            try {
                field = R.string.class.getField(element.get(i));
                int anInt = field.getInt(new R.string());
                name = getResources().getString(anInt);
                Log.i(TAG, "queryXDATA: 获得的关注元素的名称为: " + name);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                Log.e(TAG, "queryXDATA: ", e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, "queryXDATA: ", e);
            }
            xValue.add(name);
        }
    }


    //初始化柱状图
    private void initBarChar() {


        //设置
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置
        chart.getXAxis().setDrawGridLines(false);//x轴不显示网格
        chart.getAxisLeft().setAxisMinValue(0.0f);//设置Y轴显示最小值，不然0下面会有空隙
        chart.getXAxis().setDrawLabels(true);//设置绘制x轴线
        chart.getAxisRight().setEnabled(false);//右侧不显示Y轴
        chart.getAxisLeft().setDrawGridLines(false);//不显示Y轴网格
        chart.getXAxis().setAxisMinimum(0);//设置X轴显示最小值为0
        chart.getXAxis().setAxisMaximum(3f);//设置X轴最大值为3
        chart.getXAxis().setLabelCount(3);//设置X轴标签个数
        chart.getXAxis().setCenterAxisLabels(true);//设置x轴标签居中显示
        chart.setNoDataText(getResources().getString(R.string.table_empty_text));//表格为空显示的文本
        chart.setTouchEnabled(false);//禁用交互
        chart.setDescription(null);//设置描述为空
    }


    /**
     * BarChart设置数据
     *
     * @param data1:第一列的值 也就是推荐摄入量的值
     *                    data2： 第二列的值 就是实际摄入量的值
     */
    public void setChartData(List<Float> data1, List<Float> data2) {

        //柱状图Y轴的数据
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();


        if (data1 != null && data2 != null) {

            if (data1.size() > 0 && data1.size() == data2.size()) {
                for (int i = 0; i < data1.size(); i++) {
                    entriesGroup1.add(new BarEntry(i, data1.get(i)));
                    entriesGroup2.add(new BarEntry(i, data2.get(i)));
                }
            }

        }


        BarDataSet set1 = new BarDataSet(entriesGroup1, "推荐摄入量");
        set1.setColor(getResources().getColor(R.color.barChart1_color));
        BarDataSet set2 = new BarDataSet(entriesGroup2, "实际摄入量");
        set2.setColor(getResources().getColor(R.color.barChart2_color));
        BarData data = new BarData(set1, set2);
        float barWidth = 0.2f; // 柱子宽度
        data.setBarWidth(barWidth); // set the width of each bar

        try {
            chart.setData(data);
        } catch (Exception e) {
            Log.e(TAG, "setChartData: ", e);
        }
        float groupSpace = 0.55f;
        float barSpace = 0.02f; // 间隙
        try {
            chart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping
        } catch (Exception e) {
            Log.e(TAG, "setChartData: ", e);
        }
        chart.invalidate(); // 刷新
    }


    //设置x轴文本
    private void setXDATA(final List<String> element) {
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                Log.i(TAG, "getFormattedValue: value=" + value);
                String v = "NODATA";
                if (EmptyUtils.isNotEmpty(element)) {
                    switch ((int) value) {
                        case 0:
                            v = element.get(0);
                            break;
                        case 1:
                            v = element.get(1);
                            break;
                        case 2:
                            v = element.get(2);
                            break;
                        default:
                            break;
                    }
                }
                return v;
            }

        });
        chart.invalidate(); // 刷新

    }


    /**
     * 查询数据处理数据(柱状图)
     */
    private void queryAndSetData(String date) {

        final OnceRecordDao onceRecordDao = new OnceRecordDao(this);
        Date cdate = null;


        try {
            cdate = dateFormat.parse(date);
            Log.i(TAG, "queryAndSetData: cdate = " + cdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (EmptyUtils.isNotEmpty(cdate)) {
            final Date finalCdate = cdate;
            //开启线程查询数据库,
            new Thread() {
                @Override
                public void run() {
                    if (EmptyUtils.isNotEmpty(data1)) {
                        data1.clear();
                    }
                    if (EmptyUtils.isNotEmpty(data2)) {
                        data2.clear();
                    }
                    if (EmptyUtils.isNotEmpty(charDatas)) {
                        charDatas.clear();
                    }
                    if (EmptyUtils.isNotEmpty(onceRecords)) {
                        onceRecords.clear();
                    }
                    //需要的数据,当日数据
                    List<OnceRecord> onceRecs = onceRecordDao.queryForDate(finalCdate);
                    if (EmptyUtils.isNotEmpty(onceRecs)) {
                        try {
                            MainActivity.this.onceRecords.addAll(onceRecs);
                        }catch (Exception e){
                            Log.e(TAG, "run: 将查询出来的记录集合添加到onceRecords ", e);
                        }
                    }
                    //全部数据, 用来测试
//                    List<OnceRecord> onceRecords1 = onceRecordDao.showRecords();
                    //处理数据 打印日志
                    if (MainActivity.this.onceRecords != null) {
                        Log.i(TAG, "queryAndSetData: 查询数据= " + MainActivity.this.onceRecords);
//                        Log.i(TAG, "queryAndSetData: 查询所有数据 = " + onceRecords1);


                        for (OnceRecord onceRec : MainActivity.this.onceRecords) {
                            weightRecords = onceRec.getWeightRecords();
                            if (EmptyUtils.isEmpty(weightRecords)) {
                                Log.i(TAG, "run: " + dateFormat.format(onceRec.getRecordTime()) + "没有称重记录");
                            }

                            //临时存放数据
                            List<FoodNutritrion_sub> fsubs = new ArrayList<FoodNutritrion_sub>();

                            //处理数据,整理出柱状图需要的数据
                            for (WeightRecord w : weightRecords) {
                                Log.i(TAG, "run: 输出 " + dateFormat.format(onceRec.getRecordTime()) + " 获取的称重记录: " + w);
                                if (EmptyUtils.isEmpty(w)) {
                                    continue;
                                }


                                //根据称重记录保存的foodId查询食材
                                FoodNutritionDao foodNutritionDao = new FoodNutritionDao(MainActivity.this);
                                FoodNutrition foodNutrition = foodNutritionDao.showFoodByFoodId(w.getFoodId());


                                //获取关注的三个元素
                                for (int i = 0; i < methods.size(); i++) {
                                    Method m = methods.get(i);
                                    try {
                                        //调用get方法获取重量数据
                                        Float weight = (Float) m.invoke(foodNutrition) * w.getWeight() / 100;
                                        Log.i(TAG, "run: 获取到的重量数据为: " + weight);
                                        if (m.getName().equalsIgnoreCase("get" + element.get(i))) {
                                            try {
                                                Float aFloat = data2.get(i);

                                                aFloat = aFloat + weight;
                                                data2.set(i, aFloat);
                                                Log.i(TAG, "run: 获取称重值" + aFloat);
                                            } catch (Exception e) {
                                                data2.add(weight);
                                                Log.i(TAG, "run: 没有记录,添加记录: " + weight);
                                            }
                                        }

                                    } catch (IllegalAccessException e) {
                                        e.printStackTrace();
                                    } catch (InvocationTargetException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }


                        //推荐摄入量的测试数据
                        data1.add(100f);
                        data1.add(100f);
                        data1.add(100f);

                        Log.i(TAG, "run:data1 = " + data1 + " data2 = " + data2);
                        //执行完毕handler发送消息更新数据
                        Message message = mHandler.obtainMessage(UPDATECHARTDATAS);
                        mHandler.sendMessage(message);
                    }


                }

            }.start();
        }

    }

    /**
     * 设置下面Horizontal RecyclerView的数据显示
     */
    private void changeElementData() {
        new Thread() {
            @Override
            public void run() {
                try {
                    if (EmptyUtils.isNotEmpty(foodNutritrionSubs)) {
                        foodNutritrionSubs.clear();
                    }
                    for (OnceRecord onceRec : onceRecords) {
                        weightRecords = onceRec.getWeightRecords();
                        if (EmptyUtils.isEmpty(weightRecords)) {
                            Log.i(TAG, "run: " + dateFormat.format(onceRec.getRecordTime()) + "没有称重记录");
                        }
                        for (WeightRecord w : onceRec.getWeightRecords()) {
                            Log.i(TAG, "run: 输出 " + dateFormat.format(onceRec.getRecordTime()) + " 获取的称重记录: " + w);
                            if (EmptyUtils.isEmpty(w)) {
                                continue;
                            }
                            //获取该次重量记录
                            Float weight = w.getWeight();
                            //根据称重记录保存的foodId查询食材
                            FoodNutritionDao foodNutritionDao = new FoodNutritionDao(MainActivity.this);
                            FoodNutrition foodNutrition = foodNutritionDao.showFoodByFoodId(w.getFoodId());
                            Class<? extends FoodNutrition> foodNutritionClass = foodNutrition.getClass();
                            Method[] methods = foodNutritionClass.getMethods();

                            int i = 0;
                            Log.i(TAG, "run: 第" + i + "次 : foodNutritrionSubs.size = " + foodNutritrionSubs.size());

                            for (Method method : methods) {
                                String methodName = method.getName();
                                //如果该方法以 _unit 结尾，则判断为是一个元素,获取相关的值
                                if (methodName.endsWith("_unit") && methodName.startsWith("get")) {

                                    try {
                                        FoodNutritrion_sub foodNutritrion_sub = foodNutritrionSubs.get(i);
                                    } catch (Exception e) {
                                        Log.e(TAG, "run: ", e);
                                        foodNutritrionSubs.add(new FoodNutritrion_sub());
                                    }


                                    try {
                                        FoodNutritrion_sub foodNutritrion_sub = foodNutritrionSubs.get(i);
                                        //调用该方法获取单位
                                        String unitName = (String) method.invoke(foodNutrition);
                                        //字符串截取获取获取数量值的方法
                                        String substring = methodName.substring(0, methodName.indexOf("_"));
                                        Method method1 = foodNutritionClass.getMethod(substring);
                                        Float f = (Float) method1.invoke(foodNutrition);
                                        //字符串截取获取元素名
                                        String name = methodName.substring(3, methodName.indexOf("_"));
                                        //设置值,并添加到list
                                        foodNutritrion_sub.setName(name);
                                        foodNutritrion_sub.setUnitName(unitName);
                                        foodNutritrion_sub.setHanLiang(f);
                                        float curHanLiang = foodNutritrion_sub.getHanLiang() * weight / 100;
                                        Float curHanLiang1 = foodNutritrion_sub.getCurHanLiang();
                                        if (curHanLiang1 == null) {
                                            curHanLiang1 = 0f;
                                        }
                                        foodNutritrion_sub.setCurHanLiang(curHanLiang + curHanLiang1);
                                        Log.i(TAG, "run: 当前含量 : = " + (curHanLiang + curHanLiang1));
                                        foodNutritrionSubs.set(i, foodNutritrion_sub);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        i++;
                                    }
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                    Log.e(TAG, "run: ", e);
                }

                Log.i(TAG, "run: foodNutritrionSubs的长度 = " + foodNutritrionSubs.size());
                Message message = mHandler.obtainMessage(UPDATERECYCLER1DATAS);
                mHandler.sendMessage(message);
            }
        }.start();
    }

    //最下面那个显示称量记录的RecyclerView
//    private void changeFoodData() {
//
//    }


    /**
     * 设置显示的textView的值，并自动设置CircleProgressBar进度
     */
    public void setShowWeightData(String data) {


        //计算比例(最大值5000)
        float f = 0;
        try {
            f = Float.parseFloat(data);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "setShowWeightData: float = " + f + "数值字符串转float转换错误");
        }
        //计算进度
        float progress = f / 5000 * 100;
        Log.i(TAG, "setShowWeightData: f = " + f);
        //给ProgressBar设置进度,自带动画的
        circleProgressBar_index.setProgressWithAnimation(progress, animationDuration);

        //给显示的TextView设置值 unit_g
        int g = (int) f;
        unit_ke.setText(g + "");
        //给其他单位设置值
        String liang = null;
        String anshi = null;
        String bang = null;
        String haosheng = null;
        try {
            liang = decimalFormat.format(WeightUtil.toLiang(f)).toString();
            anshi = decimalFormat.format(WeightUtil.toAnShi(f)).toString();
            bang = decimalFormat.format(WeightUtil.toBang(f)).toString();

            //毫升的液体暂时密度按1来算
            haosheng = decimalFormat.format(WeightUtil.toHaoSheng(f, 1f)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "setShowWeightData: exception " + e);
        }

        tb_liang.setBottomText(liang);
        tb_anshi.setBottomText(anshi);
        tb_bang.setBottomText(bang);
        tb_haosheng.setBottomText(haosheng);
    }


    /*-----------------------蓝牙----------------------------*/
    // 代码管理服务生命周期
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        //服务保持连接
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.i(TAG, "无法初始化蓝牙");
            }

            Toast.makeText(MainActivity.this, "连接设备", Toast.LENGTH_LONG).show();
            // 自动连接到设备成功启动初始化。
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "无法连接");
            mBluetoothLeService = null;
        }
    };

    //广播
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothService.ACTION_GATT_CONNECTED.equals(action)) {
                Toast.makeText(context, "连接成功", Toast.LENGTH_LONG).show();
            } else if (BluetoothService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Toast.makeText(context, "断开连接", Toast.LENGTH_LONG).show();
            } else if (BluetoothService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // 显示所有用户界面上的支持服务和特色
                displayGattServices(mBluetoothLeService.getSupportedGattServices());
            } else if (BluetoothService.ACTION_DATA_AVAILABLE.equals(action)) {
                //String data = intent.getStringExtra(BluetoothService.EXTRA_DATA);

                String jstate = intent.getStringExtra(BluetoothService.JSTATE);
                String jdata = intent.getStringExtra(BluetoothService.JDATA);

                //给控件设置值
                setShowWeightData(jdata);

                Log.i("--状态----------------", jstate);
                Log.i("--数据----------------", jdata);
            }
        }
    };

    private void displayGattServices(List<BluetoothGattService> gattServices) {
        if (gattServices != null) {
            ArrayList<HashMap<String, String>> gattServiceData = new ArrayList();
            mGattCharacteristics = new ArrayList();
            for (BluetoothGattService gattService : gattServices) {
                HashMap<String, String> currentServiceData = new HashMap();
                String uuid = gattService.getUuid().toString();
                if (Objects.equals(uuid, "0000fff0-0000-1000-8000-00805f9b34fb")) {
                    currentServiceData.put("UUID", uuid);
                    gattServiceData.add(currentServiceData);
                    ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList();
                    List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
                    ArrayList<BluetoothGattCharacteristic> charas = new ArrayList();
                    for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                        charas.add(gattCharacteristic);
                        HashMap<String, String> currentCharaData = new HashMap();
                        currentCharaData.put("UUID", gattCharacteristic.getUuid().toString());
                        gattCharacteristicGroupData.add(currentCharaData);
                    }
                    mGattCharacteristics.add(charas);
                    if (mGattCharacteristics != null) {
                        BluetoothGattCharacteristic characteristic = (BluetoothGattCharacteristic) ((ArrayList) this.mGattCharacteristics.get(0)).get(3);
                        int charaProp = characteristic.getProperties();
                        if ((charaProp | 2) > 0) {
                            if (mNotifyCharacteristic != null) {
                                mBluetoothLeService.setCharacteristicNotification(this.mNotifyCharacteristic, false);
                                mNotifyCharacteristic = null;
                            }
                            mBluetoothLeService.readCharacteristic(characteristic);
                        }
                        if ((charaProp | 16) > 0) {
                            mNotifyCharacteristic = characteristic;
                            mBluetoothLeService.setCharacteristicNotification(characteristic, true);
                        }
                    }
                }
            }
        }
    }

    //懒加载回调
    class LazyCallback implements BluetoothAdapter.LeScanCallback {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            //Log.i("开始查询","LazyCallback");
            String name = bluetoothDevice.getName();
            String address = bluetoothDevice.getAddress();
            if (address != null) {
//                Log.i("查到的地址:", address);
                //7C:EC:79:55:63:29
            }
            if (name != null && "BIGCARE_BC301".equals(name)) {
                //String address2 = bluetoothDevice.getAddress();
                mDeviceAddress = bluetoothDevice.getAddress();
                Log.i("---查到的名字-----:", mDeviceAddress);

                mBluetoothAdapter.stopLeScan(lazyCallback);

                //Intent gattServiceIntent = new Intent(getActivity(), BluetoothService.class);
                boolean ble = MainActivity.this.getApplicationContext().bindService(new Intent(MainActivity.this.getApplication(), BluetoothService.class), mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务

                if (ble) {
                    Log.i("绑定成功", "dd");
                } else {
                    Log.i("绑定失败", "dd");
                }

                MainActivity.this.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());

            }

            /*try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart-1");

        Log.i("sssss", mDeviceAddress + "");
        if (mDeviceAddress != null) {
            Intent gattServiceIntent = new Intent(MainActivity.this, BluetoothService.class);
            if (mBluetoothLeService == null)
                MainActivity.this.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);//绑定服务
            MainActivity.this.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //添加唤醒锁,保持设备唤醒状态
        mWakeLock.acquire();
        queryAndSetData(dateFormat.format(Calendar.getInstance().getTime()));

        //检查有没有登录,更新username   TextView显示的值
        User user = ((MyApplication) getApplication()).getUser();
        //如果为空
        if(EmptyUtils.isEmpty(user)){
            username.setText(getResources().getString(R.string.username_null));
        } else {
            //如果不为空
            String name = user.getUsername();
            if(EmptyUtils.isEmpty(name)){
                username.setText(getResources().getString(R.string.no_username));
            }else {
                username.setText(name);
            }
        }

        Log.i(TAG, "onResume--1");

        Log.i("onresume", "什么事都没做");

        if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(MainActivity.this, "打开蓝牙成功", Toast.LENGTH_LONG).show();
            Log.i("打开蓝牙成功", "BluetoothConnection");
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Toast.makeText(MainActivity.this, "打开蓝牙后", Toast.LENGTH_LONG).show();

        if (lazyCallback == null) {
            Log.i("lazyCallback", "lazyCallback  new前");
            lazyCallback = new LazyCallback();
        }
        Log.i("lazyCallback", "new 后");


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    boolean d = mBluetoothAdapter.startLeScan(lazyCallback);
                    Log.i("扫描状态：", d + "");
                } catch (Exception e) {
                    Log.i("异常：", e.toString());
                    e.printStackTrace();
                }

            }
        }).start();

        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.i(TAG, "连接请求的结果=" + result);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause--1");

        //释放唤醒锁
        mWakeLock.release();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop--1");
        if (mDeviceAddress != null) {
            if (mBluetoothLeService.isRestricted()) {
                if (mServiceConnection != null) {
                    if (mBluetoothLeService != null)
                        MainActivity.this.unbindService(mServiceConnection);
                }
            }
        }

        if (mDeviceAddress != null && mGattCharacteristics != null) {
            MainActivity.this.unregisterReceiver(mGattUpdateReceiver);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy--1");


        if (mBluetoothAdapter != null) {
            mBluetoothAdapter.disable();
        }

        mBluetoothLeService = null;
        mBluetoothAdapter.stopLeScan(lazyCallback);
    }


}





