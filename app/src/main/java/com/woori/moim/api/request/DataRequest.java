package com.woori.moim.api.request;

import com.google.gson.annotations.SerializedName;

public class DataRequest {

    @SerializedName("dataHeader")
    private RequestHeader RequestHeaders;

    @SerializedName("dataBody")
    private RequestBody requestBodies;

    public void setRequestHeaders(RequestHeader RequestHeaders) {
        this.RequestHeaders = RequestHeaders;
    }

    public void setRequestBodies(RequestBody requestBodies) {
        this.requestBodies = requestBodies;
    }
}
