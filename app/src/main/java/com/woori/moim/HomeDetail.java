package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.woori.moim.api.AccntActivity;
import com.woori.moim.api.ApiActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HomeDetail extends AppCompatActivity {

    TextView textView_moim_name, textView_bank, textView_bank_num, textView_card;
    ListView listView;
    ImageView btn1, btn2, btn3,btn_pay;
    Button button_card_plus, btn_accnt;
    RecyclerView recyclerView;

    String moim_name, name, id;

    ImageView imageView3;
    Button button;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    PeopleListAdapter peopleListAdapter;
    ArrayList<PeopleItem> list = new ArrayList<PeopleItem>();

    CardAdapter adapter;

    ArrayList<CardSelectItem> list2 = new ArrayList<CardSelectItem>();


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
        String accnt = sharedPreferences.getString("accnt", "0000-000-000000");
        textView_bank_num.setText(accnt);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_detail);

        textView_moim_name = findViewById(R.id.textView_moim_name);
        textView_bank = findViewById(R.id.textView_bank);
        textView_bank_num = findViewById(R.id.textView_bank_num);
        textView_card = findViewById(R.id.textView_card);
        listView = findViewById(R.id.nameList);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        button_card_plus = findViewById(R.id.button_card_plus);
        btn_pay = findViewById(R.id.btn_pay);
        btn_accnt = findViewById(R.id.button_account_plus);
        recyclerView = findViewById(R.id.recyclerView);
        imageView3 = findViewById(R.id.imageView3);
        button = findViewById(R.id.plus);

        Intent intent = getIntent();
        moim_name = intent.getStringExtra("moim_name");
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");
        textView_moim_name.setText(moim_name);
        textView_card.setText(name + "님의 카드");

        Glide.with(getApplicationContext()).load(R.drawable.payment_list_icon).into(btn1);
        Glide.with(getApplicationContext()).load(R.drawable.analysis_icon).into(btn2);
        Glide.with(getApplicationContext()).load(R.drawable.good_icon).into(btn3);
        Glide.with(getApplicationContext()).load(R.drawable.pay_btn).into(btn_pay);

        //String accnt = intent.getStringExtra("accnt");
        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
        String accnt = sharedPreferences.getString("accnt", "0000-000-000000");
        textView_bank_num.setText(accnt);

        peopleListAdapter = new PeopleListAdapter();
        listView.setAdapter(peopleListAdapter);
        getFirebase();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new CardAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.items.clear();
        adapter.notifyDataSetChanged();


        getFirebase2();

        RecyclerRightDecoration spaceDecoration = new RecyclerRightDecoration(30);
        recyclerView.addItemDecoration(spaceDecoration);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CardRegistration.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("moim_name", moim_name);
                startActivity(intent);
            }
        });
        button_card_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CardRegistration.class);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                intent.putExtra("moim_name", moim_name);
                startActivity(intent);
            }
        });

        btn_accnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ApiActivity.class);
                startActivity(intent);
             //   finish();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recommendation.class);
                startActivity(intent);
            }
        });

        setting_paybtn();
        setting_listbtn();
        setting_analysisbtn();
        setting_recommendbtn();
    }

    public void getFirebase() {


        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim/" + moim_name + "/user");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PeopleItem peopleItem = ds.getValue(PeopleItem.class);
                    list.add(peopleItem);

                }
                listViewAdd();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }

    public void listViewAdd() {
        peopleListAdapter.items.clear();
        peopleListAdapter.notifyDataSetChanged();
        for (int i = 0; i < list.size(); i++) {
            peopleListAdapter.addItem(new PeopleItem(list.get(i).getName(), list.get(i).getId(), list.get(i).getToken()));
        }
        peopleListAdapter.notifyDataSetChanged();
        list.clear();
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

    public void getFirebase2() {

        adapter.items.clear();
        adapter.notifyDataSetChanged();
        list2.clear();

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim/" + moim_name + "/user/" + id + "/card");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CardSelectItem cardSelectItem = ds.getValue(CardSelectItem.class);
                    list2.add(cardSelectItem);

                }
                listViewAdd2();
                if (adapter.items.size() !=0) {
                    imageView3.setVisibility(View.INVISIBLE);
                    button.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });
    }

    public void listViewAdd2() {

        adapter.items.clear();
        adapter.notifyDataSetChanged();

        for (int i = 0; i < list2.size(); i++) {
            adapter.addItem(new CardSelectItem(list2.get(i).getNum(), list2.get(i).getPw()));
            adapter.notifyDataSetChanged();

        }

        list2.clear();
    }

    public void setting_paybtn(){
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> name=new ArrayList<>();
                ArrayList<String> id=new ArrayList<>();
                ArrayList<String> token=new ArrayList<>();
                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("moim/"+moim_name+"/user");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            id.add(ds.getKey().toString());
                            HashMap<String, String> map= (HashMap<String, String>) ds.getValue();
                            name.add(map.get("name"));
                            token.add(map.get("token"));
                        }
                        Intent intent=new Intent(HomeDetail.this, Select_MemberActivity.class);
                        intent.putExtra("moim_name", moim_name);
                        intent.putStringArrayListExtra("name", name);
                        intent.putStringArrayListExtra("id", id);
                        intent.putStringArrayListExtra("token", token);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void setting_listbtn(){
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagePayHistory.getInstance().startParsing(HomeDetail.this, moim_name);
            }
        });
    }
    public void setting_analysisbtn(){
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagePayHistory.getInstance().startParsing_for_analysis(HomeDetail.this, moim_name);
            }
        });
    }
    public void setting_recommendbtn(){
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagePayHistory.getInstance().get_top_category(HomeDetail.this, moim_name);
            }
        });
    }
}