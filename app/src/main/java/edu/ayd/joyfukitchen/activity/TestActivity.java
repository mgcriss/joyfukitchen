package edu.ayd.joyfukitchen.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/5/4.
 */
public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView imageView = (ImageView) findViewById(R.id.iv_test);

        Picasso.with(this).load("https://github.com/square/picasso/raw/master/website/static/sample.png").into(imageView);


    }
}
