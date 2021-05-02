package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MoimFinish extends AppCompatActivity {

    TextView date, text_moim_name;
    String moim_name;
    ListView listView;
    PeopleListAdapter peopleListAdapter;
    ArrayList<PeopleItem> list = new ArrayList<PeopleItem>();

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;

    Map<String, Object> moimValue = null;
    MoimInfo moimInfo = null;

    String name, id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moim_finish);


        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");


        date = findViewById(R.id.text_date);
        text_moim_name = findViewById(R.id.text_moim_name);
        listView = findViewById(R.id.listView);
        peopleListAdapter = new PeopleListAdapter();
        listView.setAdapter(peopleListAdapter);

        long now = System.currentTimeMillis();
        Date mDate = new Date(now);
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy년 M월 d일");
        String getTime = simpleDate.format(mDate);
        date.setText(getTime);

        Intent intent2 = getIntent();
        moim_name = intent2.getStringExtra("moim_name");
        name = intent2.getStringExtra("name");
        id = intent2.getStringExtra("id");
        text_moim_name.setText(moim_name);


        ImageView button = findViewById(R.id.button);
        Glide.with(getApplicationContext()).load(R.drawable.next_btn).into(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(getApplicationContext()).load(R.drawable.member_check).into(imageView);

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim/" + moim_name + "/user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PeopleItem peopleItem = ds.getValue(PeopleItem.class);
                    list.add(peopleItem);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });


        for (int i = 0; i < list.size(); i++) {
            peopleListAdapter.addItem(new PeopleItem(list.get(i).getName(), list.get(i).getId(), list.get(i).getToken()));
        }

        peopleListAdapter.notifyDataSetChanged();



    }


    class PeopleListAdapter extends BaseAdapter {
        ArrayList<PeopleItem> items = new ArrayList<PeopleItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        public void addItem(PeopleItem item) {
            items.add(item);
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MoimPeopleSearchItemView view = null;
            if (convertView == null) {
                view = new MoimPeopleSearchItemView(getApplicationContext());
            } else {
                view = (MoimPeopleSearchItemView) convertView;
            }

            PeopleItem item = items.get(position);
            view.setName(item.getName());
            view.setId(item.getId());

            return view;
        }
    }
}