package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.User;
import com.example.yangsong.piaoai.model.ThirdLoginModelImp;
import com.example.yangsong.piaoai.view.LoginView;


/**
 * 描述：MVP中的P实现类
 */
public class ThirdLoginPresenterImp extends BasePresenterImp<LoginView,User> implements ThirdLoginPresenter{
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private ThirdLoginModelImp thirdLoginModelImp = null;
    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author ys
     * @date 2017/6/13 15:12
     */
    public ThirdLoginPresenterImp(LoginView view, Context context) {
        super(view);
        this.context = context;
        this.thirdLoginModelImp = new ThirdLoginModelImp(context);
    }

    @Override
    public void loadLogin(String openid) {
        thirdLoginModelImp.thirdLogin(openid, this);
    }

    @Override
    public void unSubscribe() {
        thirdLoginModelImp.onUnsubscribe();
    }
}
