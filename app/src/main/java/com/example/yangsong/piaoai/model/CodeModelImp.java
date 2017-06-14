package com.example.yangsong.piaoai.model;

import android.content.Context;

import com.example.yangsong.piaoai.api.ServiceApi;
import com.example.yangsong.piaoai.base.BaseModel;
import com.example.yangsong.piaoai.base.IBaseRequestCallBack;
import com.example.yangsong.piaoai.bean.Identify;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by ys on 2017/6/13.
 */

public class CodeModelImp extends BaseModel implements CodeModel<Identify>{
    private Context mContext = null;
    private ServiceApi serviceApi;
    private CompositeSubscription mCompositeSubscription;

    public CodeModelImp(Context context) {
        super();
        mContext = context;
        serviceApi = retrofitManager.getService();
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void getCode(String phoneNumber, String type, final IBaseRequestCallBack<Identify> iBaseRequestCallBack) {
        mCompositeSubscription.add(serviceApi.getCode(phoneNumber,type)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())   //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<Identify>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(Identify identify) {
                        iBaseRequestCallBack.requestSuccess(identify);

                    }

                })

        );
    }

    @Override
    public void onUnsubscribe() {

    }
}
