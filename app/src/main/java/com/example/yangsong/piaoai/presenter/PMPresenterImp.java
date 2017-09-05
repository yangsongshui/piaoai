package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.TVOC;
import com.example.yangsong.piaoai.model.PMModelImp;
import com.example.yangsong.piaoai.view.TVOCView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class PMPresenterImp extends BasePresenterImp<TVOCView,TVOC> implements BindingPresenter {
    private Context context = null;
    private PMModelImp pmModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public PMPresenterImp(TVOCView view, Context context) {
        super(view);
        this.context = context;
        this.pmModelImp = new PMModelImp(context);

    }

    @Override
    public void binding(Map<String, String> map) {
            pmModelImp.GetData(map, this);

    }

    @Override
    public void unSubscribe() {
        pmModelImp.onUnsubscribe();
    }
}
