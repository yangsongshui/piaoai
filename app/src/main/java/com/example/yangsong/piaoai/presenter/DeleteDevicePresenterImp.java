package com.example.yangsong.piaoai.presenter;

import android.content.Context;

import com.example.yangsong.piaoai.base.BasePresenterImp;
import com.example.yangsong.piaoai.bean.Msg;
import com.example.yangsong.piaoai.model.DeleteDeviceModelImp;
import com.example.yangsong.piaoai.view.MsgView;

import java.util.Map;


/**
 * 描述：MVP中的P实现类
 */
public class DeleteDevicePresenterImp extends BasePresenterImp<MsgView,Msg> implements BindingPresenter {
    private Context context = null;
    private DeleteDeviceModelImp deleteDeviceModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     * @author dc
     * @date 2017/6/13 15:12
     */
    public DeleteDevicePresenterImp(MsgView view, Context context) {
        super(view);
        this.context = context;
        this.deleteDeviceModelImp = new DeleteDeviceModelImp(context);
    }

    @Override
    public void binding(Map<String, String> map) {
        deleteDeviceModelImp.binding(map, this);
    }

    @Override
    public void unSubscribe() {
        deleteDeviceModelImp.onUnsubscribe();
    }
}
