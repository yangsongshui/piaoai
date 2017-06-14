package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Identify;
import com.example.yangsong.piaoai.model.CodeModelImp;
import com.example.yangsong.piaoai.view.GetCodeView;

/**
 * Created by ys on 2017/6/13.
 */

public class GetCodePresenterImp extends BasePresenterImp<GetCodeView,Identify> implements GetCodePresenter{
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private CodeModelImp loginModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public GetCodePresenterImp(GetCodeView view, Context context) {
        super(view);
        this.context = context;
        this.loginModelImp = new CodeModelImp(context);
    }

    @Override
    public void GetCode(String phoneNumber, String type) {
        loginModelImp.getCode(phoneNumber,type,this);
    }

    @Override
    public void unSubscribe() {
        loginModelImp.onUnsubscribe();
    }
}
