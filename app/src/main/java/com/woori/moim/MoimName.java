package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MoimName extends AppCompatActivity {

    ImageView button;
    EditText editText;
    String name, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moim_name);

        button =  findViewById(R.id.button_next);
        editText = (EditText) findViewById(R.id.editText_moin_name);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");

        ImageView imageView5 = findViewById(R.id.imageView5);
        Glide.with(getApplicationContext()).load(R.drawable.moim_name_input).into(imageView5);
        Glide.with(getApplicationContext()).load(R.drawable.next_btn).into(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Moim_pepole.class);
                intent.putExtra("moim_name", editText.getText().toString());
                intent.putExtra("name", name);
                intent.putExtra("id", id);
                startActivity(intent);
                finish();
            }
        });

    }
}