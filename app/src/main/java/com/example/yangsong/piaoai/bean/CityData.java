package com.example.yangsong.piaoai.bean;

import java.util.List;

/**
 * Created by ys on 2017/8/3.
 */

public class CityData {

    /**
     * resCode : 0
     * resMessage : 查询成功！
     * resBody : {"list":[{"pm2_5":"10","ct":"2017-07-19 13:02:18"},{"pm2_5":"11","ct":"2017-07-19 14:02:05"},{"pm2_5":"11","ct":"2017-07-19 15:02:05"},{"pm2_5":"11","ct":"2017-07-19 16:02:05"},{"pm2_5":"11","ct":"2017-07-19 17:02:10"},{"pm2_5":"11","ct":"2017-07-19 18:02:02"},{"pm2_5":"11","ct":"2017-07-19 19:02:04"}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * pm2_5 : 10
             * ct : 2017-07-19 13:02:18
             */

            private String pm2_5;
            private String ct;

            public String getPm2_5() {
                return pm2_5;
            }

            public void setPm2_5(String pm2_5) {
                this.pm2_5 = pm2_5;
            }

            public String getCt() {
                return ct;
            }

            public void setCt(String ct) {
                this.ct = ct;
            }
        }
    }
}
