package com.woori.moim;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagePayHistory {
    private static ManagePayHistory managePayHistory;
    Pay_History item;
    ArrayList<Pay_History> list;

    public static ManagePayHistory getInstance(){
        if(managePayHistory==null){
            managePayHistory=new ManagePayHistory();
        }
        return managePayHistory;
    }
    public ManagePayHistory(){
        item=new Pay_History();
        list=new ArrayList<>();
    }

    public ArrayList<Pay_History> getList() {
        return list;
    }

    public void setList(ArrayList<Pay_History> list) {
        if(this.list!=null){
            this.list=null;
        }
        this.list=new ArrayList<>();
        this.list = list;
    }

    public void startParsing(Context context, String moim_name){
        if(this.list!=null){
            this.list=null;
        }
        this.list=new ArrayList<>();
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference("history");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    Pay_History item=ds.getValue(Pay_History.class);
                    if(item.getMoim_name().equals(moim_name)){
                        list.add(item);
                    }
                }
                Intent intent=new Intent(context, PaylistActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void startParsing_for_analysis(Context context, String moim_name){
        if(this.list!=null){
            this.list=null;
        }
        this.list=new ArrayList<>();
        DatabaseReference databaseReference;
        databaseReference= FirebaseDatabase.getInstance().getReference("history");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    Pay_History item=ds.getValue(Pay_History.class);
                    if(item.getMoim_name().equals(moim_name)){
                        list.add(item);
                    }
                }
                Intent intent=new Intent(context, AnalysisActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void get_top_category(Context context, String moim_name){
        if(this.list!=null){
            this.list=null;
        }
        this.list=new ArrayList<>();
        ArrayList<Integer> cnt=new ArrayList<>();
        for(int i=0; i<9; i++){
            cnt.add(0);
        }
        ArrayList<String> cat=new ArrayList<>();
        cat.add("영화/문화");cat.add("레저/스포츠");cat.add("식당");cat.add("카페");cat.add("여가");
        cat.add("술/유흥");cat.add("숙박");cat.add("경조사");cat.add("기타");
        DatabaseReference databaseReference;
        databaseReference=FirebaseDatabase.getInstance().getReference("history");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    Pay_History item=ds.getValue(Pay_History.class);
                    if(item.getMoim_name().equals(moim_name)){
                        list.add(item);
                        if(item.getCategory().equals("영화/문화")){
                            cnt.set(0, cnt.get(0)+1);
                        }
                        else if(item.getCategory().equals("레저/스포츠")){
                            cnt.set(1, cnt.get(1)+1);
                        }
                        else if(item.getCategory().equals("식당")){
                            cnt.set(2, cnt.get(2)+1);
                        }
                        else if(item.getCategory().equals("카페")){
                            cnt.set(3, cnt.get(3)+1);
                        }
                        else if(item.getCategory().equals("여가")){
                            cnt.set(4, cnt.get(4)+1);
                        }
                        else if(item.getCategory().equals("술/유흥")){
                            cnt.set(5, cnt.get(5)+1);
                        }
                        else if(item.getCategory().equals("숙박")){
                            cnt.set(6, cnt.get(6)+1);
                        }
                        else if(item.getCategory().equals("경조사")){
                            cnt.set(7, cnt.get(7)+1);
                        }
                        else if(item.getCategory().equals("기타")){
                            cnt.set(8, cnt.get(8)+1);
                        }
                    }
                }

                String category="영화/문화";
                int max=cnt.get(0);
                for(int i=1; i<9; i++){
                    if(cnt.get(i)>max){
                        category=cat.get(i);
                    }
                }

                Intent intent=new Intent(context, Recommendation.class);
                intent.putExtra("category", category);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
