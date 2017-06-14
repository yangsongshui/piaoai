package com.example.yangsong.piaoai.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {
    MapView mapView;
    AMap aMap;
    MyLocationStyle myLocationStyle;


    @Override
    protected int getContentView() {
        return R.layout.activity_map;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initMap (savedInstanceState);
    }
    private void initMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //定位监听
        aMap.setOnMyLocationChangeListener(this);

    }

    @Override
    public void onMyLocationChange(Location location) {

    }
}
