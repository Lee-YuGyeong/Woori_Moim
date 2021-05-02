package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Select_CategoryActivity extends AppCompatActivity {
    RadioGroup rg;
    RadioButton r1, r2, r3, r4, r5, r6, r7, r8, r9;
    ImageView category_btn;
    String moim, request;
    float total;
    ArrayList<String> name, id;
    String date;
    String pk;
    String category=null;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__category);

        setting_view();
        setting_radio_btn();
        setting_btn();
    }

    public void setting_view(){
        rg=findViewById(R.id.rg);
        r1=findViewById(R.id.r1);
        r2=findViewById(R.id.r2);
        r3=findViewById(R.id.r3);
        r4=findViewById(R.id.r4);
        r5=findViewById(R.id.r5);
        r6=findViewById(R.id.r6);
        r7=findViewById(R.id.r7);
        r8=findViewById(R.id.r8);
        r9=findViewById(R.id.r9);
        category_btn=findViewById(R.id.category_btn);
        Glide.with(this).load(R.drawable.pay_complete).into(category_btn);

        Intent intent=getIntent();
        moim=intent.getStringExtra("moim");
        request=intent.getStringExtra("request");
        total=intent.getFloatExtra("total", 0);
        name=intent.getStringArrayListExtra("name");
        id=intent.getStringArrayListExtra("id");
        date=intent.getStringExtra("date");
        pk=intent.getStringExtra("pk");
    }

    public void setting_radio_btn(){
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.r1){
                    category="영화/문화";
                }
                else if(checkedId==R.id.r2){
                    category="레저/스포츠";
                }
                else if(checkedId==R.id.r3){
                    category="식당";
                }
                else if(checkedId==R.id.r4){
                    category="카페";
                }
                else if(checkedId==R.id.r5){
                    category="여가";
                }
                else if(checkedId==R.id.r6){
                    category="술/유흥";
                }
                else if(checkedId==R.id.r7){
                    category="숙박";
                }
                else if(checkedId==R.id.r8){
                    category="경조사";
                }
                else if(checkedId==R.id.r9){
                    category="기타";
                }
            }
        });
    }

    public void setting_btn(){
        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(category==null){
                    Toast.makeText(Select_CategoryActivity.this, "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("history/"+pk);
                    HashMap<String, Object> map=new HashMap<>();
                    map.put("category", category);
                    databaseReference.updateChildren(map);

                    finish();
                }
            }
        });
    }

}