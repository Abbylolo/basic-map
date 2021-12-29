package com.example.location_baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class LandActivity extends AppCompatActivity {
    private static final long DELAY_TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.land); // 显示着陆页
        new Handler().postDelayed(new splashHandle(),DELAY_TIME); // 1s后进行页面切换
    }

    class splashHandle implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(LandActivity.this,MainActivity.class));
            LandActivity.this.finish(); //关闭着陆页
        }
    }
}