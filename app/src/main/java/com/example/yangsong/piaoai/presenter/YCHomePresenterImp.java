package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.YCHomeModelImp;
import com.example.yangsong.piaoai.view.MsgView;


/**
 * 描述：MVP中的P实现类
 */
public class YCHomePresenterImp extends BasePresenterImp<MsgView, Msg> {
    private Context context = null;
    private YCHomeModelImp ycHomeModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public YCHomePresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.ycHomeModelImp = new YCHomeModelImp(context);

    }

    public void binding(String imei) {

        ycHomeModelImp.GetData(imei, this);

    }

    public void unSubscribe() {
        ycHomeModelImp.onUnsubscribe();
    }
}

