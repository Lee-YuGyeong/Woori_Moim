package com.woori.moim.api.interfaces;


import com.woori.moim.api.model.Body;

public interface MainActivityView {

    void success(Body result);
    void failure(String msg);

}
