package com.example.yangsong.piaoai.bean;

/**
 * Created by yangsong on 2017/5/25.
 */

public class User {


    /**
     * resCode : 0
     * resMessage : 登录成功！
     * resBody : {"position":"","birthday":"","sex":"","phoneNumber":"18212345678","email":"","nickName":"","department":"","role":"0","headPic":"","date":"2017-06-27 14:54:44","passWord":"e10adc3949ba59abbe56e057f20f883e","city":""}
     */

    private String resCode;
    private String resMessage;
    private ResBodyBean resBody;

    @Override
    public String toString() {
        return "User{" +
                "resCode='" + resCode + '\'' +
                ", resMessage='" + resMessage + '\'' +
                ", resBody=" + resBody +
                '}';
    }

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
         * position :
         * birthday :
         * sex :
         * phoneNumber : 18212345678
         * email :
         * nickName :
         * department :
         * role : 0
         * headPic :
         * date : 2017-06-27 14:54:44
         * passWord : e10adc3949ba59abbe56e057f20f883e
         * city :
         */

        private String position;
        private String birthday;
        private String sex;
        private String phoneNumber;
        private String email;
        private String nickName;
        private String department;
        private String role;
        private String headPic;
        private String date;
        private String passWord;
        private String city;

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
