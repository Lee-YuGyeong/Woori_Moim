package com.woori.moim;

import java.util.HashMap;
import java.util.Map;

public class CardInfo {
    public String num;
    public String date;
    public String cvc;
    public String pw;

    public CardInfo() {
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }

    public CardInfo(String num, String date, String cvc, String pw) {
        this.num = num;
        this.date = date;
        this.cvc = cvc;
        this.pw = pw;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("num", num);
        result.put("date", date);
        result.put("cvc", cvc);
        result.put("pw", pw);
        return result;
    }

}
