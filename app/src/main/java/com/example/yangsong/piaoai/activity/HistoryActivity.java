package com.example.yangsong.piaoai.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseActivity;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.fragment.DayFragment;
import com.example.yangsong.piaoai.fragment.MonthFragment;
import com.example.yangsong.piaoai.fragment.TimeFragment;
import com.example.yangsong.piaoai.fragment.WeekFragment;
import com.example.yangsong.piaoai.inter.OnCheckedListener;
import com.example.yangsong.piaoai.myview.MyMarkerView;
import com.example.yangsong.piaoai.myview.SharePopuoWindow;
import com.example.yangsong.piaoai.util.Log;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class HistoryActivity extends BaseActivity implements OnChartValueSelectedListener, RadioGroup.OnCheckedChangeListener {
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
    private Fragment[] frags = new Fragment[6];
    protected BaseFragment baseFragment;
    private TimeFragment dataFragment;
    private SharePopuoWindow sharePopuoWindow;
    OnCheckedListener onCheckedListener;

    @Override
    protected int getContentView() {
        return R.layout.activity_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        String deviceID = getIntent().getStringExtra("deviceID");
        type = getIntent().getStringExtra("type");
        indext = getIntent().getIntExtra("indext",0);
        Log.e(TAG, deviceID);
        initData();
        initChart();
        initView();


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
            dataFragment = new TimeFragment(this);
        }

        if (!dataFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.cardiac_fl, dataFragment).commit();
            baseFragment = dataFragment;
        }
    }

    private void initView() {
        tabLayout.addTab(tabLayout.newTab().setText("PM2.5"));
        if (type.equals("1")) {
            tabLayout.addTab(tabLayout.newTab().setText("TVOC"));
            tabLayout.addTab(tabLayout.newTab().setText("CO2"));
            tabLayout.addTab(tabLayout.newTab().setText("甲醛"));
            tabLayout.addTab(tabLayout.newTab().setText("温度"));
            tabLayout.addTab(tabLayout.newTab().setText("湿度"));
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
                if (onCheckedListener != null) {
                    onCheckedListener.onViewChecked(tab, tab.getPosition());
                }

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
                        break;
                    case R.id.cancel_tv:
                        sharePopuoWindow.dismiss();
                        break;
                    case R.id.weixin_iv:
                        break;
                }
            }
        });
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
                    return new TimeFragment(this);
            case 1:
                return new DayFragment(this);
            case 2:
                return new WeekFragment(this);
            case 3:
                return new MonthFragment(this);
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
        mChart.setDragEnabled(true);
        //设置是否可以缩放
        mChart.setScaleYEnabled(false);
        //设置是否能扩大扩小
        mChart.setPinchZoom(true);
        //设置四个边的间距
        // mChart.setViewPortOffsets(10, 0, 0, 10);
        //隐藏Y轴
        mChart.getAxisRight().setEnabled(false);
        mChart.getAxisLeft().setEnabled(false);
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart

        XAxis xAxis = mChart.getXAxis();

        xAxis.setAxisMinimum(-0.5f);
        xAxis.setGranularity(0.3f);

        xAxis.setTextColor(Color.rgb(255, 255, 255));
        xAxis.setAxisLineColor(Color.argb(148, 255, 255, 255));
        xAxis.setTextSize(10);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴在底部
        //不画网格
        xAxis.setDrawGridLines(false);
        mChart.getLegend().setEnabled(false);

        CombinedData data = new CombinedData();

        data.setData(getLineData());


        mChart.setOnChartValueSelectedListener(this);
        mChart.setData(data);

        mChart.invalidate();
    }

    private LineData getLineData() {
        mChart.getXAxis().setAxisMaximum(23);
        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            String[] day = new String[]{"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00", "10:00", "11:00"
                    , "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return day[(int) value % day.length];
            }


        };
        mChart.getXAxis().setValueFormatter(formatter);
        ArrayList<Entry> values1 = new ArrayList<>();
        values1.add(new Entry(0, 300));
        values1.add(new Entry(1, 400));
        values1.add(new Entry(2, 512));
        values1.add(new Entry(3, 700));
        values1.add(new Entry(4, 124));
        values1.add(new Entry(5, 567));
        values1.add(new Entry(6, 234));
        values1.add(new Entry(7, 323));
        values1.add(new Entry(8, 232));
        values1.add(new Entry(9, 283));
        values1.add(new Entry(10, 456));
        values1.add(new Entry(11, 234));
        values1.add(new Entry(12, 334));
        values1.add(new Entry(13, 434));
        values1.add(new Entry(14, 534));
        values1.add(new Entry(15, 634));
        values1.add(new Entry(16, 734));
        values1.add(new Entry(17, 834));
        values1.add(new Entry(18, 134));
        values1.add(new Entry(19, 434));
        values1.add(new Entry(20, 634));
        values1.add(new Entry(21, 334));
        values1.add(new Entry(22, 834));
        values1.add(new Entry(23, 634));

        LineDataSet set1;
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
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

    public void setOnCheckedListener(OnCheckedListener onCheckedListener) {
        this.onCheckedListener = onCheckedListener;
    }
}
