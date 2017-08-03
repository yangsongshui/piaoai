package com.example.yangsong.piaoai.model;

import android.content.Context;

import com.example.yangsong.piaoai.api.ServiceApi;
import com.example.yangsong.piaoai.base.BaseModel;
import com.example.yangsong.piaoai.base.IBaseRequestCallBack;
import com.example.yangsong.piaoai.bean.CityData;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 描述：MVP中的M实现类，处理业务逻辑数据
 * 作者：dc on 2017/2/16 11:05
 * 邮箱：597210600@qq.com
 */
public class CityDataModelImp extends BaseModel implements CityDataModel<CityData> {

    private Context context = null;
    private ServiceApi serviceApi;
    private CompositeSubscription mCompositeSubscription;

    public CityDataModelImp(Context mContext) {
        super();
        context = mContext;
        serviceApi = retrofitManager.getService();
        mCompositeSubscription = new CompositeSubscription();
    }


    @Override
    public void cityData(String city, final IBaseRequestCallBack<CityData> iBaseRequestCallBack) {
        mCompositeSubscription.add(serviceApi.findCityData(city)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())   //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<CityData>() {

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
                    public void onNext(CityData msg) {
                        iBaseRequestCallBack.requestSuccess(msg);

                    }

                })
        );
    }

    @Override
    public void onUnsubscribe() {
        //判断状态
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.clear();  //注销
            mCompositeSubscription.unsubscribe();
        }
    }
}
