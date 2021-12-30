package com.example.location_baidumap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;

/**
 * @author Abby
 * @version V1.0
 **/
public class MainActivity extends Activity {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;
    private RadioGroup mapTypeGroup = null;
    private String mapType = null;
    private LocationClient mLocationClient = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMap(); //初始化地图
        changeMapType(); //功能一：切换地图类型
        showLocation(); //功能二：显示定位
    }

    /**
     * 地图初始化
     */
    protected void initMap(){
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
        //获取地图控制对象
        mBaiduMap = mMapView.getMap();

        /* 法二：直接在Java代码中添加MapView的方式来展示地图
        mMapView = new MapView(this);
        setContentView(mMapView);
        */
    }

    /**
     * 功能一：切换地图类型
     */
    protected  void changeMapType(){
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
     * 设置地图类型
     * @param type 地图类型
     */
    protected void setMapType(String type){
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

    /**
     * 功能二：显示定位
     */
    protected void showLocation(){
        //开启地图的定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //定位初始化
        mLocationClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        MyLocationListener myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);//开启地图定位图层
        mLocationClient.start();
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mMapView == null){
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView = null;
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
