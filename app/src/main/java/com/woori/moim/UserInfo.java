package com.woori.moim;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    public String id;
    public String name;
    public String token;

    public UserInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public UserInfo(String id, String name, String token) {
        this.id = id;
        this.name = name;
        this.token = token;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("token", token);
        return result;
    }

}
