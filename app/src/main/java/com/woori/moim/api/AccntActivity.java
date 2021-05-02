package com.woori.moim.api;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woori.moim.HomeActivity;
import com.woori.moim.HomeDetail;
import com.woori.moim.R;
import com.woori.moim.SendActivity;
import com.woori.moim.api.interfaces.MainActivityView;
import com.woori.moim.api.model.Body;
import com.woori.moim.api.request.DataRequest;
import com.woori.moim.api.request.RequestBody;
import com.woori.moim.api.request.RequestHeader;
import com.woori.moim.src.BaseActivity;

public class AccntActivity extends BaseActivity implements MainActivityView {

    TextView textView;
    ImageView button1;
    ImageView button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accnt);

        button1 = findViewById(R.id.accnt_btn_cert);
        Glide.with(this).load(R.drawable.accnt_cert).into(button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
                //showCustomToast("인증되었습니다\n계좌번호 : 10026288*****");
            }
        });

        button2 = findViewById(R.id.accnt_btn_next);
        Glide.with(this).load(R.drawable.button_next).into(button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("accnt", "10026288*****");
//                editor.commit();

                Intent intent = new Intent(getApplicationContext(), HomeDetail.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void getData() {
        RequestBody requestBody = new RequestBody();
        requestBody.setCRTF_UNQ_NO("MG12345678901234567890");
        requestBody.setENCY_RRNO_LSNM("1234567");
        requestBody.setENCY_SMS_CRTF_NO("1111111");
        requestBody.setRRNO_BFNB("930216");

        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setUTZ_MCHR_APP_VER_NM("");
        requestHeader.setUTZ_MCHR_MDL_NM("");
        requestHeader.setUTZ_MCHR_OS_DSCD("");
        requestHeader.setUTZ_MCHR_OS_VER_NM("");
        requestHeader.setUTZPE_CNCT_IPAD("");
        requestHeader.setUTZPE_CNCT_MCHR_IDF_SRNO("");
        requestHeader.setUTZPE_CNCT_MCHR_UNQ_ID("");
        requestHeader.setUTZPE_CNCT_TEL_NO_TXT("");

        DataRequest dataRequest = new DataRequest();
        dataRequest.setRequestBodies(requestBody);
        dataRequest.setRequestHeaders(requestHeader);

        final MainService service = new MainService(this, this);
        service.postData(dataRequest);

    }

    @Override
    public void success(Body result) {
        String accnt = result.getREPT_FA().get(0).getCUS_USG_ACNO();

        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accnt", accnt);
        editor.commit();

        showCustomToast("인증되었습니다\n계좌번호 : " + accnt);
    }

    @Override
    public void failure(String msg) {
        showCustomToast(msg);
    }
}