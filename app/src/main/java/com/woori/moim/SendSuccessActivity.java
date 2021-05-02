package com.woori.moim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;


public class SendSuccessActivity extends AppCompatActivity {

    ImageView button;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_success);

        imageView = findViewById(R.id.success_iv);
        Glide.with(this).load(R.drawable.icon_check).into(imageView);


        button = findViewById(R.id.finish_button_next);
        Glide.with(this).load(R.drawable.button_finish).into(button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}