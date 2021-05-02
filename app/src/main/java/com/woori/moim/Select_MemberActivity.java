package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Select_MemberActivity extends AppCompatActivity {
    RecyclerView member_choice;
    ImageView next;
    ArrayList<PeopleItem> members;
    Select_member_Adapter adapter;
    ArrayList<Integer> select;
    ArrayList<String> name;
    ArrayList<String> id;
    ArrayList<String> token;
    String moim_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__member);

        setting_member_item();
        setting_view();
        setting_recyclerview();
    }

    public void setting_member_item(){
        Intent intent=getIntent();
        ArrayList<String> temp_name=intent.getStringArrayListExtra("name");
        ArrayList<String> temp_id=intent.getStringArrayListExtra("id");
        ArrayList<String> temp_token=intent.getStringArrayListExtra("token");
        moim_name=intent.getStringExtra("moim_name");
        members=new ArrayList<>();
        select=new ArrayList<>();
        for(int i=0; i<temp_name.size(); i++){
            members.add(new PeopleItem(temp_name.get(i), temp_id.get(i), temp_token.get(i)));
            select.add(0);
        }

        name=new ArrayList<>();
        id=new ArrayList<>();
        token=new ArrayList<>();
    }
    public void setting_view(){
        member_choice=findViewById(R.id.select_member_recyclerview);
        next=findViewById(R.id.button_next);
        Glide.with(this).load(R.drawable.next).into(next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0; i<select.size(); i++){
                    if(select.get(i)==1){
                        name.add(members.get(i).getName());
                        id.add(members.get(i).getId());
                        token.add(members.get(i).getToken());
                    }
                }
                if(name.size()==0){
                    Toast.makeText(Select_MemberActivity.this, "모임원을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(Select_MemberActivity.this, CardSelect.class);
                intent.putExtra("moim_name", moim_name);
                intent.putStringArrayListExtra("name", name);
                intent.putStringArrayListExtra("id", id);
                intent.putStringArrayListExtra("token", token);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setting_recyclerview(){
        adapter=new Select_member_Adapter(members, this);
        member_choice.setLayoutManager(new LinearLayoutManager(this));
        member_choice.setAdapter(adapter);

        adapter.setOnMemberItemClickListener(new Select_member_Adapter.OnMemberItemClickListener() {
            @Override
            public void selectItemClick(Select_member_Adapter.SelectViewHolder holder, View view, int position) {
                if(select.get(position)==0){
                    select.set(position, 1);
                    Glide.with(Select_MemberActivity.this).load(R.drawable.member_check).into((ImageView) view);
                }
                else if(select.get(position)==1){
                    select.set(position, 0);
                    Glide.with(Select_MemberActivity.this).load(R.drawable.member_uncheck).into((ImageView) view);
                }
            }
        });
    }

}