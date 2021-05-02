package com.woori.moim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CardRegistration extends AppCompatActivity {

    EditText text_cardNum1, text_cardNum2, text_cardNum3,text_cardNum4, text_date1, text_date2, text_cvc, text_pw;
    ImageView button_chk, btn_finish;

    String cardNum, date, cvc, pw;
    boolean chk=false;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    DatabaseReference mDBReference = null;
    HashMap<String, Object> childUpdates = null;
    Map<String, Object> cardValue = null;
    CardInfo cardInfo = null;

    ImageView button_chk_img;


    String moim_name,name,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_registration);

        Intent intent = getIntent();
        moim_name = intent.getStringExtra("moim_name");
        name = intent.getStringExtra("name");
        id = intent.getStringExtra("id");


        text_cardNum1 = findViewById(R.id.text_cardNum1);
        text_cardNum2 = findViewById(R.id.text_cardNum2);
        text_cardNum3 = findViewById(R.id.text_cardNum3);
        text_cardNum4 = findViewById(R.id.text_cardNum4);
        text_date1 = findViewById(R.id.text_date1);
        text_date2 = findViewById(R.id.text_date2);
        text_cvc = findViewById(R.id.text_cvc);
        text_pw = findViewById(R.id.text_pw);
        button_chk = findViewById(R.id.button_chk);
        button_chk_img = findViewById(R.id.imageView10);
        btn_finish = findViewById(R.id.btn_finish);
        ImageView textView7 = findViewById(R.id.textView7);


        TextView textView12 = findViewById(R.id.textView12);
        TextView textView13 = findViewById(R.id.textView13);
        TextView textView14 = findViewById(R.id.textView14);
        textView12.setPaintFlags(textView12.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView13.setPaintFlags(textView13.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView14.setPaintFlags(textView14.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        Glide.with(getApplicationContext()).load(R.drawable.gray_box).into(textView7);

        Glide.with(getApplicationContext()).load(R.drawable.member_check).into(button_chk);
        Glide.with(getApplicationContext()).load(R.drawable.btn_finish).into(btn_finish);
        Glide.with(getApplicationContext()).load(R.drawable.member_uncheck).into(button_chk_img);
        button_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chk){
                    Glide.with(getApplicationContext()).load(R.drawable.member_uncheck).into(button_chk_img);
                    chk=false;
                }else{
                    Glide.with(getApplicationContext()).load(R.drawable.member_check).into(button_chk_img);
                    chk=true;
                }
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chk){
                    cardNum = text_cardNum1.getText().toString() + text_cardNum2.getText().toString() + text_cardNum3.getText().toString()+ text_cardNum4.getText().toString() ;
                    date = text_date1.getText().toString() + text_date2.getText().toString();
                    cvc = text_cvc.getText().toString();
                    pw = text_pw.getText().toString();

                    try {
                        String key = "key";
                        String en;
                        //en = Encrypt(cardNum, key);
                        //String de = Decrypt(en, key);

                        // textView_en.setText(en);
                        // textView_de.setText(de);
                        sendFirebase(Encrypt(cardNum, key), Encrypt(date, key), Encrypt(cvc, key), Encrypt(pw, key));

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "약관 동의 버튼을 체크 해주세요.", Toast.LENGTH_LONG).show();
                }

                finish();

            }
        });

        text_cardNum1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_cardNum1.length() == 4) {
                    text_cardNum2.requestFocus();
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

        text_cardNum2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_cardNum2.length() == 4) {
                    text_cardNum3.requestFocus();
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

        text_cardNum3.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_cardNum3.length() == 4) {
                    text_cardNum4.requestFocus();
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

        text_cardNum4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_cardNum4.length() == 4) {
                    text_date1.requestFocus();
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

        text_date1.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_date1.length() == 2) {
                    text_date2.requestFocus();
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

        text_date2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_date2.length() == 2) {
                    text_cvc.requestFocus();
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

        text_cvc.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (text_cvc.length() == 3) {
                    text_pw.requestFocus();
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

    public void sendFirebase(String num, String date, String cvc, String pw) {


        mDBReference = FirebaseDatabase.getInstance().getReference();
        childUpdates = new HashMap<>();
        cardInfo = new CardInfo(num, date, cvc, pw);
        cardValue = cardInfo.toMap();
        childUpdates.put("/moim/" + moim_name + "/user/" + id + "/card/" + text_cardNum1.getText().toString(), cardValue);
        mDBReference.updateChildren(childUpdates);

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


    public static String Encrypt(String text, String key) throws Exception {

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes("UTF-8");
        int len = b.length;
        if (len > keyBytes.length) len = keyBytes.length;
        System.arraycopy(b, 0, keyBytes, 0, len);
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] results = cipher.doFinal(text.getBytes("UTF-8"));

        return Base64.encodeToString(results, 0);

    }//암호화


}