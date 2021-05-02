package com.woori.moim;

import java.util.ArrayList;

public class Moim {
    String moim_name;
    ArrayList<PeopleItem> members;

    public Moim(){}

    public Moim(String moim_name, ArrayList<PeopleItem> members) {
        this.moim_name = moim_name;
        this.members = members;
    }

    public String getMoim_name() {
        return moim_name;
    }

    public void setMoim_name(String moim_name) {
        this.moim_name = moim_name;
    }

    public ArrayList<PeopleItem> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<PeopleItem> members) {
        this.members = members;
    }
}
