package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.FLZDevice;
import com.example.yangsong.piaoai.model.CloseModelImp;
import com.example.yangsong.piaoai.view.FLZView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class ClosePresenterImp extends BasePresenterImp<FLZView,FLZDevice> implements MsgPresenter {
    //传入泛型V和T分别为WeatherView、WeatherInfoBean表示建立这两者之间的桥梁
    private Context context = null;
    private CloseModelImp closeModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public ClosePresenterImp(FLZView view, Context context) {
        super(view);
        this.context = context;
        this.closeModelImp = new CloseModelImp(context);
    }

    @Override
    public void loadWeather(Map<String, String> map) {
        closeModelImp.loadWeather(map, this);
    }

    @Override
    public void unSubscribe() {
        closeModelImp.onUnsubscribe();
    }
}
