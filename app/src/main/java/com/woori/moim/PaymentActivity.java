package com.woori.moim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {
    TextView pay_timer;
    ImageView barcode_img;
    TextView message;
    ImageView payment_btn;
    Timer timer;
    ArrayList<String> name, id, token;
    String moim_name;
    Context context;
    int pay_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        setting_view();
        setting_barcode();
        timer=new Timer(600000, 1000);
        timer.start();
    }

    public void setting_view(){
        context=this;
        Intent intent=getIntent();
        name=intent.getStringArrayListExtra("name");
        id=intent.getStringArrayListExtra("id");
        token=intent.getStringArrayListExtra("token");
        moim_name=intent.getStringExtra("moim_name");
        pay_timer=findViewById(R.id.pay_timer);
        barcode_img=findViewById(R.id.barcode_img);
        message=findViewById(R.id.message);
        payment_btn=findViewById(R.id.payment_btn);
        Glide.with(this).load(R.drawable.pay).into(payment_btn);
        payment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pay_flag==0){
                    Toast.makeText(context, "아직 결제가 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
                else if(pay_flag==1){
                    Intent intent=new Intent(context, Payment_CompleteActivity.class);
                    intent.putStringArrayListExtra("name", name);
                    intent.putStringArrayListExtra("id", id);
                    intent.putStringArrayListExtra("token", token);
                    intent.putExtra("moim_name", moim_name);
                    intent.putExtra("total", 30000f);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
    public void setting_barcode(){
        Bitmap bitmap;
        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
        try{
            bitmap=barcodeEncoder.encodeBitmap(getIntent().getStringExtra("account"), BarcodeFormat.CODE_128, 800, 150);
            barcode_img.setImageBitmap(bitmap);
        }
        catch(Exception e){

        }
    }
    public class Timer extends CountDownTimer {
        public Timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minute, second;
            long total=millisUntilFinished/1000;
            minute=total/60;
            second=total%60;
            pay_timer.setText("결제 가능 시간: "+minute+"분 "+second+"초");
            if(600-total==15){
                message.setText("결제가 완료되었습니다\n하단 결제 버튼을 눌러주세요");
                timer.cancel();
                pay_flag=1;
            }
        }

        @Override
        public void onFinish() {
            pay_timer.setText("결제 가능 시간: 0초");
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setTitle("결제 가능 시간 초과")
                    .setMessage("결제 가능 시간을 초과하였습니다\n전 페이지로 돌아가\n다시 모임원을 선택해주세요")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(context, Select_MemberActivity.class));
                            finish();
                        }
                    })
                    .setCancelable(false);
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
    }
}