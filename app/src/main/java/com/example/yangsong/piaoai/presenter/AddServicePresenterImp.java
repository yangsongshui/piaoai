package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.AddServiceModelImp;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class AddServicePresenterImp extends BasePresenterImp<MsgView,Msg> implements MsgPresenter {
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private AddServiceModelImp addServiceModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public AddServicePresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.addServiceModelImp = new AddServiceModelImp(context);
    }

    @Override
    public void loadWeather(Map<String, String> map) {
        addServiceModelImp.binding(map, this);
    }

    @Override
    public void unSubscribe() {
        addServiceModelImp.onUnsubscribe();
    }
}
