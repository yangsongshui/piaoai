package com.example.yangsong.piaoai.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.yangsong.piaoai.R;
import com.example.yangsong.piaoai.base.BaseFragment;
import com.example.yangsong.piaoai.bean.Facility;

import butterknife.BindView;


public class PMFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.pm_pm25)
    TextView pm25;
    @BindView(R.id.pm_pm_tv)
    TextView pm_tv;
    Facility.ResBodyBean.ListBean facility;
    @BindView(R.id.pm_cardiac_rgrpNavigation)
    RadioGroup cardiacRgrpNavigation;
    private Fragment[] frags = new Fragment[4];
    protected BaseFragment baseFragment;
    private PMTimeFragment dataFragment;
    int indext = 0;
    @SuppressLint({"NewApi", "ValidFragment"})
    public PMFragment(Facility.ResBodyBean.ListBean facility) {
        this.facility = facility;
    }

    public PMFragment() {
    }

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        if (!facility.get_$Pm25224().equals("")) {
            pm25.setText(facility.get_$Pm25224());
            int pm = Integer.parseInt(facility.get_$Pm25224());
            if (pm >= 0 && pm <= 35) {
                pm_tv.setText(getString(R.string.out_msg));
            } else if (pm > 35 && pm <= 75) {
                pm_tv.setText(getString(R.string.out_msg2));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_liang));
            } else if (pm > 75 && pm <= 115) {
                pm_tv.setText(getString(R.string.out_msg3));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_qingdu));
            } else if (pm > 115 && pm <= 150) {
                pm_tv.setText(getString(R.string.out_msg4));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhongdu));
            } else if (pm > 150 && pm <= 250) {
                pm_tv.setText(getString(R.string.out_msg5));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_zhong));
            } else if (pm > 250 ) {
                pm_tv.setText(getString(R.string.out_msg6));
                pm_tv.setBackground(getResources().getDrawable(R.drawable.pm_yanzhong));
            }
        }
        initData();
        cardiacRgrpNavigation.check(R.id.pm_chart_tiem_rb);
        cardiacRgrpNavigation.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_pm;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void initData() {
        if (dataFragment == null) {
            dataFragment = new PMTimeFragment(getActivity(), facility.getDeviceid());
        }

        if (!dataFragment.isAdded()) {
            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.pm_chart_fl, dataFragment).commit();
            baseFragment = dataFragment;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getCheckedRadioButtonId()) {
            case R.id.pm_chart_tiem_rb:
                showFragment(0);
                break;
            case R.id.pm_chart_day_rb:
                showFragment(1);
                break;
            case R.id.pm_chart_week_rb:
                showFragment(2);
                break;
            case R.id.pm_chart_month_rb:
                showFragment(3);
                break;
        }
    }

    private void showFragment(int position) {
        if (frags[position] == null) {
            frags[position] = getFrag(position);
        }

        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), frags[position]);
    }

    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                if (dataFragment != null)
                    return dataFragment;
                else
                    return new PMTimeFragment(getActivity(), facility.getDeviceid());
            case 1:
                return new PMDayFragment(getActivity(), facility.getDeviceid());
            case 2:
                return new PMWeekFragment(getActivity(), facility.getDeviceid());
            case 3:
                return new PMMonthFragment(getActivity(), facility.getDeviceid());
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
            transaction.hide(baseFragment).add(R.id.pm_chart_fl, fragment).commit();
        } else {
            transaction.hide(baseFragment).show(fragment).commit();
        }

        baseFragment = (BaseFragment) fragment;


    }
}
