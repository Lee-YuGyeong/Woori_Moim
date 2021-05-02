package com.woori.moim;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class MoimPeopleSearchItemView extends LinearLayout {

    TextView text_name;
    TextView text_id;


    public MoimPeopleSearchItemView(Context context) {
        super(context);
        init(context);
    }

    public MoimPeopleSearchItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.people_item, this, true);

        text_id = (TextView) findViewById(R.id.id);
        text_name = (TextView) findViewById(R.id.name);
    }

    public void setName(String name){
        text_name.setText(name);
    }

    public void setId(String id){

        text_id.setText(id);
    }


}
