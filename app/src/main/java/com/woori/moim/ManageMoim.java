package com.woori.moim;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageMoim {
    private static ManageMoim manageMoim;
    Moim moim;
    private ArrayList<Moim> moimList;

    public static ManageMoim getInstance() {
        if (manageMoim == null) {
            manageMoim = new ManageMoim();
        }
        return manageMoim;
    }

    ManageMoim() {
        moim = new Moim();
        moimList = new ArrayList<>();
    }

    public ArrayList<Moim> getMoimList() {
        return moimList;
    }

    public void startParsing() {

        DatabaseReference databaseReference;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("moim");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String firemessage = snapshot.getValue().toString();    //문자열로 받기
                    //sport.add(firemessage);  //리스트에 추가
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });


        //여기서 파이어베이스로부터 받아오는 데이터를 ManageMoim의 moimList에 저장해야해
        //ManageMoim.getInstance().startParsing() 이런식으로 splash에서 호출해서 하면 될거야
    }
}
