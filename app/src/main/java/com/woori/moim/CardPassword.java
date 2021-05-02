package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CardPassword extends AppCompatActivity {

    EditText edit1, edit2, edit3, edit4;
    String edit = "";
    String pw, moim_name, account;

    ArrayList<String> name, id, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_password);

        edit1 = findViewById(R.id.edit_1);
        edit2 = findViewById(R.id.edit_2);
        edit3 = findViewById(R.id.edit_3);
        edit4 = findViewById(R.id.edit_4);


        edit1.setNextFocusDownId(edit2.getId());
        edit2.setNextFocusDownId(edit3.getId());
        edit3.setNextFocusDownId(edit4.getId());

        ImageView imageView11 = findViewById(R.id.imageView11);
        Glide.with(getApplicationContext()).load(R.drawable.password_input).into(imageView11);


        Intent intent = getIntent();
        name = intent.getStringArrayListExtra("name");
        id = intent.getStringArrayListExtra("id");
        token = intent.getStringArrayListExtra("token");
        moim_name = intent.getStringExtra("moim_name");
        pw = intent.getStringExtra("pw");
        account = intent.getStringExtra("account");



        ImageView button = findViewById(R.id.button);
        Glide.with(getApplicationContext()).load(R.drawable.next_btn).into(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    edit = edit1.getText().toString() + edit2.getText().toString() + edit3.getText().toString() + edit4.getText().toString();
                    String key = "key";
                    String de = Decrypt(pw, key);
                    if (Integer.parseInt(de) == Integer.parseInt(edit)) {
                        Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                        intent.putExtra("moim_name", moim_name);
                        intent.putStringArrayListExtra("name", name);
                        intent.putStringArrayListExtra("id", id);
                        intent.putStringArrayListExtra("token", token);
                        intent.putExtra("account", account);
                        startActivity(intent);
                        finish();
                        // Toast.makeText(getApplicationContext(), "올바른 비밀번호", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 입력해주세요. 비밀번호 - " + Integer.parseInt(de), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });


        edit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (edit1.length() == 1) {
                    edit2.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        edit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (edit2.length() == 1) {
                    edit3.requestFocus();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });

        edit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (edit3.length() == 1) {
                    edit4.requestFocus();
                }

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });


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

    }//복호화

}