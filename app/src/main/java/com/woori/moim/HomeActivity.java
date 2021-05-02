package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    TextView textView2, textView1, textView_moim_name;

    ImageView button_add, button_pay;
    String name, id;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;

    Map<String, Object> moimValue = null;
    MoimInfo moimInfo = null;

    ArrayList<MoimItem> list = new ArrayList<MoimItem>();
    MoimAdapter moimAdapter;

    ArrayList<PeopleItem> list2 = new ArrayList<PeopleItem>();

    RecyclerView recyclerView;
    TextView nameList;
    String names = "";
    int l = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("mine",MODE_PRIVATE);
        name = sharedPreferences.getString("name","");
        id = sharedPreferences.getString("id","");


        textView2 = findViewById(R.id.textView2);
        textView1 = findViewById(R.id.textView1);
        textView_moim_name = findViewById(R.id.textView_moim_name);
        button_add = findViewById(R.id.button_add);
        //button_pay = findViewById(R.id.button_pay);
        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.layout);

        nameList = findViewById(R.id.nameList);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        moimAdapter = new MoimAdapter(getApplicationContext());
        recyclerView.setAdapter(moimAdapter);

        RecyclerRightDecoration spaceDecoration = new RecyclerRightDecoration(30);
        recyclerView.addItemDecoration(spaceDecoration);

        Glide.with(getApplicationContext()).load(R.drawable.moim_add_btn).into(button_add);

        moimAdapter.setOnItemClickListener(new MoimAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MoimAdapter.ViewHolder holder, View view, int position) {
                MoimItem item = moimAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), HomeDetail.class);
                intent.putExtra("moim_name", item.getMoim_name());
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        Glide.with(this)
                .asBitmap()
                .load(R.drawable.home_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Drawable drawable = new BitmapDrawable(getResources(), resource);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            layout.setBackground(drawable);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        SharedPreferences sf = getSharedPreferences("mine", MODE_PRIVATE);
        String name = sf.getString("name", "");
        textView2.setText(name);
        textView2.setText("안녕하세요," + name + "님\n오늘도 즐거운 모임생활 하세요!");
        textView1.setText(name);

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MoimName.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        l = 0;
        getFirebase();
        list.clear();
    }

    public void getFirebase() {

        list.clear();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(list!=null){
                    list=null;
                }
                list=new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    MoimItem moimItem = ds.getValue(MoimItem.class);
                    list.add(moimItem);
                    listViewAdd();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }

    public void listViewAdd() {
        moimAdapter.items.clear();
        moimAdapter.notifyDataSetChanged();
        for (int i = 0; i < list.size(); i++) {
            getFirebase2(list.get(i).getMoim_name());
        }
        list.clear();
        moimAdapter.notifyDataSetChanged();
    }

    public void getFirebase2(String moim_name) {

        list2.clear();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim/" + moim_name + "/user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PeopleItem peopleItem = ds.getValue(PeopleItem.class);
                    list2.add(peopleItem);

                }
                listViewAdd2();
                if (l == 1) {
                    moimAdapter.addItem(new MoimItem(moim_name, names));
                    moimAdapter.notifyDataSetChanged();
                    l=0;
                }
                names = "";
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

        list2.clear();
    }

    public void listViewAdd2() {

        for (int i = 0; i < list2.size(); i++) {
            names += list2.get(i).getName();
            names += "\n";

            if (name.equals(list2.get(i).getName())) {
                l = 1;
            }

        }
        list2.clear();

    }


}