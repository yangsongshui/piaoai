package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;

import java.util.Map;

/**
 * Created by ys on 2017/6/19.
 */

public interface PMDataModel<T> {
    /**
     * @descriptoin	获取网络数据
     * @author	ys
     * @param map 请求参数集合
     * @param iBaseRequestCallBack 数据的回调接口
     * @date 2017/2/17 19:01
     */
    void GetData(Map<String, String> map, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @descriptoin	注销subscribe
     * @author
     * @param
     * @date 2017/2/17 19:02
     * @return
     */
    void onUnsubscribe();
}
