package com.woori.moim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SendActivity extends AppCompatActivity {
    ImageView button;
    EditText et1;
    EditText et3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        et1 = findViewById(R.id.send_et1);
        et3 = findViewById(R.id.send_et3);

        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        String request = intent.getStringExtra("request");

        et3.setText(money);
        et1.setText(request);

        button = findViewById(R.id.send_btn_next);
        Glide.with(this).load(R.drawable.button_send).into(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}