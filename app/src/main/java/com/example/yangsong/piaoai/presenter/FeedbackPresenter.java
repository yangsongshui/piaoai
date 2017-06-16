package com.example.yangsong.piaoai.presenter;

/**
 * 描述：MVP中的P接口定义
 */
public interface FeedbackPresenter {

    /**
     * @descriptoin	请求接口数据
     * @author	ys
     * @param phoneNumber          反馈用户手机号
     * @param remark               反馈内容
     * @date 2017/6/13
     * @return
     */
    void addRemark(String phoneNumber, String remark);

    /**
     * @descriptoin	注销subscribe
     * @author	ys
     * @date 2017/6/13
     */
    void unSubscribe();
}
