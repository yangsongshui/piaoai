package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.model.PMDataModelImp;
import com.example.yangsong.piaoai.model.PMHourModelImp;
import com.example.yangsong.piaoai.view.PMView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class PMdataPresenterImp extends BasePresenterImp<PMView,PMBean> implements BindingPresenter {
    private Context context = null;
    private PMDataModelImp pmDataModelImp = null;
    private PMHourModelImp pmHourModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public PMdataPresenterImp(PMView view, Context context) {
        super(view);
        this.context = context;
        this.pmDataModelImp = new PMDataModelImp(context);
        this.pmHourModelImp = new PMHourModelImp(context);
    }

    @Override
    public void binding(Map<String, String> map) {
        if (map.get("type").equals("0"))
            pmHourModelImp.GetData(map, this);
        else
            pmDataModelImp.GetData(map, this);
    }

    @Override
    public void unSubscribe() {
        pmDataModelImp.onUnsubscribe();
        pmHourModelImp.onUnsubscribe();
    }
}

