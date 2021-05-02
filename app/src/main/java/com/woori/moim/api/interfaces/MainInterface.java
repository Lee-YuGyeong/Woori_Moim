package com.woori.moim.api.interfaces;



import com.woori.moim.api.model.DataResponse;
import com.woori.moim.api.request.DataRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MainInterface {

    @POST("/oai/wb/v1/login/executeCellCerti")
    Call<DataResponse> postData(@Body DataRequest dataRequest);

}
