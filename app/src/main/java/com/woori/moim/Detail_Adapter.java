package com.woori.moim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Detail_Adapter extends RecyclerView.Adapter {
    ArrayList<Pay_History> items;
    Context context;
    public Detail_Adapter(ArrayList<Pay_History> items, Context context){
        this.items=items;
        this.context=context;
    }
    static final class DetailViewHolder extends RecyclerView.ViewHolder{
        TextView date, category, money, members;
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.date);
            category=itemView.findViewById(R.id.category);
            money=itemView.findViewById(R.id.money);
            members=itemView.findViewById(R.id.member);
        }
        public void setItem(Pay_History item){
            int syidx, eyidx, smidx, emdix, sdidx, edidx;
            syidx=eyidx=smidx=emdix=sdidx=edidx=0;
            for(int i=1; i<item.getDate().length(); i++){
                if(item.getDate().charAt(i)=='년'){
                    eyidx=i;
                    smidx=eyidx+2;
                }
                if(item.getDate().charAt(i)=='월'){
                    emdix=i;
                    sdidx=emdix+1;
                }
                if(item.getDate().charAt(i)=='일'){
                    edidx=i;
                }
            }
            date.setText(item.getDate().substring(syidx, eyidx)+"."+item.getDate().substring(smidx, emdix)+"."+item.getDate().substring(sdidx, edidx));
            category.setText(item.getCategory());
            money.setText(item.getTotal()/10000+"(만원)");
            members.setText(item.getMembers().size()+"명");
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=inflater.inflate(R.layout.monthdetail_item, parent, false);
        return new DetailViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DetailViewHolder vh=(DetailViewHolder)holder;
        vh.setItem(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items==null?0:items.size();
    }

    public void changeItem(ArrayList<Pay_History> items){
        if(this.items!=null){
            this.items=null;
        }
        this.items=items;
        notifyDataSetChanged();
    }
}
