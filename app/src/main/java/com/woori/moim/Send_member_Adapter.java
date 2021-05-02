package com.woori.moim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Send_member_Adapter extends RecyclerView.Adapter {
    ArrayList<PeopleItem> items;
    Context context;
    public Send_member_Adapter(ArrayList<PeopleItem> items, Context context){
        this.items=items;
        this.context=context;
    }
    static final class SendViewHolder extends RecyclerView.ViewHolder{
        TextView member_name;
        TextView member_id;
        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            member_name=itemView.findViewById(R.id.member_name);
            member_id=itemView.findViewById(R.id.member_id);
        }
        public void setItem(PeopleItem item){
            member_name.setText(item.getName());
            member_id.setText(item.getId());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.send_member_item, parent, false);
        return new SendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SendViewHolder vh=(SendViewHolder)holder;
        vh.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}
