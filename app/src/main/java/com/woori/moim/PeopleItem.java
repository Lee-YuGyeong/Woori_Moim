package com.woori.moim;

public class PeopleItem {

    String name;
    String id;
    String token;

    public PeopleItem(){}

    public PeopleItem(String name) {
        this.name = name;
    }

    public PeopleItem(String name, String id){
        this.name=name;
        this.id=id;
    }

    public PeopleItem(String name, String id, String token) {
        this.name = name;
        this.id = id;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
