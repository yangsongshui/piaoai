package com.example.yangsong.piaoai.bean;

/**
 * Created by ys on 2017/6/13.
 */

public class Identify {

    /**
     * resCode : 0
     * resMessage : 发送成功！
     * resBody : {"identify":"407138"}
     */

    private String resCode;
    private String resMessage;
    private ResBodyBean resBody;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public ResBodyBean getResBody() {
        return resBody;
    }

    public void setResBody(ResBodyBean resBody) {
        this.resBody = resBody;
    }

    public static class ResBodyBean {
        /**
         * identify : 407138
         */

        private String identify;

        public String getIdentify() {
            return identify;
        }

        public void setIdentify(String identify) {
            this.identify = identify;
        }
    }
}
