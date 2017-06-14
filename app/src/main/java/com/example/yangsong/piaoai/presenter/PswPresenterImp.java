package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.PswModelImp;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class PswPresenterImp extends BasePresenterImp<MsgView,Msg> implements PswPresenter {
    private Context context = null;
    private PswModelImp pswModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public PswPresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.pswModelImp = new PswModelImp(context);
    }

    @Override
    public void updatePwd(Map<String, String> map) {
        pswModelImp.updatePwd(map, this);
    }

    @Override
    public void unSubscribe() {
        pswModelImp.onUnsubscribe();
    }
}
