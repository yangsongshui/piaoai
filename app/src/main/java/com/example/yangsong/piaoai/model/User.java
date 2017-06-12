package com.example.yangsong.piaoai.model;

/**
 * Created by yangsong on 2017/5/25.
 */

public class User {
    String name;
    String psw;
    String phone;
    String pic;

    public User() {
    }

    public User(String phone, String psw) {
        this.phone = phone;
        this.psw = psw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
