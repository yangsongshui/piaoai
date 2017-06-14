package com.example.yangsong.piaoai.presenter;

import java.util.Map;

/**
 * 描述：MVP中的P接口定义
 */
public interface BindingPresenter {

    /**
     * @descriptoin	请求接口数据
     * @author	ys
     * @param map<String,String>  请求参数集合
     * @date 2017/6/13
     * @return
     */
    void binding(Map<String, String> map);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
