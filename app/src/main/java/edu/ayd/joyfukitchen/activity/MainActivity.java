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
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import edu.ayd.joyfukitchen.Adapter.ElementRecycleViewAdapter;
import edu.ayd.joyfukitchen.bean.FoodElement;
import edu.ayd.joyfukitchen.service.BluetoothService;
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
    //圆圈中间的textView
    private TextView unit_ke;
    //中间circleProgressBar
    private CircularProgressBar circleProgressBar_index;
    //下一步
    private Button btn_next;


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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_index);
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
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {

        }
    }

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

        decimalFormat = new DecimalFormat("##.00");

        //获取屏幕亮度管理
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");

        //设置点击事件
        weekCalendar.setOnDateClickListener(new WeekCalendar.OnDateClickListener() {
            @Override
            public void onDateClick(String s) {
                //s为“yyyy-MM-dd”格式的日期字符串
                ToastUtil.show(MainActivity.this, s);
            }
        });


        //设置recycleView测试数据
        List<FoodElement> foodElements = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FoodElement foodElement = new FoodElement();
            foodElement.setElementName("天地一哈哈");
            foodElement.setElementValue("150mg");
            foodElements.add(foodElement);
        }


        //设置RecycleView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_element.setLayoutManager(linearLayoutManager);
        rv_element.setAdapter(new ElementRecycleViewAdapter(this, foodElements));

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rv_food.setLayoutManager(linearLayoutManager1);
        rv_food.setAdapter(new ElementRecycleViewAdapter(this, foodElements));


        //X轴的测试数据
        List<String> xValue = new ArrayList<String>();
        xValue.add("能量");
        xValue.add("脂肪");
        xValue.add("蛋白质");
        List<Float> data1 = new ArrayList<Float>();
        data1.add(1500f);
        data1.add(1200f);
        data1.add(500f);
        List<Float> data2 = new ArrayList<Float>();
        data2.add(200f);
        data2.add(1200f);
        data2.add(800f);


        setChartData(data1, data2, xValue);


        final Random random = new Random();
        //设置下一步事件 测试用
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int v1 = random.nextInt(5000);
                Log.i(TAG, "onClick: v1 = " + v1);
                setShowWeightData(v1 + "");
            }
        });

    }


    /**
     * BarChart设置数据
     *
     * @param data1:第一列的值 也就是推荐摄入量的值
     *                    data2： 第二列的值 就是实际摄入量的值
     *                    element: 元素的名称（蛋白质  脂肪等等）,只能有三个
     */
    public void setChartData(List<Float> data1, List<Float> data2, final List<String> element) {

        //Y轴的测试数据
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();

        //设置数据
        for (int i = 0; i < data1.size(); i++) {
            entriesGroup1.add(new BarEntry(i, data1.get(i)));
            entriesGroup2.add(new BarEntry(i, data2.get(i)));
        }


        BarDataSet set1 = new BarDataSet(entriesGroup1, "推荐摄入量");
        set1.setColor(getResources().getColor(R.color.barChart1_color));
        BarDataSet set2 = new BarDataSet(entriesGroup2, "实际摄入量");
        set2.setColor(getResources().getColor(R.color.barChart2_color));


        float groupSpace = 0.55f;
        float barSpace = 0.02f; // 间隙
        float barWidth = 0.2f; // 宽度

        BarData data = new BarData(set1, set2);
        data.setBarWidth(barWidth); // set the width of each bar


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

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                Log.i(TAG, "getFormattedValue: value=" + value);
                String v = "a";
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
                return v;
            }

        });

        chart.setData(data);
        chart.setNoDataText(getResources().getString(R.string.table_empty_text));//表格为空显示的文本
        chart.setTouchEnabled(false);//禁用交互
        chart.setDescription(null);//设置描述为空
        chart.groupBars(0f, groupSpace, barSpace); // perform the "explicit" grouping
        chart.invalidate(); // 刷新
    }


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
    private class LazyCallback implements BluetoothAdapter.LeScanCallback {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            //Log.i("开始查询","LazyCallback");
            String name = bluetoothDevice.getName();
            String address = bluetoothDevice.getAddress();
            if (address != null) {
                Log.i("查到的地址:", address);
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





