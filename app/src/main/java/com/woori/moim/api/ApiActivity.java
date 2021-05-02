package com.woori.moim.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.woori.moim.R;
import com.woori.moim.api.interfaces.MainActivityView;
import com.woori.moim.api.model.Body;
import com.woori.moim.api.request.DataRequest;
import com.woori.moim.api.request.RequestBody;
import com.woori.moim.api.request.RequestHeader;
import com.woori.moim.src.BaseActivity;

public class ApiActivity extends BaseActivity implements MainActivityView {

    TextView textView;
    ImageView button1;
    ImageView button2;
    ImageView imageView;
    FrameLayout frameLayout;
    int i=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        frameLayout = findViewById(R.id.api_iv_agree);

        button1 = findViewById(R.id.api_btn_cert);
        Glide.with(this).load(R.drawable.cell_cert).into(button1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomToast("MG31923982245218093511");
            }
        });

        button2 = findViewById(R.id.api_btn_next);
        Glide.with(this).load(R.drawable.button_next).into(button2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccntActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageView = findViewById(R.id.api_iv_check);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i *= -1;
                if (i == 1)
                    imageView.setImageResource(R.drawable.check);
                else
                    imageView.setImageResource(R.drawable.non_check);
            }
        });
        getData();
    }

    public void getData() {
        RequestBody requestBody = new RequestBody();
//        requestBody.setINQ_ACNO("1002123456789");
//        requestBody.setINQ_BAS_DT("20210220");
//        requestBody.setACCT_KND("P");
//        requestBody.setINQ_CUCD("KRW");

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

    }

    @Override
    public void failure(String msg) {
        //showCustomToast(msg);
    }
}