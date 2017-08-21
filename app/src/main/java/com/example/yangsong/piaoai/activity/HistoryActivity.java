package com.example.yangsong.piaoai.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.api.ServiceApi;
import com.example.yangsong.piaoai.app.MyApplication;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.CityData;
import com.example.yangsong.piaoai.bean.Weather;
import com.example.yangsong.piaoai.fragment.DayFragment;
import com.example.yangsong.piaoai.fragment.MonthFragment;
import com.example.yangsong.piaoai.fragment.TimeFragment;
import com.example.yangsong.piaoai.fragment.WeekFragment;
import com.example.yangsong.piaoai.inter.FragmentEvent;
import com.example.yangsong.piaoai.myview.MyMarkerView;
import com.example.yangsong.piaoai.myview.SharePopuoWindow;
import com.example.yangsong.piaoai.presenter.CityDataPresenterImp;
import com.example.yangsong.piaoai.util.DateUtil;
import com.example.yangsong.piaoai.util.Log;
import com.example.yangsong.piaoai.util.Toastor;
import com.example.yangsong.piaoai.view.CityDataView;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.yangsong.piaoai.util.Constan.WEATHER_URL;
import static com.example.yangsong.piaoai.util.DateUtil.FORMAT_ONE;


public class HistoryActivity extends BaseActivity implements OnChartValueSelectedListener, RadioGroup.OnCheckedChangeListener, CityDataView {
    private final static String TAG = HistoryActivity.class.getSimpleName();
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.cardiac_fl)
    FrameLayout frameLayout;
    @BindView(R.id.line_chart)
    CombinedChart mChart;
    @BindView(R.id.cardiac_rgrpNavigation)
    RadioGroup cardiacRgrpNavigation;
    String type;
    int indext = 0;
    @BindView(R.id.address_w)
    TextView addressW;
    @BindView(R.id.history_wendu_tv)
    TextView historyWenduTv;
    @BindView(R.id.history_tianqi_tv)
    TextView historyTianqiTv;
    @BindView(R.id.history_tianqi_iv)
    ImageView historyTianqiIv;
    @BindView(R.id.ziwaixian_w)
    TextView ziwaixianW;
    @BindView(R.id.history_shidu_tv)
    TextView historyShiduTv;
    private Fragment[] frags = new Fragment[6];
    protected BaseFragment baseFragment;
    private TimeFragment dataFragment;
    private SharePopuoWindow sharePopuoWindow;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    Toastor toastor;
    Retrofit retrofit;
    private Map<String, String> map;
    CityDataPresenterImp cityDataPresenterImp;
    List<String> mList;
    List<String> time;
    ProgressDialog progressDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mList = new ArrayList<>();
        time = new ArrayList<>();
        String deviceID = getIntent().getStringExtra("deviceID");
        type = getIntent().getStringExtra("type");
        indext = getIntent().getIntExtra("indext", 0);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在分享...");
        cityDataPresenterImp = new CityDataPresenterImp(this, this);
        Log.e(TAG, deviceID);
        initData();
        initChart();
        initView();
        initWerthc();
        map = new HashMap<>();


    }


    @OnClick({R.id.history_left_iv, R.id.tv_history_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.history_left_iv:
                finish();
                break;
            case R.id.tv_history_right:
                //分享
                sharePopuoWindow.showAtLocation(this.findViewById(R.id.activity_history), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
        }
    }

    private void initData() {
        if (dataFragment == null) {
            dataFragment = new TimeFragment(this, indext);
        }

        if (!dataFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.cardiac_fl, dataFragment).commit();
            baseFragment = dataFragment;
        }
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("PM2.5"));
        if (type.equals("1")||type.equals("4")) {
            tabLayout.addTab(tabLayout.newTab().setText("CO2"));
            tabLayout.addTab(tabLayout.newTab().setText("TVOC"));
            tabLayout.addTab(tabLayout.newTab().setText("甲醛"));
            //tabLayout.addTab(tabLayout.newTab().setText("温度"));
            //tabLayout.addTab(tabLayout.newTab().setText("湿度"));
        }
        cardiacRgrpNavigation.check(R.id.cardiac_tiem_rb);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.getTabAt(indext).select();
        cardiacRgrpNavigation.setOnCheckedChangeListener(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //选中了tab的逻辑
                Log.i("选中了", tab.getPosition() + "");
                indext = tab.getPosition();
                EventBus.getDefault().post(new FragmentEvent(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //上一次tab的逻辑
                Log.i("上一次选中", tab.getPosition() + "");
            }
        });
        sharePopuoWindow = new SharePopuoWindow(this, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.QQ_tv:
                        UMShare(SHARE_MEDIA.QQ);
                        break;
                    case R.id.cancel_tv:
                        sharePopuoWindow.dismiss();
                        break;
                    case R.id.weixin_iv:
                        UMShare(SHARE_MEDIA.WEIXIN);
                        break;
                    case R.id.wxcircle_iv:
                        UMShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                }
            }
        });
    }

    private void initWerthc() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(WEATHER_URL)
                .build();
        toastor = new Toastor(this);
    }

    private void showFragment(int position) {
        if (frags[position] == null) {
            frags[position] = getFrag(position);
        }

        addOrShowFragment(getSupportFragmentManager().beginTransaction(), frags[position]);
    }

    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                if (dataFragment != null)
                    return dataFragment;
                else
                    return new TimeFragment(this, indext);
            case 1:
                return new DayFragment(this, indext);
            case 2:
                return new WeekFragment(this, indext);
            case 3:
                return new MonthFragment(this, indext);
            default:
                return null;
        }
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (baseFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(baseFragment).add(R.id.cardiac_fl, fragment).commit();
        } else {
            transaction.hide(baseFragment).show(fragment).commit();
        }

        baseFragment = (BaseFragment) fragment;


    }

    private void initChart() {

        /**
         * ====================1.初始化-自由配置===========================
         */
        // 是否在折线图上添加边框
        mChart.setDrawGridBackground(false);
        mChart.setDrawBorders(false);
        // 设置右下角描述
        Description description = new Description();
        description.setText("");
        mChart.setDescription(description);
        mChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.BAR, CombinedChart.DrawOrder.LINE,
        });
        //设置透明度
        // mChart.setAlpha(0.8f);
        //设置是否可以触摸，如为false，则不能拖动，缩放等
        mChart.setTouchEnabled(true);
        //设置是否可以拖拽
        mChart.setDragEnabled(false);
        //设置是否可以缩放
        mChart.setScaleEnabled(false);
        mChart.setDoubleTapToZoomEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(false);
        //设置四个边的间距
        // mChart.setViewPortOffsets(10, 0, 0, 10);
        //隐藏Y轴
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = mChart.getXAxis();
        xAxis.setAxisMinimum(-0.1f);
        xAxis.setGranularity(0.3f);
        xAxis.setAxisMaximum(6);
        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.argb(148, 255, 255, 255));
        xAxis.setTextSize(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);

        mChart.getLegend().setEnabled(false);

        mChart.setOnChartValueSelectedListener(this);

    }

    private LineData getLineData() {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            // Log.e(TAG, mList.get(i)+" " + i );
            if (i >= (mList.size())) {
                values1.add(new Entry(i, 0));
            } else
                values1.add(new Entry(i, Integer.parseInt(mList.get(i))));
        }
        IAxisValueFormatter formatter = new IAxisValueFormatter() {


            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return time.get((int) value % time.size());
            }


        };
        mChart.getXAxis().setValueFormatter(formatter);
        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
        } else {
            set1 = new LineDataSet(values1, "");
        }
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(3f);//设置焦点圆心的大小
        set1.enableDashedHighlightLine(5f, 2f, 1f);//点击后的高亮线的显示样式
        set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
        set1.setHighlightEnabled(true);//是否禁用点击高亮线

        set1.setDrawHighlightIndicators(false);//设置不显示水平高亮线
        set1.setDrawCircles(false);  //设置有圆点
        set1.setDrawValues(false);  //不显示数据
        set1.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER); //设置为曲线

        set1.setColor(Color.rgb(255, 255, 255));    //设置曲线的颜色

        return new LineData(set1);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getCheckedRadioButtonId()) {
            case R.id.cardiac_tiem_rb:
                showFragment(0);
                break;
            case R.id.cardiac_day_rb:
                showFragment(1);
                break;
            case R.id.cardiac_week_rb:
                showFragment(2);
                break;
            case R.id.cardiac_month_rb:
                showFragment(3);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.e("定位数据", aMapLocation.getCity());
                    addressW.setText(aMapLocation.getCity());
                    String city = aMapLocation.getCity().substring(0, aMapLocation.getCity().length() - 1);
                    cityDataPresenterImp.binding(city);
                    ServiceApi service = retrofit.create(ServiceApi.class);
                    Call<Weather> call = service.getWeather(aMapLocation.getCity(), "1");
                    call.enqueue(new Callback<Weather>() {
                        @Override
                        public void onResponse(Call<Weather> call, Response<Weather> response) {
                            //请求成功操作
                            Weather weather = response.body();
                            if (weather.getShowapi_res_code() == 0) {
                                initWeather(weather);
                            } else {
                                toastor.showSingletonToast("天气查询失败");

                            }

                        }

                        @Override
                        public void onFailure(Call<Weather> call, Throwable t) {
                            //请求失败操作

                            toastor.showSingletonToast("天气查询失败");
                        }
                    });

                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    toastor.showSingletonToast("定位失败:" + aMapLocation.getErrorInfo());

                }
            }
        }
    };

    private void initWeather(Weather weather) {
        MyApplication.newInstance().getGlide().load(weather.getShowapi_res_body().getNow().getWeather_pic()).into(historyTianqiIv);
        historyTianqiTv.setText(weather.getShowapi_res_body().getNow().getWeather());
        historyWenduTv.setText(weather.getShowapi_res_body().getNow().getTemperature() + "℃");
        historyShiduTv.setText(weather.getShowapi_res_body().getNow().getSd());
        ziwaixianW.setText(weather.getShowapi_res_body().getF1().getZiwaixian());

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void disimissProgress() {

    }

    @Override
    public void loadDataSuccess(CityData tData) {
        if (tData.getResBody().getList().size() > 0) {
            getData(tData.getResBody().getList());
        } else {
            CombinedData data = new CombinedData();
            data.setData(getLineData());
            mChart.setData(data);
            mChart.invalidate();
        }

    }

    private void getData(List<CityData.ResBodyBean.ListBean> listBean) {
        mList.clear();
        time.clear();
        for (int i = 0; i < listBean.size(); i++) {
            String date = DateUtil.stringtoString(listBean.get(i).getCt(),FORMAT_ONE);
            time.add(date);
            mList.add(listBean.get(i).getPm2_5());
        }
        CombinedData data = new CombinedData();
        data.setData(getLineData());
        mChart.setData(data);
        mChart.invalidate();
    }

    @Override
    public void loadDataError(Throwable throwable) {
        Log.e(TAG, throwable.toString());
        toastor.showSingletonToast("服务器连接异常");
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
            progressDialog.show();
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            toastor.showSingletonToast(platform + " 分享成功啦");
            progressDialog.dismiss();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            toastor.showSingletonToast(platform + " 分享失败啦");
            progressDialog.dismiss();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            progressDialog.dismiss();
            toastor.showSingletonToast(platform + " 分享取消了");

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void UMShare(SHARE_MEDIA platform) {

        View viewScreen = getWindow().getDecorView();
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, 0, width, height);
        viewScreen.destroyDrawingCache();
        UMImage image = new UMImage(HistoryActivity.this, bitmap);//bitmap文件
        image.compressStyle = UMImage.CompressStyle.QUALITY;
        new ShareAction(HistoryActivity.this).setPlatform(platform)
                .withText("飘爱检测仪")
                .withMedia(image)
                .setCallback(umShareListener)
                .share();
    }
}
