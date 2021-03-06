package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.TVOC;
import com.example.yangsong.piaoai.model.TVOCDataModelImp;
import com.example.yangsong.piaoai.model.TVOCHourModelImp;
import com.example.yangsong.piaoai.view.TVOCView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class TVOCdataPresenterImp extends BasePresenterImp<TVOCView, TVOC> implements BindingPresenter {
    private Context context = null;
    private TVOCDataModelImp tvocDataModelImp = null;
    private TVOCHourModelImp tvocHourModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public TVOCdataPresenterImp(TVOCView view, Context context) {
        super(view);
        this.context = context;
        this.tvocDataModelImp = new TVOCDataModelImp(context);
        this.tvocHourModelImp = new TVOCHourModelImp(context);
    }

    @Override
    public void binding(Map<String, String> map) {
        if (map.get("type").equals("0"))
            tvocHourModelImp.GetData(map, this);
        else
            tvocDataModelImp.GetData(map, this);
    }

    @Override
    public void unSubscribe() {
        tvocDataModelImp.onUnsubscribe();
        tvocHourModelImp.onUnsubscribe();
    }
}

