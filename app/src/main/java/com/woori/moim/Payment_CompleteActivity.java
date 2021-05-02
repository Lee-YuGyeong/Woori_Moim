package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.woori.moim.Network.Api;
import com.woori.moim.Network.ApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class Payment_CompleteActivity extends AppCompatActivity {
    ImageView sendpush_btn;
    float total;
    float divide;
    ArrayList<String> name, id, token;
    String myaddress;
    String request;
    String moim_name;
    String request_token;
    String pk;
    String date;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__complete);

        setting_view();
        sending_data();
        sending_pushalarm_for_transfer();
    }
    public void setting_view(){
        sendpush_btn=findViewById(R.id.sendpush_btn);

        context=this;
        myaddress="1234 5789 1234 5612";
        Intent intent=getIntent();
        total=intent.getFloatExtra("total", 1);
        name=intent.getStringArrayListExtra("name");
        id=intent.getStringArrayListExtra("id");
        token=intent.getStringArrayListExtra("token");
        moim_name=intent.getStringExtra("moim_name");
        divide=total/(name.size());

        request=getSharedPreferences("mine", MODE_PRIVATE).getString("name", "no");
        request_token=getSharedPreferences("mine", MODE_PRIVATE).getString("token", "no");
        //request_token="dEXN6QpQRLCUT7-ZixEKKK:APA91bE0t5VduUVIs6iBbY9yLMkkoOk9uIJkpB1nFcUvvhdn3gV_aNE4Q2yWP8vptgBHv5_fPUaAzWydX7CH8MlAVDn7MhcL7M9fV-dBSB_qZv-5yR3YXDSO9rgpVpzDfJqp2ur12SUG";
        //나중에 지울거
    }

    public void sending_pushalarm_for_transfer(){
        sendpush_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0; i<name.size(); i++){
                    if(name.get(i).equals(request)){
                        continue;
                    }
                    else{
                        sendNotificationToUser(token.get(i));
                    }
                }

                Toast.makeText(Payment_CompleteActivity.this, "계좌이체 요청을 보냈습니다.", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Select_CategoryActivity.class);
                intent.putExtra("request", request);
                intent.putExtra("moim", moim_name);
                intent.putExtra("total", total);
                intent.putExtra("date", date);
                intent.putStringArrayListExtra("name", name);
                intent.putStringArrayListExtra("id", id);
                intent.putExtra("pk", pk);
                finish();
                startActivity(intent);
            }
        });
    }
    public void sendNotificationToUser(String token){
        String n="";
        String Id="";
        for(int i=0; i<name.size(); i++){
            n+=name.get(i);
            n+="\n";
            Id+=id.get(i);
            Id+="\n";
        }
        //System.out.println(request_token);
        //System.out.println("CompleteActivity");
        System.out.println(request);
        System.out.println(request_token);
        Model model = new Model(token, new NotificationModel("결제알림", "계좌이체 부탁드립니다!!", "Send_MoneyActivity", Float.toString(divide), myaddress, n, Id, request, moim_name, Float.toString(total), request_token, pk));
        Api apiService = ApiClient.getClient().create(Api.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(model);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("testtest", "성공");
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d("testtest", "실패");
            }
        });
    }

    public String getRandomStr(){//make name of image sending to server
        char[] tmp=new char[9];//'train_'+random 9 characters 0~9,A~Z
        for(int i=0; i<tmp.length; i++){
            int div=(int)Math.floor(Math.random()*2);
            if(div==0){
                tmp[i]=(char)(Math.random()*10+'0');
            }else{
                tmp[i]=(char)(Math.random()*26+'A');
            }
        }
        return new String(tmp);
    }
    public void sending_data(){
        request=getSharedPreferences("mine", MODE_PRIVATE).getString("name", "no");
        ArrayList<Integer> issend=new ArrayList<>();
        for(int i=0; i<name.size(); i++){
            if(name.get(i).equals(request)){
                issend.add(1);
            }
            else{
                issend.add(0);
            }
        }
        SimpleDateFormat format=new SimpleDateFormat("yyyy년 MM월dd일 HH시mm분ss초");
        Date time=new Date();
        date=format.format(time);
        pk=date.replace(" ", "")+moim_name+getRandomStr();
        Pay_History his=new Pay_History(pk, moim_name, total, "", name, issend, date, "해피해피가게", request, request_token);
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference ref=database.getReference("history/"+pk);
        ref.setValue(his);
    }
}