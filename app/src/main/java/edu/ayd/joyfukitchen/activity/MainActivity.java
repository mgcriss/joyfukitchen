package edu.ayd.joyfukitchen.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import edu.ayd.joyfukitchen.view.FontSizeAutoAdjustTextView;


public class MainActivity extends Activity {

    private FontSizeAutoAdjustTextView ftv_1;
    private Button btn1;
    private Button btn2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_weight);
//        ftv_1 = (FontSizeAutoAdjustTextView) findViewById(R.id.ftv_1);
//        btn1 = (Button) findViewById(R.id.btn1);
//        btn2 = (Button) findViewById(R.id.btn2);
//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ftv_1.setText("安士");
//            }
//        });
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ftv_1.setText("克");
//            }
//        });
    }




}
