package com.woori.moim.api.model;

import com.google.gson.annotations.SerializedName;

public class DataResponse {

    @SerializedName("dataHeader")
    private Header headers;

    @SerializedName("dataBody")
    private Body bodies;


    public Header getHeader() {
        return headers;
    }

    public Body getBody() {
        return bodies;
    }

}
