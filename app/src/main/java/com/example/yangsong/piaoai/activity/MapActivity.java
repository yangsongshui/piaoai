package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.myview.AddressPopuoWindow;
import com.example.yangsong.piaoai.util.Toastor;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener {
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    MapView mapView;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    @BindView(R.id.map_search)
    AutoCompleteTextView mapSearch;
    Toastor toastor;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    private PoiResult poiResult; // poi返回的结果
    private String keyWord = "";// 要输入的poi搜索关键字
    private ProgressDialog progDialog = null;// 搜索时进度条
    GeocodeSearch geocoderSearch;
    String city = "";
    AddressPopuoWindow addressPopuoWindow;

    @Override
    protected int getContentView() {
        return R.layout.activity_map;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toastor = new Toastor(this);
        initMap(savedInstanceState);
        addressPopuoWindow = new AddressPopuoWindow(this, this);
        mapSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“GO”键*/
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    /*隐藏软键盘*/
                    InputMethodManager imm = (InputMethodManager) v
                            .getContext().getSystemService(
                                    Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive()) {
                        imm.hideSoftInputFromWindow(
                                v.getApplicationWindowToken(), 0);
                    }
                    searchButton();
                    return true;
                }
                return false;
            }
        });
    }

    private void initMap(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);
        aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
        myLocationStyle = new MyLocationStyle();
        //初始化定位蓝点样式类
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //定位监听
        aMap.setOnMyLocationChangeListener(this);

    }

    @Override
    public void onMyLocationChange(Location location) {
        LatLonPoint latLonPoint = new LatLonPoint(location.getLatitude(), location.getLongitude());
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 100, GeocodeSearch.AMAP);
        geocoderSearch.getFromLocationAsyn(query);

    }


    /**
     * 点击搜索按钮
     */
    public void searchButton() {
        keyWord = checkEditText(mapSearch);
        if ("".equals(keyWord)) {
            toastor.showSingletonToast("请输入搜索关键字");
            return;
        } else {
            doSearchQuery();
        }
    }

    /**
     * 判断edittext是否null
     */
    public static String checkEditText(EditText editText) {
        if (editText != null && editText.getText() != null
                && !(editText.getText().toString().trim().equals(""))) {
            return editText.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        currentPage = 0;
        query = new PoiSearch.Query(keyWord, "", city);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        //解析result获取地址描述信息
        city = regeocodeResult.getRegeocodeAddress().getCity();
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null) {
                mapSearch.setText("");
                String addressName = regeocodeResult.getRegeocodeAddress().getFormatAddress() + regeocodeResult.getRegeocodeAddress().getStreetNumber().getNumber();
                mapSearch.setText(addressName);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (progDialog == null)
            progDialog = new ProgressDialog(this);
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(false);
        progDialog.setMessage("正在搜索:\n" + keyWord);
        progDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }
    List<PoiItem> poiItems;
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        dissmissProgressDialog();// 隐藏对话框
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                this.poiResult = poiResult;
                // 取得搜索到的poiitems有多少页
                poiItems = this.poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                addressPopuoWindow.showAsDropDown(this.findViewById(R.id.search_ll));
                addressPopuoWindow.setList(poiItems);

            } else {
                toastor.showSingletonToast("对不起，没有搜索到相关数据！");
            }
        } else {
            toastor.showSingletonToast("检索失败");
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @OnClick({R.id.map_left_iv, R.id.complete_right, R.id.map_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.map_left_iv:
                finish();
                break;
            case R.id.complete_right:

                Intent resultIntent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("address", mapSearch.getText().toString());
                resultIntent.putExtras(bundle);
                this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
                finish();
                break;
            case R.id.map_cancel:
                mapSearch.setText("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiItem poiItem=poiItems.get(position);
        String address=poiItem.getProvinceName()+poiItem.getCityName()+poiItem.getSnippet()+poiItem.getTitle();
        Intent resultIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("address",address);
        resultIntent.putExtras(bundle);
        this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
        finish();
    }
}
