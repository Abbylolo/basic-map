package com.example.location_baidumap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {
    private Button returnButton;
    private Button confrimButton;
    private RadioGroup locaModeRadioGroup;
    private String locationMode = null;
    private RadioButton radioButton;
    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide(); //隐藏标题栏

        init(); //初始化函数
        returnToMap(); //返回地图主页面
        submitSettings(); //提交信息并修改
    }

    /**
     * 初始化函数
     */
    protected void init(){
        //获取各个控件
        confrimButton = findViewById(R.id.confirmButton);
        returnButton = findViewById(R.id.return_button);
        locaModeRadioGroup = findViewById(R.id.location_mode_group);

        //获取SharedPreference的实例对象，指定文件的名称和操作模式
        sharedPreferences = getSharedPreferences("location_mode",MODE_PRIVATE);

        //设置控件为上次的状态
        String lastLocMode = sharedPreferences.getString("locMode","普通态");//从存储文件中获取定位模式，默认值为普通态
        for(int i = 0 ;i < locaModeRadioGroup.getChildCount();i++){
            RadioButton rb = (RadioButton)locaModeRadioGroup.getChildAt(i);
            if(rb.getText().toString().equals(lastLocMode)){
                rb.setChecked(true); //设置对应单选框为选中状态
            }
        }
    }

    /**
     * 点击返回按钮标志，跳转回地图页面
     */
    protected void returnToMap(){
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 点击确认修改按钮后，提交设置信息MainActivity
     * 只有当值改变并且点击确认修改，才改变
     */
    protected void submitSettings(){
        getLocationMode();
        confrimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //修改条件：①点击确认按钮 & ②值发生变化
                if(locationMode != null) {
                    // 使用SharedPreferences存储数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("locMode",locationMode);
                    editor.commit();
                }
            }
        });
    }

    /**
     * 监听单选按钮，返回选定的值
     */
    protected void getLocationMode(){
        locaModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton = findViewById(checkedId);
                locationMode = radioButton.getText().toString();
            }
        });
    }
}
