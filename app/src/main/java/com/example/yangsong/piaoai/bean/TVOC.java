package com.example.yangsong.piaoai.bean;

import java.util.List;

/**
 * Created by ys on 2017/6/19.
 */

public class TVOC {

    /**
     * resCode : 0
     * resMessage : 查询成功！
     * resBody : {"list":[["1","F1:03:00:00:01","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","2017-06-19 10:25:22.0"]]}
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
        private List<List<String>> list;

        public List<List<String>> getList() {
            return list;
        }

        public void setList(List<List<String>> list) {
            this.list = list;
        }
    }
}
