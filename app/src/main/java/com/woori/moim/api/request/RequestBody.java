package com.woori.moim.api.request;

import com.google.gson.annotations.SerializedName;

public class RequestBody {
    @SerializedName("RRNO_BFNB")
    private String RRNO_BFNB;
    @SerializedName("ENCY_RRNO_LSNM")
    private String ENCY_RRNO_LSNM;
    @SerializedName("ENCY_SMS_CRTF_NO")
    private String ENCY_SMS_CRTF_NO;
    @SerializedName("CRTF_UNQ_NO")
    private String CRTF_UNQ_NO;

    public void setRRNO_BFNB(String RRNO_BFNB) {
        this.RRNO_BFNB = RRNO_BFNB;
    }

    public void setENCY_RRNO_LSNM(String ENCY_RRNO_LSNM) {
        this.ENCY_RRNO_LSNM = ENCY_RRNO_LSNM;
    }

    public void setENCY_SMS_CRTF_NO(String ENCY_SMS_CRTF_NO) {
        this.ENCY_SMS_CRTF_NO = ENCY_SMS_CRTF_NO;
    }

    public void setCRTF_UNQ_NO(String CRTF_UNQ_NO) {
        this.CRTF_UNQ_NO = CRTF_UNQ_NO;
    }
}
