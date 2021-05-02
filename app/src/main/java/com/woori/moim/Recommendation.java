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
    String cat="영화/문화";


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
        //cat="영화/문화";

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
        if(cat.equals("영화/문화")){
            max_cat.setText("'영화/문화' 카테고리가 제일 많으시네요?");
            recommend.setText("uni 우리 v 체크카드를 이용하면 영화 현장 티켓 구입시\n최대 6천원 할인을 제공해요.");
        }
        else if(cat.equals("레저/스포츠")){
            max_cat.setText("'레저/스포츠' 카테고리가 제일 많으시네요?");
            recommend.setText("추천 상품이 없어요..");
        }
        else if(cat.equals("경조사")){
            max_cat.setText("'경조사' 카테고리가 제일 많으시네요?");
            recommend.setText("추천 상품이 없어요..");
        }
        else if(cat.equals("식당")||cat.equals("카페")){
            max_cat.setText("'식당/카페' 카테고리가 제일 많으시네요?");
            recommend.setText("new우리V체크카드를 이용하면\n패밀리레스토랑 25% 청구할인, 커피 20% 할인을 받을 수 있어요.");
        }
        else if(cat.equals("여가")||cat.equals("숙박")){
            max_cat.setText("'여가/숙박' 카테고리가 제일 많으시네요?");
            recommend.setText("혹시 모임원과 해외 여행을 계획중이신가요?\n우리은행 인터넷 환전을 이용하면\n최저 50%, 최고 75%의 우대혜택을 받을 수 있어요.");
        }
        else if(cat.equals("술/유흥")){
            max_cat.setText("'술/유흥' 카테고리가 제일 많으시네요?");
            recommend.setText("병원 진료 예약 대행, 맞춤형 영양컨설팅을 제공하는\n우리은행 TWO CHAIRS 컨시어지 서비스로 건강관리도 잊지마세요!");
        }
        else if(cat.equals("기타")){
            max_cat.setText("'기타' 카테고리가 제일 많으시네요?");
            recommend.setText("그렇다면 나이대별로 다양한 상품을 추천드릴게요\n" +
                    "20대라면 버킷리스트 목돈 마련을 위한 우리은행 '스무살-우리 정기 적금'\n" +
                    "새내기 직장인이라면 '첫 급여 우리통장'\n" +
                    "아이가 있다면 '우리 아이 행복 적금'\n" +
                    "노후 자금을 원하신다면 '시니어 플러스 우리예금(회전형)'을 추천드려요!!");
        }
    }

}