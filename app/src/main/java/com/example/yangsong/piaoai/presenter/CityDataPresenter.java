package com.example.yangsong.piaoai.presenter;

/**
 * 描述：MVP中的P接口定义
 */
public interface CityDataPresenter {

    /**
     * @descriptoin	请求接口数据
     * @author	ys
     * @param city  请求城市
     * @date 2017/6/13
     * @return
     */
    void binding(String city);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
