package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.woori.moim.Network.Api;
import com.woori.moim.Network.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class Send_MoneyActivity extends AppCompatActivity {
    String request, address, money, moim, total, request_token, pk;
    float f_money, f_total;
    ArrayList<PeopleItem> members;
    TextView request_text, divide_text, total_text;
    ImageView deny, accept;
    RecyclerView moim_recyclerview;
    Send_member_Adapter adapter;
    String myname;//나중에 바꿔야 할 정보
    int numofpp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send__money);

        setting_view();
        setting_data();
        setting_deny_btn();
        setting_accept_btn();
    }

    public void setting_view() {
        request_text = findViewById(R.id.request_moim);
        divide_text = findViewById(R.id.divide);
        total_text = findViewById(R.id.total);
        moim_recyclerview = findViewById(R.id.moim_recyclerview);
        deny = findViewById(R.id.deny);
        accept = findViewById(R.id.accept);
        Glide.with(this).load(R.drawable.deny).into(deny);
        Glide.with(this).load(R.drawable.accept).into(accept);

        myname = getSharedPreferences("mine", MODE_PRIVATE).getString("name", "no");
    }

    public void setting_data() {
        Intent intent = getIntent();
        pk = intent.getStringExtra("pk");
        money = intent.getStringExtra("divide");
        request_token = intent.getStringExtra("request_token");
        System.out.println(request_token);
        System.out.println("Send_Money");
        f_money = Float.parseFloat(money);
        address = intent.getStringExtra("address");
        request = intent.getStringExtra("request");
        moim = intent.getStringExtra("moim_name");
        String[] narray = intent.getStringExtra("name").split("\n");
        String[] iarray = intent.getStringExtra("id").split("\n");
        numofpp = narray.length;
        members = new ArrayList<>();
        if (iarray.length != narray.length) {
            iarray = null;
            for (int i = 0; i < narray.length; i++) {
                members.add(new PeopleItem(narray[i], " "));
            }
        } else {
            for (int i = 0; i < narray.length; i++) {
                members.add(new PeopleItem(narray[i], iarray[i]));
            }
        }

        total = intent.getStringExtra("total");
        f_total = Float.parseFloat(total);

        request_text.setText(request + "님이\n" + moim + "의 결제 요청하였어요.");
        total_text.setText("총 " + total + "원");
        divide_text.setText(money + "원");

        adapter = new Send_member_Adapter(members, this);
        moim_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        moim_recyclerview.setAdapter(adapter);
    }

    public void setting_deny_btn() {
        deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddenyNotification(request_token);
                finish();
                startActivity(new Intent(Send_MoneyActivity.this, HomeActivity.class));
            }
        });
    }

    public void setting_accept_btn() {
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /************나중에 최종 결제 완료로 옮겨갈 코드**********/

//                DatabaseReference ref= FirebaseDatabase.getInstance().getReference("history/"+pk+"/issend");
//                for(int i=0; i<members.size(); i++){
//                    if(members.get(i).getName().equals(request)){
//                        HashMap<String, Object> map=new HashMap<>();
//                        map.put(Integer.toString(i), 1);
//                        ref.updateChildren(map);
//                    }
//                    else if(members.get(i).getName().equals(myname)){
//                        HashMap<String, Object> map=new HashMap<>();
//                        map.put(Integer.toString(i), 1);
//                        ref.updateChildren(map);
//                    }
//                    else{
//                        HashMap<String, Object> map=new HashMap<>();
//                        map.put(Integer.toString(i), 0);
//                        ref.updateChildren(map);
//                    }
//                }

//                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("history/" + pk);
//                ArrayList<Integer> issend = new ArrayList<>();
//                for (int i = 0; i < members.size(); i++) {
//                    if (members.get(i).getName().equals(request)) {
//                        issend.add(1);
//                    } else if (members.get(i).getName().equals(myname)) {
//                        issend.add(1);
//                    } else {
//                        issend.add(0);
//                    }
//                }
//                HashMap<String, Object> map = new HashMap<>();
//                map.put("issend", issend);
//                ref.updateChildren(map);
                /*************************************/

                Intent intent = new Intent(getApplicationContext(), SendActivity.class);
                intent.putExtra("money", money);
                intent.putExtra("request",request);
                startActivity(intent);

                finish();

            }
        });
    }

    public void senddenyNotification(String token) {
        //System.out.println(token);
        Model model = new Model(token, new NotificationModel("계좌이체 거절", myname + "이 계좌이체를 거절하였습니다", "HomeActivity", "transfer"));
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
}