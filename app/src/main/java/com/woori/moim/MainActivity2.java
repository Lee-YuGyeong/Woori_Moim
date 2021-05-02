package com.woori.moim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity2 extends AppCompatActivity {
    Intent intent;
    TextView test;
    ImageView barcode_img;
    TextView barcode_text;
    String test_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        test=findViewById(R.id.textView);
        barcode_img=findViewById(R.id.barcode_img);
        barcode_text=findViewById(R.id.barcode_text);
        intent=getIntent();
        test_txt=intent.getStringExtra("test");
        if(test_txt.equals("MOIM")){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setMessage("모임 개설");
            AlertDialog msg=builder.create();
            msg.show();
        }
        else{
            test.setText(intent.getStringExtra("test"));
        }

        create_barcode();
    }

    public void create_barcode(){
        Bitmap bitmap;
        BarcodeEncoder barcodeEncoder=new BarcodeEncoder();
        try{
            bitmap=barcodeEncoder.encodeBitmap("1234 5789 1234 5612", BarcodeFormat.CODE_128, 800, 200);
            barcode_img.setImageBitmap(bitmap);
            barcode_text.setText("1234 5789 1234 5612");
        }
        catch(Exception e){

        }

    }
}