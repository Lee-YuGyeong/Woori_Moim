package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CardSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    CardAdapter adapter;

    ArrayList<String> name, id, token;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> cardValue = null;
    CardInfo cardInfo = null;
    String moim_name, user_id;

    ArrayList<CardSelectItem> list = new ArrayList<CardSelectItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_select);


        SharedPreferences sharedPreferences = getSharedPreferences("mine", MODE_PRIVATE);
        user_id = sharedPreferences.getString("id", "");


        Intent intent = getIntent();
        name = intent.getStringArrayListExtra("name");
        id = intent.getStringArrayListExtra("id");
        token = intent.getStringArrayListExtra("token");
        moim_name = intent.getStringExtra("moim_name");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new CardAdapter(getApplicationContext());

        recyclerView.setAdapter(adapter);
        getFirebase();

        adapter.setOnItemClickListener(new CardAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(CardAdapter.ViewHolder holder, View view, int position) {
                CardSelectItem item = adapter.getItem(position);

                String numstr = item.getNum();

                try {
                    String key = "key";
                    String de = Decrypt(numstr, key);
                    Intent intent = new Intent(getApplicationContext(), CardPassword.class);
                    intent.putExtra("pw", adapter.items.get(position).getPw());
                    intent.putExtra("moim_name", moim_name);
                    intent.putStringArrayListExtra("name", name);
                    intent.putStringArrayListExtra("id", id);
                    intent.putStringArrayListExtra("token", token);
                    intent.putExtra("account", de);
                    startActivity(intent);
                    finish();

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


            }
        });

        RecyclerRightDecoration spaceDecoration = new RecyclerRightDecoration(30);
        recyclerView.addItemDecoration(spaceDecoration);

    }

    public void getFirebase() {

        DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference("moim/" + moim_name + "/user/" + user_id + "/card");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CardSelectItem cardSelectItem = ds.getValue(CardSelectItem.class);
                    list.add(cardSelectItem);
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

        for (int i = 0; i < list.size(); i++) {
            adapter.addItem(new CardSelectItem(list.get(i).getNum(), list.get(i).getPw()));
            adapter.notifyDataSetChanged();

        }
        list.clear();

    }

    public static String Decrypt(String text, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(Base64.decode(text, 0));
        return new String(results, "UTF-8");

    }


}