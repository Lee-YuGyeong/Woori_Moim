package com.woori.moim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MoimRequest extends AppCompatActivity {

    String moim_name, user_name, id;
    TextView textView_title;
    TextView textView_moim_name;
    TextView textView_user_name;
    TextView textView_user_id;
    Button button_yes;
    Button button_no;
    PeopleListAdapter peopleListAdapter;
    ListView listView;
    ArrayList<PeopleItem> list = new ArrayList<PeopleItem>();


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> userValue = null;
    UserInfo userInfo = null;

    Map<String, Object> moimValue = null;
    MoimInfo moimInfo = null;


    String my_name,my_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moim_request);

        Intent intent = getIntent();
        moim_name = intent.getStringExtra("moim_name");
        user_name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");

        textView_title = findViewById(R.id.textView_title);
        textView_moim_name = findViewById(R.id.textView_moim_name);
        textView_user_name = findViewById(R.id.textView_user_name);
        textView_user_id = findViewById(R.id.textView_user_id);
        button_yes = findViewById(R.id.button_yes);
        button_no = findViewById(R.id.button_no);

        textView_title.setText(user_name + "님이\n" + moim_name + " 가입을 요청하였어요.");
        textView_moim_name.setText(moim_name);
        textView_user_name.setText(user_name);
        textView_user_id.setText(id);

        listView = (ListView) findViewById(R.id.listView);
        peopleListAdapter = new PeopleListAdapter();
        listView.setAdapter(peopleListAdapter);

        getFirebase();


   //     SharedPreferences sharedPreferences = getSharedPreferences("mine",MODE_PRIVATE);
   //     my_name = sharedPreferences.getString("name","");
    //    my_id = sharedPreferences.getString("id","");
       // Log.d("아아",my_id);

        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getToken();
                Intent intent1 = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent1);
            }
        });
        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                DatabaseReference databaseReference;
//                databaseReference = FirebaseDatabase.getInstance().getReference("moim/"+moim_name+"/user/"+my_id);
//                databaseReference.removeValue();
            }
        });

    }

    public void sendFirebase(String token) {
//
//        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
//        String token = sharedPreferences.getString("token", "");

//        mDBReference = FirebaseDatabase.getInstance().getReference();
//        childUpdates = new HashMap<>();
//        userInfo = new UserInfo(id, user_name, token);
//        userValue = userInfo.toMap();
//        childUpdates.put("/moim/" + moim_name + "/user/" + id, userValue);
//        mDBReference.updateChildren(childUpdates);


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
        peopleListAdapter.items.clear();
        peopleListAdapter.notifyDataSetChanged();
        for (int i = 0; i < list.size(); i++) {

            if (list.get(i).getName().equals(user_name)) {

            }else{
                peopleListAdapter.addItem(new PeopleItem(list.get(i).getName(), list.get(i).getId(), list.get(i).getToken()));
            }
        }

        peopleListAdapter.notifyDataSetChanged();
    }

    public void getToken() {
        //토큰값을 받아옵니다.
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }


                        sendFirebase(task.getResult().getToken());


                    }
                });
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