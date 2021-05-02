package com.woori.moim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Select_member_Adapter extends RecyclerView.Adapter{

    ArrayList<PeopleItem> items;
    Context context;
    OnMemberItemClickListener listener;
    public Select_member_Adapter(ArrayList<PeopleItem> items, Context context){
        this.items=items;
        this.context=context;
    }

    public interface OnMemberItemClickListener{
        void selectItemClick(SelectViewHolder holder, View view, int position);
    }
    public void setOnMemberItemClickListener(OnMemberItemClickListener listener){
        this.listener=listener;
    }
    static final class SelectViewHolder extends RecyclerView.ViewHolder{
        LinearLayout member_layout;
        TextView member_name;
        TextView member_id;
        ImageView check;

        OnMemberItemClickListener listener;
        public SelectViewHolder(@NonNull View itemView) {
            super(itemView);
            member_layout=itemView.findViewById(R.id.member_layout);
            member_name=itemView.findViewById(R.id.member_name);
            member_id=itemView.findViewById(R.id.member_id);
            check=itemView.findViewById(R.id.check);

            member_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.selectItemClick(SelectViewHolder.this, check, position);
                    }
                }
            });
        }
        public void setItem(PeopleItem item){
            member_name.setText(item.getName());
            member_id.setText(item.getId());
        }
        public void setOnMemberItemClickListener(OnMemberItemClickListener listener){
            this.listener=listener;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.select_member_item, parent, false);
        return new SelectViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SelectViewHolder vh=(SelectViewHolder)holder;
        vh.setItem(items.get(position));
        vh.setOnMemberItemClickListener(listener);
        Glide.with(context).load(R.drawable.member_uncheck).into(vh.check);
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }
}
