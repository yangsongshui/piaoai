package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;


/**
 * 描述：MVP中的M；处理获取网络信息数据
 */
public interface CityDataModel<T> {

    /**
     * @descriptoin	获取网络数据
     * @author	ys
     * @param city 请求城市
     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void cityData(String city, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
