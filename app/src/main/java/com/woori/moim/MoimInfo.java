package com.woori.moim;

import java.util.HashMap;
import java.util.Map;

public class MoimInfo {

    public String moim_name;


    public MoimInfo(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public MoimInfo(String moim_name) {
        this.moim_name = moim_name;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("moim_name", moim_name);

        return result;
    }

}
