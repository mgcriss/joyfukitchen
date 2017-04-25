package edu.ayd.joyfukitchen.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.loonggg.weekcalendar.view.WeekCalendar;

import java.util.ArrayList;
import java.util.List;

import edu.ayd.joyfukitchen.Adapter.ElementRecycleViewAdapter;
import edu.ayd.joyfukitchen.bean.FoodElement;
import edu.ayd.joyfukitchen.util.ToastUtil;
import edu.ayd.joyfukitchen.view.DiyTableView;

import static android.content.ContentValues.TAG;

public class MainActivity extends BaseActivity {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weight_new);
        setStatusBarTrans();
        init();
    }

    public void init(){
        weekCalendar = (WeekCalendar) findViewById(R.id.week_calendar);
        tv_ke = (TextView) findViewById(R.id.unit_ke);
        tb_bang = (DiyTableView) findViewById(R.id.unit_bang);
        tb_liang = (DiyTableView) findViewById(R.id.unit_liang);
        tb_anshi = (DiyTableView) findViewById(R.id.unit_anshi);
        tb_haosheng = (DiyTableView) findViewById(R.id.unit_haosheng);
        chart = (BarChart) findViewById(R.id.barchart);
        rv_element = (RecyclerView) findViewById(R.id.rv_element_content);
        rv_food = (RecyclerView) findViewById(R.id.rv_food_material_history);

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
        for(int i = 0; i < 100; i++){
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


    }







    /**
     * BarChart设置数据
     * @param data1:第一列的值 也就是推荐摄入量的值
     *          data2： 第二列的值 就是实际摄入量的值
     *             element: 元素的名称（蛋白质  脂肪等等）,只能有三个
     * */
    public void setChartData(List<Float> data1, List<Float> data2, final List<String> element){

        //Y轴的测试数据
        List<BarEntry> entriesGroup1 = new ArrayList<>();
        List<BarEntry> entriesGroup2 = new ArrayList<>();

        //设置数据
       for(int i = 0; i < data1.size(); i++){
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
                Log.i(TAG, "getFormattedValue: value="+value);
                String v = "a";
                switch ((int)value){
                    case 0: v = element.get(0);break;
                    case 1: v = element.get(1); break;
                    case 2: v = element.get(2);break;
                    default:break;
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




}
