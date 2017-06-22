package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.PMBean;
import com.example.yangsong.piaoai.model.MethanalHourModelImp;
import com.example.yangsong.piaoai.model.MethanalModelImp;
import com.example.yangsong.piaoai.view.PMView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class MethanalPresenterImp extends BasePresenterImp<PMView,PMBean> implements BindingPresenter {
    private Context context = null;
    private MethanalModelImp methanalModelImp = null;
    private MethanalHourModelImp methanalHourModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public MethanalPresenterImp(PMView view, Context context) {
        super(view);
        this.context = context;
        this.methanalModelImp = new MethanalModelImp(context);
        this.methanalHourModelImp = new MethanalHourModelImp(context);
    }

    @Override
    public void binding(Map<String, String> map) {
        if (map.get("type").equals("0"))
            methanalHourModelImp.GetData(map, this);
        else
            methanalModelImp.GetData(map, this);

    }

    @Override
    public void unSubscribe() {
        methanalModelImp.onUnsubscribe();
        methanalHourModelImp.onUnsubscribe();
    }
}

