package com.woori.moim;

public class CardSelectItem {

    String num;
    String date;
    String cvc;
    String pw;

    public CardSelectItem() {

    }


    public CardSelectItem(String num) {
        this.num = num;
    }

    public CardSelectItem(String num, String pw) {
        this.num = num;
        this.pw = pw;
    }

    public CardSelectItem(String num, String date, String cvc, String pw) {
        this.num = num;
        this.date = date;
        this.cvc = cvc;
        this.pw = pw;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
