package com.example.location_baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

/**
 * @author Abby
 * @version V1.0
 **/
public class MainActivity extends Activity {
    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    RadioGroup mapTypeGroup = null;
    String mapType = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);

        //法一：通过layout文件中添加MapView控件来展示地图
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = findViewById(R.id.bMapView);

        /* 法二：直接在Java代码中添加MapView的方式来展示地图
        mMapView = new MapView(this);
        setContentView(mMapView);
        */

        //一、切换地图类型
        //① MAP_TYPE_NORMAL  普通地图（包含3D地图）   ② MAP_TYPE_SATELLITE  卫星图    ③ MAP_TYPE_NONE  空白地图
        //在导航栏的单选按钮改变状态时触发事件（即为单选按钮组设置监听）
        mapTypeGroup = (RadioGroup) findViewById(R.id.map_type_group);
        mapTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,int checkedId){
                //根据checkedId获取到单选按钮组里面的具体单选按钮
                RadioButton radioButton = (RadioButton)findViewById(checkedId);
                //获取选中的单选按钮的值
                mapType = radioButton.getText().toString();
                setMapType(mapType);
            }
        });
    }

    /**
     *
     * @param type
     */
    protected void setMapType(String type){
        mBaiduMap = mMapView.getMap();
        switch (type) {
            case "卫星图":
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case "空白地图":
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                break;
            default:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

}
