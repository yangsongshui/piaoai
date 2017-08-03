package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.CityData;
import com.example.yangsong.piaoai.model.CityDataModelImp;
import com.example.yangsong.piaoai.view.CityDataView;


/**
 * 描述：MVP中的P实现类
 */
public class CityDataPresenterImp extends BasePresenterImp<CityDataView,CityData> implements CityDataPresenter{
    private Context context = null;
    private CityDataModelImp cityDataModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public CityDataPresenterImp(CityDataView view,Context context) {
        super(view);
       this.context=context;
        this.cityDataModelImp = new CityDataModelImp(context);

    }

    @Override
    public void binding(String city) {
        cityDataModelImp.cityData(city, this);
    }

    @Override
    public void unSubscribe() {
        cityDataModelImp.onUnsubscribe();
    }
}
