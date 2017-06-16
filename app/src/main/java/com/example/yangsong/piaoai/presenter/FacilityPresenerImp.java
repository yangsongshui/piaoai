package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Facility;
import com.example.yangsong.piaoai.model.FacilityModelImp;
import com.example.yangsong.piaoai.view.FacilityView;

/**
 * Created by ys on 2017/6/16.
 */

public class FacilityPresenerImp extends BasePresenterImp<FacilityView, Facility> implements FacilityPresener {
    private Context context = null;
    private FacilityModelImp facilityModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public FacilityPresenerImp(FacilityView view, Context context) {
        super(view);
        this.context = context;
        this.facilityModelImp = new FacilityModelImp(context);
    }

    @Override
    public void findUserDevice(String phoneNumber) {
        facilityModelImp.findUserDevice(phoneNumber, this);
    }

    @Override
    public void unSubscribe() {
        facilityModelImp.onUnsubscribe();
    }
}
