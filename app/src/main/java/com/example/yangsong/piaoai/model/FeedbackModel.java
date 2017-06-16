package com.example.yangsong.piaoai.model;

import com.example.yangsong.piaoai.base.IBaseRequestCallBack;


/**
 * 描述：MVP中的M；处理获取网络信息数据
 */
public interface FeedbackModel<T> {

    /**
     * @param phoneNumber          反馈用户手机号
     * @param remark               反馈内容
     * @param iBaseRequestCallBack 数据的回调接口
     * @descriptoin 获取网络数据
     * @author ys
     * @date 2017/2/17 19:01
     */
    void addRemark(String phoneNumber, String remark, IBaseRequestCallBack<T> iBaseRequestCallBack);

    /**
     * @param
     * @return
     * @descriptoin 注销subscribe
     * @author
     * @date 2017/2/17 19:02
     */
    void onUnsubscribe();
}
