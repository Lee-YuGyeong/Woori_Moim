package com.woori.moim;

public class MoimItem {

    String moim_name;
    String user_name;

    public MoimItem(){}

    public MoimItem(String moim_name, String user_name) {
        this.moim_name = moim_name;
        this.user_name = user_name;
    }

    public String getMoim_name() {
        return moim_name;
    }

    public void setMoim_name(String moim_name) {
        this.moim_name = moim_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
