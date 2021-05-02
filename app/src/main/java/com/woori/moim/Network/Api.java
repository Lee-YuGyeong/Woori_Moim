package com.woori.moim.Network;

import com.woori.moim.Model;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface Api {
    @Headers({"Authorization: key=" + "AAAAzuqN2Wc:APA91bGBjphA6YtkwDcgz7u9R558B1OorepA2G_cQjczd0Qo8tMCmHL-miBGqTUe98qI6opTVKeEZ-p5fpKGyql34Kfri7cvEabpXYZnVCqGp_HpX3TlZzF2Iqn17SGWiTtjHzsSh_1U", "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(
            @Body Model root);

}
