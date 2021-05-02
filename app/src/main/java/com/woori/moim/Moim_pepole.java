package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.woori.moim.Network.Api;
import com.woori.moim.Network.ApiClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Callback;

public class Moim_pepole extends AppCompatActivity {

    RecyclerView recyclerView;
    PeopleAdapter adapter;

    PeopleListAdapter peopleListAdapter;
    ListView listView;

    EditText editText_id;
    ImageView button_search;

    String moim_name;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;

    Map<String, Object> moimValue = null;
    MoimInfo moimInfo = null;

    ArrayList<PeopleItem> list = new ArrayList<PeopleItem>();

    String name;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moim_pepole);


        Intent intent = getIntent();
        moim_name = intent.getStringExtra("moim_name");
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");


        editText_id = findViewById(R.id.editText_id);
        button_search = findViewById(R.id.button_search);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new PeopleAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);


        listView = (ListView) findViewById(R.id.listView);
        peopleListAdapter = new PeopleListAdapter();
        listView.setAdapter(peopleListAdapter);

        SharedPreferences sf = getSharedPreferences("mine", MODE_PRIVATE);
        adapter.addItem(new PeopleItem(name, id, sf.getString("token", "")));
        adapter.notifyDataSetChanged();

        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strID = editText_id.getText().toString();
                peopleListAdapter.items.clear();
                peopleListAdapter.notifyDataSetChanged();

                getFirebase(strID);

            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PeopleItem item = (PeopleItem) peopleListAdapter.getItem(position);
                adapter.addItem(new PeopleItem(item.getName(), item.getId(), item.getToken()));
                adapter.notifyDataSetChanged();

                peopleListAdapter.items.clear();
                peopleListAdapter.notifyDataSetChanged();
                list.clear();
            }
        });

        ImageView button_next = findViewById(R.id.button_next);
        Glide.with(getApplicationContext()).load(R.drawable.next_btn).into(button_next);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 1; i < adapter.items.size(); i++) {
                    sendNotificationToUser2(adapter.getItem(0).getId(), adapter.getItem(0).getName(), adapter.getItem(i).getToken());
                }
                Intent intent = new Intent(getApplicationContext(), MoimFinish.class);
                intent.putExtra("moim_name", moim_name);
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

        ImageView imageView4 = findViewById(R.id.imageView4);
        Glide.with(getApplicationContext()).load(R.drawable.moim_name_input).into(imageView4);
        Glide.with(getApplicationContext()).load(R.drawable.search_icon).into(button_search);
    }

    public void getFirebase(String strID) {

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("user/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PeopleItem peopleItem = ds.getValue(PeopleItem.class);
                    list.add(peopleItem);

                }
                listViewAdd(strID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
            }
        });

    }

    public void listViewAdd(String strID) {
        peopleListAdapter.items.clear();
        peopleListAdapter.notifyDataSetChanged();

        for (int i = 0; i < list.size(); i++) {


            if (strID.matches(list.get(i).getId())) {
                peopleListAdapter.addItem(new PeopleItem(list.get(i).getName(), list.get(i).getId(), list.get(i).getToken()));
                peopleListAdapter.notifyDataSetChanged();
            }


        }

        list.clear();
    }


    private void sendNotificationToUser2(String id, String name, String token) {

        Model model = new Model(token, new NotificationModel("모임 개설", name+"님이 "+"'"+moim_name+"' 모임에 초대했어요.\n 수락버튼을 눌러주세요!", "MoimRequest", name, id, moim_name));
      //  Model model = new Model(token, new NotificationModel("모임개설", "모임 확인 부탁드립니다!!", "MoimRequest", name, id, moim_name));
        Api apiService = ApiClient.getClient().create(Api.class);
        retrofit2.Call<ResponseBody> responseBodyCall = apiService.sendNotification(model);

        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.d("testtest", "성공");
                sendFirebase();
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                Log.d("testtest", "실패");
            }
        });
    }


    public void sendFirebase() {

        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
        //String name = s;
        String token = sharedPreferences.getString("token", "");


        mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();
        moimInfo = new MoimInfo(moim_name);
        moimValue = moimInfo.toMap();
        childUpdates.put("/moim/" + moim_name, moimValue);
        mDBReference.updateChildren(childUpdates);


        for (int i = 0; i < adapter.items.size(); i++) {
            mDBReference = FirebaseDatabase.getInstance().getReference();
            childUpdates = new HashMap<>();
            userInfo = new UserInfo(adapter.items.get(i).getId(), adapter.items.get(i).getName(), adapter.items.get(i).getToken());
            userValue = userInfo.toMap();
            childUpdates.put("/moim/" + moim_name + "/user/" + adapter.items.get(i).getId(), userValue);
            mDBReference.updateChildren(childUpdates);
        }


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