package com.example.yangsong.piaoai.presenter;

/**
 * Created by ys on 2017/6/13.
 */

public interface ThirdLoginPresenter {

    /**
     * @descriptoin	登陆接口
     * @author	ys
     * @param openid 第三方登陆唯一标识符
     * @date 2017/6/13
     * @return
     */
    void loadLogin(String openid);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
