package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.woori.moim.Network.Api;
import com.woori.moim.Network.ApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class PaylistActivity extends AppCompatActivity {
    ImageView left, right, bg;
    TextView date;
    RecyclerView list_recyclerview;
    int year, month;
    int select_cat=0;
    String cc=null;
    Context context;

    String moim_name;//임시, 받아와야할 내용
    ArrayList<Pay_History> total;
    ArrayList<Pay_History> nowlist;
    List_Adapter adapter;
    ArrayList<ImageView> list2;
    String content_ch, date_ch, category_ch, pk_ch;
    float money_ch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paylist);

        setting_view();

        setting_recyclerview();
        setting_left_btn();
        setting_right_btn();

    }

    public void setting_view(){
        context=this;
        left=findViewById(R.id.left_img);
        right=findViewById(R.id.right_img);
        bg=findViewById(R.id.date_bg);
        list_recyclerview=findViewById(R.id.list_recyclerview);
        date=findViewById(R.id.date_txt);

        getting_today();
        Glide.with(this).load(R.drawable.left_arrow).into(left);
        Glide.with(this).load(R.drawable.right_arrow).into(right);
        Glide.with(this).load(R.drawable.date_background).into(bg);
        date.setText(year+"."+month);
        total=new ArrayList<>();
        list2=new ArrayList<>();
    }

    public void getting_today(){
        Calendar cal=Calendar.getInstance();

        year=cal.get(cal.YEAR);
        month=cal.get(cal.MONTH)+1;
    }

    public void setting_left_btn(){
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month=month-1;
                if(month==0){
                    month=12;
                    year=year-1;
                }
                date.setText(year+"."+month);
                change_recyclerview();
            }
        });
    }
    public void setting_right_btn(){
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                month=month+1;
                if(month==13){
                    month=1;
                    year=year+1;
                }
                date.setText(year+"."+month);
                change_recyclerview();
            }
        });
    }
    public void setting_recyclerview(){
        total=ManagePayHistory.getInstance().getList();
//        for(int i=0; i<temp.size(); i++){
//            if(temp.get(i).getMoim_name().equals(moim_name)){
//                total.add(temp.get(i));
//            }
//        }
        setting_nowlist();
        adapter=new List_Adapter(nowlist, context);
        list_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        list_recyclerview.setAdapter(adapter);

        adapter.setOnRequestItemClickListener(new List_Adapter.OnRequestItemClickListener() {
            @Override
            public void requestItemClick(List_Adapter.ListViewHolder holder, View view, int position) {
                String names="";
                int cnt=0;
                if(!getSharedPreferences("mine", MODE_PRIVATE).getString("name", "no").equals(nowlist.get(position).getRequest())){
                    Toast.makeText(context, "대표 결제자만 요청할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                for(int i=0; i<nowlist.get(position).getIssend().size(); i++){
                    if(nowlist.get(position).getMembers().get(i).equals(nowlist.get(position).getRequest())){
                        continue;
                    }
                    if(nowlist.get(position).getIssend().get(i)==0){
                        cnt++;
                        names=names+nowlist.get(position).getMembers().get(i)+"님"+" ";
                        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("token/"+nowlist.get(position).getMembers().get(i));
                        ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                sendNotificationToUser(snapshot.getValue().toString(), position);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
                if(cnt==0){
                    Toast.makeText(context, "미납 인원이 없어요.", Toast.LENGTH_SHORT).show();
                }
                else{
                    AlertDialog.Builder bul=new AlertDialog.Builder(context)
                            .setTitle("계좌이체전송알림")
                            .setMessage(names+"에게\n계좌이체 요청 알람을 보냈습니다")
                            .setCancelable(false)
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    AlertDialog al=bul.create();
                    al.show();
                }
            }
        });

        adapter.setOnChangeItemClickListener(new List_Adapter.OnChangeItemClickListener() {
            @Override
            public void changeItemClick(List_Adapter.ListViewHolder holder, View view, TextView cat, int position) {
                select_cat=-1;
                LinearLayout category=(LinearLayout)View.inflate(context, R.layout.select_category, null);
                if(list2.size()!=0){
                    list2.clear();
                }
                list2.add((ImageView)category.findViewById(R.id.b00));
                list2.add((ImageView)category.findViewById(R.id.b20));
                list2.add((ImageView)category.findViewById(R.id.b30));
                list2.add((ImageView)category.findViewById(R.id.b40));
                list2.add((ImageView)category.findViewById(R.id.b50));
                list2.add((ImageView)category.findViewById(R.id.b60));
                list2.add((ImageView)category.findViewById(R.id.b70));
                list2.add((ImageView)category.findViewById(R.id.b80));
                list2.add((ImageView)category.findViewById(R.id.b90));
                ImageView i1=category.findViewById(R.id.i00);
                ImageView i2=category.findViewById(R.id.i20);
                ImageView i3=category.findViewById(R.id.i30);
                ImageView i4=category.findViewById(R.id.i40);
                ImageView i5=category.findViewById(R.id.i50);
                ImageView i6=category.findViewById(R.id.i60);
                ImageView i7=category.findViewById(R.id.i70);
                ImageView i8=category.findViewById(R.id.i80);
                ImageView i9=category.findViewById(R.id.i90);
                Glide.with(context).load(R.drawable.a1).into(i1);
                Glide.with(context).load(R.drawable.a2).into(i2);
                Glide.with(context).load(R.drawable.a3).into(i3);
                Glide.with(context).load(R.drawable.a4).into(i4);
                Glide.with(context).load(R.drawable.a5).into(i5);
                Glide.with(context).load(R.drawable.a6).into(i6);
                Glide.with(context).load(R.drawable.a7).into(i7);
                Glide.with(context).load(R.drawable.a8).into(i8);
                Glide.with(context).load(R.drawable.a9).into(i9);
                i1.setOnClickListener(categoryclick);
                i2.setOnClickListener(categoryclick);
                i3.setOnClickListener(categoryclick);
                i4.setOnClickListener(categoryclick);
                i5.setOnClickListener(categoryclick);
                i6.setOnClickListener(categoryclick);
                i7.setOnClickListener(categoryclick);
                i8.setOnClickListener(categoryclick);
                i9.setOnClickListener(categoryclick);
                AlertDialog categorydialog=new AlertDialog.Builder(context)
                        .setView(category)
                        .setCancelable(false)
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", null)
                        .create();
                categorydialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button=((AlertDialog)categorydialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(select_cat==-1){
                                    Toast.makeText(context, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    content_ch=nowlist.get(position).getContent();
                                    date_ch=nowlist.get(position).getDate();
                                    money_ch=nowlist.get(position).getTotal();
                                    category_ch=nowlist.get(position).getCategory();
                                    pk_ch=nowlist.get(position).getPk();

                                    change_ui(select_cat, cat);
                                    change_managehistory();
                                    change_server();
                                    categorydialog.dismiss();
                                }
                            }
                        });
                    }
                });
                categorydialog.show();
            }
        });
    }

    public void change_recyclerview(){
        setting_nowlist();
        adapter.changeItem(nowlist);
    }
    public void setting_nowlist(){
        if(nowlist!=null){
            nowlist=null;
        }
        nowlist=new ArrayList<>();
        System.out.println(total.size()+"here");
        for(int i=0; i<total.size(); i++){
            int y=total.get(i).getDate().indexOf("년");
            y=Integer.parseInt(total.get(i).getDate().substring(0, y));
            int b=total.get(i).getDate().indexOf("년");
            int d=total.get(i).getDate().indexOf("월");
            b=Integer.parseInt(total.get(i).getDate().substring(b+1, d).trim());
            if(year==y&&month==b){
                nowlist.add(total.get(i));
            }
        }
    }

    public View.OnClickListener categoryclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch(id){
                case R.id.i00:
                    select_cat=1;
                    changecolor2(select_cat);
                    return;
                case R.id.i20:
                    select_cat=2;
                    changecolor2(select_cat);
                    return;
                case R.id.i30:
                    select_cat=3;
                    changecolor2(select_cat);
                    return;
                case R.id.i40:
                    select_cat=4;
                    changecolor2(select_cat);
                    return;
                case R.id.i50:
                    select_cat=5;
                    changecolor2(select_cat);
                    return;
                case R.id.i60:
                    select_cat=6;
                    changecolor2(select_cat);
                    return;
                case R.id.i70:
                    select_cat=7;
                    changecolor2(select_cat);
                    return;
                case R.id.i80:
                    select_cat=8;
                    changecolor2(select_cat);
                    return;
                case R.id.i90:
                    select_cat=9;
                    changecolor2(select_cat);
                    return;
            }
        }
    };
    public void changecolor2(int flag){
        flag=flag-1;
        for(int i=0; i<9; i++){
            if(i==flag){
                list2.get(i).setBackgroundColor(Color.YELLOW);
            }
            else{
                list2.get(i).setBackgroundColor(Color.WHITE);
            }
        }
    }
    public void change_ui(int c, TextView cat){
        if(c==1){
            cc="영화/문화";
        }
        else if(c==2){
            cc="레저/스포츠";
        }
        else if(c==3){
            cc="식당";
        }
        else if(c==4){
            cc="카페";
        }
        else if(c==5){
            cc="여가";
        }
        else if(c==6){
            cc="술/유흥";
        }
        else if(c==7){
            cc="숙박";
        }
        else if(c==8){
            cc="경조사";
        }
        else if(c==9){
            cc="기타";
        }
        cat.setText(cc);
    }
    public void change_managehistory(){
        if(total!=null){
            total=null;
        }
        total=new ArrayList<>();

        total=ManagePayHistory.getInstance().getList();
        for(int i=0; i<total.size(); i++){
            if(total.get(i).getPk().equals(pk_ch)){
                total.get(i).setCategory(cc);
            }
        }
        System.out.println(total.size());
        ManagePayHistory.getInstance().setList(total);
    }
    public void change_server(){
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference("history/"+pk_ch);
        HashMap<String, Object> map=new HashMap<>();
        map.put("category", cc);
        databaseReference.updateChildren(map);
    }

    public void sendNotificationToUser(String token, int pos){
        System.out.println("start");
        System.out.println(pos);
        String n="";
        String id="";
        for(int i=0; i<nowlist.get(pos).getMembers().size(); i++){
            System.out.println(n);
            n+=nowlist.get(pos).getMembers().get(i);
            n+="\n";
            id+="";
            id+="\n";
        }
        System.out.println(token);
        System.out.println("send her");
        Model model=new Model(token, new NotificationModel("결제알림", "계좌이체 부탁드립니다!!", "Send_MoneyActivity", Float.toString(nowlist.get(pos).getTotal()/nowlist.get(pos).getMembers().size()), "1234 5689 1234", n, id, nowlist.get(pos).getRequest(), nowlist.get(pos).getMoim_name(), Float.toString(nowlist.get(pos).getTotal()), nowlist.get(pos).getRequest_token(), nowlist.get(pos).getPk()));
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