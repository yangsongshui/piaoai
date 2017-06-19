package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.ModificationModelImp;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class ModificationPresenterImp extends BasePresenterImp<MsgView,Msg> implements BindingPresenter {
    private Context context = null;
    private ModificationModelImp modificationModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public ModificationPresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.modificationModelImp = new ModificationModelImp(context);
    }

    @Override
    public void binding(Map<String, String> map) {
        modificationModelImp.binding(map, this);
    }

    @Override
    public void unSubscribe() {
        modificationModelImp.onUnsubscribe();
    }
}
