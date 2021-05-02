package com.woori.moim;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManagePeopleItem {
    private static ManagePeopleItem managePeopleItem;
    PeopleItem item;
    ArrayList<PeopleItem> list;

    public static ManagePeopleItem getInstance() {
        if (managePeopleItem == null) {
            managePeopleItem = new ManagePeopleItem();
        }
        return managePeopleItem;
    }

    ManagePeopleItem() {
        item = new PeopleItem();
        list = new ArrayList<>();
    }

    public ArrayList<PeopleItem> getList() {
        return list;
    }

    public void startParsing() {

        DatabaseReference databaseReference;

        databaseReference = FirebaseDatabase.getInstance().getReference("user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PeopleItem peopleItem = ds.getValue(PeopleItem.class);
                    managePeopleItem.list.add(peopleItem);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });


    }
}
