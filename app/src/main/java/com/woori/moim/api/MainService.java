package com.woori.moim.api;

import android.content.Context;


import com.woori.moim.api.interfaces.MainActivityView;
import com.woori.moim.api.interfaces.MainInterface;
import com.woori.moim.api.model.DataResponse;
import com.woori.moim.api.request.DataRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.woori.moim.src.ApplicationClass.getRetrofit;


public class MainService {

    Context mContext;
    private MainActivityView mView;

    public MainService(final MainActivityView view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    // 계좌 인증
    public void postData(DataRequest dataRequest) {
        final MainInterface mainInterface = getRetrofit().create(MainInterface.class);
        mainInterface.postData(dataRequest).enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                final DataResponse dataResponse = response.body();
                if (dataResponse == null) {
                    mView.failure("빈 값");
                    return;
                }
                mView.success(dataResponse.getBody());
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                mView.failure("서버 연결 실패");
            }
        });
    }
}
