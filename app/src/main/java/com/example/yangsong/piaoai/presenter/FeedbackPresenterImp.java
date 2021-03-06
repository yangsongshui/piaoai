package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.FeedbackModelImp;
import com.example.yangsong.piaoai.view.MsgView;


/**
 * 描述：MVP中的P实现类
 */
public class FeedbackPresenterImp extends BasePresenterImp<MsgView,Msg> implements FeedbackPresenter {
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private FeedbackModelImp updateModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public FeedbackPresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.updateModelImp = new FeedbackModelImp(context);
    }

    @Override
    public void addRemark(String phoneNumber, String remark) {

        updateModelImp.addRemark(phoneNumber,remark, this);
    }

    @Override
    public void unSubscribe() {
        updateModelImp.onUnsubscribe();
    }
}
