package com.woori.moim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class List_Adapter extends RecyclerView.Adapter {
    ArrayList<Pay_History> items;
    Context context;
    OnRequestItemClickListener listener;
    OnChangeItemClickListener listener2;
    public List_Adapter(ArrayList<Pay_History> items, Context context){
        this.items=items;
        this.context=context;
    }

    public interface OnRequestItemClickListener{
        void requestItemClick(ListViewHolder holder, View view, int position);
    }
    public interface OnChangeItemClickListener{
        void changeItemClick(ListViewHolder holder, View view, TextView cat, int position);
    }
    public void setOnRequestItemClickListener(OnRequestItemClickListener listener){
        this.listener=listener;
    }
    public void setOnChangeItemClickListener(OnChangeItemClickListener listener2){
        this.listener2=listener2;
    }
    public static class ListViewHolder extends RecyclerView.ViewHolder{
        TextView day, content, time, money, cat;
        ImageView request, change;

        OnRequestItemClickListener listener;
        OnChangeItemClickListener listener2;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            day=itemView.findViewById(R.id.day);
            content=itemView.findViewById(R.id.content);
            time=itemView.findViewById(R.id.time);
            money=itemView.findViewById(R.id.money);
            cat=itemView.findViewById(R.id.cat);
            request=itemView.findViewById(R.id.request);
            change=itemView.findViewById(R.id.change);

            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.requestItemClick(ListViewHolder.this, request, position);
                    }
                }
            });
            change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(listener2!=null){
                        listener2.changeItemClick(ListViewHolder.this, change, cat, position);
                    }
                }
            });
        }
        public void setItem(Pay_History item){
            int a=item.getDate().indexOf("월");
            int b=item.getDate().indexOf("일");
            day.setText(item.getDate().substring(a+1, b+1));
            time.setText(item.getDate().substring(b+1));
            content.setText(item.getContent());
            money.setText(item.getTotal()/10000+"(만원)");
            cat.setText(item.getCategory());
        }
        public void setOnRequestItemClickListener(OnRequestItemClickListener listener){
            this.listener=listener;
        }
        public void setOnChangeItemClickListener(OnChangeItemClickListener listener2){
            this.listener2=listener2;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.list_item, parent, false);
        return new ListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListViewHolder vh=(ListViewHolder)holder;
        vh.setItem(items.get(position));
        vh.setOnRequestItemClickListener(listener);
        vh.setOnChangeItemClickListener(listener2);
        Glide.with(context).load(R.drawable.request_transfer).into(vh.request);
        Glide.with(context).load(R.drawable.change_category).into(vh.change);
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public void changeItem(ArrayList<Pay_History> item){
        if(items!=null){
            items=null;
        }
        this.items=item;
        notifyDataSetChanged();
    }
}
