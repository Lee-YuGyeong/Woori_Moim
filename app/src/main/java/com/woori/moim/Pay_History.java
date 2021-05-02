package com.woori.moim;

import java.util.ArrayList;

public class Pay_History {
    String pk;
    String moim_name;
    float total;
    String category;
    ArrayList<String> members;
    ArrayList<Integer> issend;
    String date;
    String content;
    String request;
    String request_token;

    public Pay_History(){}
    public Pay_History(String pk, String moim_name, float total, String category, ArrayList<String> members, ArrayList<Integer> issend, String date, String content, String request, String request_token) {
        this.pk=pk;
        this.moim_name = moim_name;
        this.total = total;
        this.category = category;
        this.members = members;
        this.issend=issend;
        this.date = date;
        this.content=content;
        this.request=request;
        this.request_token=request_token;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getMoim_name() {
        return moim_name;
    }

    public void setMoim_name(String moim_name) {
        this.moim_name = moim_name;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public ArrayList<Integer> getIssend() {
        return issend;
    }

    public void setIssend(ArrayList<Integer> issend) {
        this.issend = issend;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }
}
