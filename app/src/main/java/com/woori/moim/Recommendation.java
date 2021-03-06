package com.woori.moim;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Recommendation extends AppCompatActivity {

    Button rg_btn1, rg_btn2, rg_btn3, rg_btn4, rg_btn5;
    ImageView imageView,imageView2,imageView1;
    ImageView img1, img2, img3, img4, img5;

    Context context=this;
    TextView recommend, max_cat;
    String cat="μν/λ¬Έν";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);

        rg_btn1 = findViewById(R.id.rg_btn1);
        rg_btn2 = findViewById(R.id.rg_btn2);
        rg_btn3 = findViewById(R.id.rg_btn3);
        rg_btn4 = findViewById(R.id.rg_btn4);
        rg_btn5 = findViewById(R.id.rg_btn5);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        img4=findViewById(R.id.img4);
        img5=findViewById(R.id.img5);
        max_cat=findViewById(R.id.max_cat);
        recommend=findViewById(R.id.recommend_product);

        Glide.with(this).load(R.drawable.restaurant_yes).into(img1);
        Glide.with(this).load(R.drawable.cafe_no).into(img2);
        Glide.with(this).load(R.drawable.movie_no).into(img3);
        Glide.with(this).load(R.drawable.culture_no).into(img4);
        Glide.with(this).load(R.drawable.leisure_no).into(img5);

        Glide.with(getApplicationContext()).load(R.drawable.restaurant).into(imageView);
        Glide.with(getApplicationContext()).load(R.drawable.recom_notification).into(imageView1);
        //Glide.with(getApplicationContext()).load(R.drawable.).into(imageView2);

        Intent intent=getIntent();
        cat=intent.getStringExtra("category");
        //cat="μν/λ¬Έν";

        rg_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load(R.drawable.restaurant).into(imageView);
                Glide.with(context).load(R.drawable.restaurant_yes).into(img1);
                Glide.with(context).load(R.drawable.cafe_no).into(img2);
                Glide.with(context).load(R.drawable.movie_no).into(img3);
                Glide.with(context).load(R.drawable.culture_no).into(img4);
                Glide.with(context).load(R.drawable.leisure_no).into(img5);
            }
        });


        rg_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load(R.drawable.cafe).into(imageView);
                Glide.with(context).load(R.drawable.restaurant_no).into(img1);
                Glide.with(context).load(R.drawable.cafe_yes).into(img2);
                Glide.with(context).load(R.drawable.movie_no).into(img3);
                Glide.with(context).load(R.drawable.culture_no).into(img4);
                Glide.with(context).load(R.drawable.leisure_no).into(img5);
            }
        });


        rg_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load(R.drawable.movie).into(imageView);
                Glide.with(context).load(R.drawable.restaurant_no).into(img1);
                Glide.with(context).load(R.drawable.cafe_no).into(img2);
                Glide.with(context).load(R.drawable.movie_yes).into(img3);
                Glide.with(context).load(R.drawable.culture_no).into(img4);
                Glide.with(context).load(R.drawable.leisure_no).into(img5);
            }
        });


        rg_btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load(R.drawable.culture).into(imageView);
                Glide.with(context).load(R.drawable.restaurant_no).into(img1);
                Glide.with(context).load(R.drawable.cafe_no).into(img2);
                Glide.with(context).load(R.drawable.movie_no).into(img3);
                Glide.with(context).load(R.drawable.culture_yes).into(img4);
                Glide.with(context).load(R.drawable.leisure_no).into(img5);
            }
        });


        rg_btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext()).load(R.drawable.leisure).into(imageView);
                Glide.with(context).load(R.drawable.restaurant_no).into(img1);
                Glide.with(context).load(R.drawable.cafe_no).into(img2);
                Glide.with(context).load(R.drawable.movie_no).into(img3);
                Glide.with(context).load(R.drawable.culture_no).into(img4);
                Glide.with(context).load(R.drawable.leisure_yes).into(img5);
            }
        });


//
//        Glide.with(getApplicationContext()).load(R.drawable.restaurant_yes).into(imageView1);
//        Glide.with(getApplicationContext()).load(R.drawable.cafe_no).into(imageView2);
//        Glide.with(getApplicationContext()).load(R.drawable.movie_no).into(imageView3);
//        Glide.with(getApplicationContext()).load(R.drawable.culture_no).into(imageView4);
//        Glide.with(getApplicationContext()).load(R.drawable.leisure_no).into(imageView5);
        setting_recommend_product();

    }

    public void setting_recommend_product(){
        if(cat.equals("μν/λ¬Έν")){
            max_cat.setText("'μν/λ¬Έν' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("uni μ°λ¦¬ v μ²΄ν¬μΉ΄λλ₯Ό μ΄μ©νλ©΄ μν νμ₯ ν°μΌ κ΅¬μμ\nμ΅λ 6μ²μ ν μΈμ μ κ³΅ν΄μ.");
        }
        else if(cat.equals("λ μ /μ€ν¬μΈ ")){
            max_cat.setText("'λ μ /μ€ν¬μΈ ' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("μΆμ² μνμ΄ μμ΄μ..");
        }
        else if(cat.equals("κ²½μ‘°μ¬")){
            max_cat.setText("'κ²½μ‘°μ¬' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("μΆμ² μνμ΄ μμ΄μ..");
        }
        else if(cat.equals("μλΉ")||cat.equals("μΉ΄ν")){
            max_cat.setText("'μλΉ/μΉ΄ν' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("newμ°λ¦¬Vμ²΄ν¬μΉ΄λλ₯Ό μ΄μ©νλ©΄\nν¨λ°λ¦¬λ μ€ν λ 25% μ²­κ΅¬ν μΈ, μ»€νΌ 20% ν μΈμ λ°μ μ μμ΄μ.");
        }
        else if(cat.equals("μ¬κ°")||cat.equals("μλ°")){
            max_cat.setText("'μ¬κ°/μλ°' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("νΉμ λͺ¨μμκ³Ό ν΄μΈ μ¬νμ κ³νμ€μ΄μ κ°μ?\nμ°λ¦¬μν μΈν°λ· νμ μ μ΄μ©νλ©΄\nμ΅μ  50%, μ΅κ³  75%μ μ°λννμ λ°μ μ μμ΄μ.");
        }
        else if(cat.equals("μ /μ ν₯")){
            max_cat.setText("'μ /μ ν₯' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("λ³μ μ§λ£ μμ½ λν, λ§μΆ€ν μμμ»¨μ€νμ μ κ³΅νλ\nμ°λ¦¬μν TWO CHAIRS μ»¨μμ΄μ§ μλΉμ€λ‘ κ±΄κ°κ΄λ¦¬λ μμ§λ§μΈμ!");
        }
        else if(cat.equals("κΈ°ν")){
            max_cat.setText("'κΈ°ν' μΉ΄νκ³ λ¦¬κ° μ μΌ λ§μΌμλ€μ?");
            recommend.setText("κ·Έλ λ€λ©΄ λμ΄λλ³λ‘ λ€μν μνμ μΆμ²λλ¦΄κ²μ\n" +
                    "20λλΌλ©΄ λ²ν·λ¦¬μ€νΈ λͺ©λ λ§λ ¨μ μν μ°λ¦¬μν 'μ€λ¬΄μ΄-μ°λ¦¬ μ κΈ° μ κΈ'\n" +
                    "μλ΄κΈ° μ§μ₯μΈμ΄λΌλ©΄ 'μ²« κΈμ¬ μ°λ¦¬ν΅μ₯'\n" +
                    "μμ΄κ° μλ€λ©΄ 'μ°λ¦¬ μμ΄ νλ³΅ μ κΈ'\n" +
                    "λΈν μκΈμ μνμ λ€λ©΄ 'μλμ΄ νλ¬μ€ μ°λ¦¬μκΈ(νμ ν)'μ μΆμ²λλ €μ!!");
        }
    }

}