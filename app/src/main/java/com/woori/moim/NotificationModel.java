package com.woori.moim;

import java.util.ArrayList;

public class NotificationModel {
    private String title;
    private String body;
    private String clickAction;
    private String money;
    private String address;
    private String test;
    private String name;
    private String id;
    private String request;
    private String moim_name;
    private String total;
    private String request_token;
    private String pk;


    public NotificationModel(String title, String body, String clickAction, String test) {
        this.title = title;
        this.body = body;
        this.clickAction = clickAction;
        this.test = test;
    }


    public NotificationModel(String title, String body, String clickAction, String name, String id, String moim_name) {
        this.title = title;
        this.body = body;
        this.clickAction = clickAction;
        this.name = name;
        this.id = id;
        this.moim_name = moim_name;
    }


    public NotificationModel(String title, String body, String clickAction, String money, String address, String name, String id, String request, String moim_name, String total, String request_token, String pk) {
        this.title = title;
        this.body = body;
        this.clickAction = clickAction;
        this.money = money;
        this.address = address;
        this.name = name;
        this.id = id;
        this.request = request;
        this.moim_name = moim_name;
        this.total = total;
        this.request_token=request_token;
        this.pk=pk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getClickAction() {
        return clickAction;
    }

    public void setClickAction(String clickAction) {
        this.clickAction = clickAction;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getMoim_name() {
        return moim_name;
    }

    public void setMoim_name(String moim_name) {
        this.moim_name = moim_name;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
}