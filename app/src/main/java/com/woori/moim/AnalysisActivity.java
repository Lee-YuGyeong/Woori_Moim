package com.woori.moim;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;

public class AnalysisActivity extends AppCompatActivity {
    PieChart category_piechart;
    TextView want_month, want_category;
    ImageView month_btn, category_btn;
    RecyclerView month_recyclerview;
    BarChart moim_barchart, my_barchart, expect_barchart;
    Detail_Adapter adapter;
    Context context;

    ArrayList<Pay_History> total;
    ArrayList<Pay_History> r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12;
    ArrayList<Pay_History> c1, c2, c3, c4, c5, c6, c7, c8, c9;
    ArrayList<Pay_History> mr1, mr2, mr3, mr4, mr5, mr6, mr7, mr8, mr9, mr10, mr11, mr12;
    ArrayList<Pay_History> mc1, mc2, mc3, mc4, mc5, mc6, mc7, mc8, mc9;
    ArrayList<Integer> moim_monthtotal, my_monthtotal;
    ArrayList<Integer> expect_avg, expect_regression, expect_total;
    int select_month=-1, select_cat=-1;
    String cat="전체";
    String mon="전체";
    ArrayList<ImageView> list;
    ArrayList<ImageView> list2;

    String myname="고우준";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);

        setting_view();
        setting_category_data();
        setting_month_data();

        setting_recyclerview();
        setting_piechart();
        setting_moim_barchart();
        setting_my_barchart();

        calculate_next();
        setting_expect_barchart();

        setting_select_month_btn();
        setting_select_category_btn();
    }

    public void setting_view(){
        context=this;
        category_piechart=findViewById(R.id.category_piechart);
        want_month=findViewById(R.id.want_month);
        want_category=findViewById(R.id.want_category);
        month_btn=findViewById(R.id.month_btn);
        Glide.with(this).load(R.drawable.want_month).into(month_btn);
        category_btn=findViewById(R.id.category_btn);
        Glide.with(this).load(R.drawable.want_category).into(category_btn);
        month_recyclerview=findViewById(R.id.month_recyclerview);
        moim_barchart=findViewById(R.id.moim_barchart);
        my_barchart=findViewById(R.id.my_barchart);
        expect_barchart=findViewById(R.id.expect_barchart);

        r1=new ArrayList<>();r2=new ArrayList<>();r3=new ArrayList<>();r4=new ArrayList<>();r5=new ArrayList<>();r6=new ArrayList<>();r7=new ArrayList<>();r8=new ArrayList<>();r9=new ArrayList<>();r10=new ArrayList<>();r11=new ArrayList<>();r12=new ArrayList<>();
        c1=new ArrayList<>();c2=new ArrayList<>();c3=new ArrayList<>();c4=new ArrayList<>();c5=new ArrayList<>();c6=new ArrayList<>();c7=new ArrayList<>();c8=new ArrayList<>();c9=new ArrayList<>();
        mr1=new ArrayList<>();mr2=new ArrayList<>();mr3=new ArrayList<>();mr4=new ArrayList<>();mr5=new ArrayList<>();mr6=new ArrayList<>();mr7=new ArrayList<>();mr8=new ArrayList<>();mr9=new ArrayList<>();mr10=new ArrayList<>();mr11=new ArrayList<>();mr12=new ArrayList<>();
        mc1=new ArrayList<>();mc2=new ArrayList<>();mc3=new ArrayList<>();mc4=new ArrayList<>();mc5=new ArrayList<>();mc6=new ArrayList<>();mc7=new ArrayList<>();mc8=new ArrayList<>();mc9=new ArrayList<>();
        list=new ArrayList<>();
        list2=new ArrayList<>();

        moim_monthtotal=new ArrayList<>();
        my_monthtotal=new ArrayList<>();
        expect_avg=new ArrayList<>();
        expect_regression=new ArrayList<>();
        expect_total=new ArrayList<>();
        for(int i=1; i<=12; i++){
            moim_monthtotal.add(0);
            my_monthtotal.add(0);
        }
    }
    public void setting_category_data(){
        total=ManagePayHistory.getInstance().getList();
        for(int i=0; i<total.size(); i++){
            if(total.get(i).getCategory().equals("영화/문화")){
                c1.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc1.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("레저/스포츠")){
                c2.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc2.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("식당")){
                c3.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc3.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("카페")){
                c4.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc4.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("여가")){
                c5.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc5.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("술/유흥")){
                c6.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc6.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("숙박")){
                c7.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc7.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("경조사")){
                c8.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc8.add(total.get(i));
                    }
                }
            }
            else if(total.get(i).getCategory().equals("기타")){
                c9.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mc9.add(total.get(i));
                    }
                }
            }
        }
    }
    public void setting_month_data(){
        for(int i=0; i<total.size(); i++){
            int stidx=total.get(i).getDate().indexOf("년");
            int edidx=total.get(i).getDate().indexOf("월");
            int idx=0;
            String d=total.get(i).getDate();
            for(int j=stidx; j<=edidx; j++){
                if(d.charAt(j)==' '){
                    idx=j+1;
                    break;
                }
            }
            String month=d.substring(idx, edidx);
            if(month.equals("01")){
                r1.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr1.add(total.get(i));
                        my_monthtotal.set(0, (int)(my_monthtotal.get(0)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(0, (int) (moim_monthtotal.get(0)+total.get(i).getTotal()));
            }
            else if(month.equals("02")){
                r2.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr2.add(total.get(i));
                        my_monthtotal.set(1, (int)(my_monthtotal.get(1)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(1, (int) (moim_monthtotal.get(1)+total.get(i).getTotal()));
            }
            else if(month.equals("03")){
                r3.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr3.add(total.get(i));
                        my_monthtotal.set(2, (int)(my_monthtotal.get(2)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(2, (int) (moim_monthtotal.get(2)+total.get(i).getTotal()));
            }
            else if(month.equals("04")){
                r4.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr4.add(total.get(i));
                        my_monthtotal.set(3, (int)(my_monthtotal.get(3)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(3, (int) (moim_monthtotal.get(3)+total.get(i).getTotal()));
            }
            else if(month.equals("05")){
                r5.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr5.add(total.get(i));
                        my_monthtotal.set(4, (int)(my_monthtotal.get(4)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(4, (int) (moim_monthtotal.get(4)+total.get(i).getTotal()));
            }
            else if(month.equals("06")){
                r6.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr6.add(total.get(i));
                        my_monthtotal.set(5, (int)(my_monthtotal.get(5)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(5, (int) (moim_monthtotal.get(5)+total.get(i).getTotal()));
            }
            else if(month.equals("07")){
                r7.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr7.add(total.get(i));
                        my_monthtotal.set(6, (int)(my_monthtotal.get(6)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(6, (int) (moim_monthtotal.get(6)+total.get(i).getTotal()));
            }
            else if(month.equals("08")){
                r8.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr8.add(total.get(i));
                        my_monthtotal.set(7, (int)(my_monthtotal.get(7)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(7, (int) (moim_monthtotal.get(7)+total.get(i).getTotal()));
            }
            else if(month.equals("09")){
                r9.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr9.add(total.get(i));
                        my_monthtotal.set(8, (int)(my_monthtotal.get(8)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(8, (int) (moim_monthtotal.get(8)+total.get(i).getTotal()));
            }
            else if(month.equals("10")){
                r10.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr10.add(total.get(i));
                        my_monthtotal.set(9, (int)(my_monthtotal.get(9)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(9, (int) (moim_monthtotal.get(9)+total.get(i).getTotal()));
            }
            else if(month.equals("11")){
                r11.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr11.add(total.get(i));
                        my_monthtotal.set(10, (int)(my_monthtotal.get(10)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(10, (int) (moim_monthtotal.get(10)+total.get(i).getTotal()));
            }
            else if(month.equals("12")){
                r12.add(total.get(i));
                for(int j=0; j<total.get(i).getMembers().size(); j++){
                    if(total.get(i).getMembers().get(j).equals(myname)){
                        mr12.add(total.get(i));
                        my_monthtotal.set(11, (int)(my_monthtotal.get(11)+total.get(i).getTotal()/total.get(i).getMembers().size()));
                        break;
                    }
                }
                moim_monthtotal.set(11, (int) (moim_monthtotal.get(11)+total.get(i).getTotal()));
            }
        }
    }

    public void setting_recyclerview(){
        adapter=new Detail_Adapter(total, this);
        month_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        month_recyclerview.setAdapter(adapter);
    }
    public void setting_piechart(){
        category_piechart.setUsePercentValues(true);
        category_piechart.getDescription().setEnabled(false);
        category_piechart.setExtraOffsets(5, 10, 5, 5);

        category_piechart.setDrawHoleEnabled(true);
        category_piechart.setHoleColor(Color.WHITE);
        category_piechart.setTransparentCircleRadius(31f);

        ArrayList<PieEntry> yValues=new ArrayList<>();
        if(c1.size()!=0){
            yValues.add(new PieEntry(c1.size(), "영화/문화"));
        }
        if(c2.size()!=0){
            yValues.add(new PieEntry(c2.size(), "레저/스포츠"));
        }
        if(c3.size()!=0){
            yValues.add(new PieEntry(c3.size(), "식당"));
        }
        if(c4.size()!=0){
            yValues.add(new PieEntry(c4.size(), "카페"));
        }
        if(c5.size()!=0){
            yValues.add(new PieEntry(c5.size(), "여가"));
        }
        if(c6.size()!=0){
            yValues.add(new PieEntry(c6.size(), "술/유흥"));
        }
        if(c7.size()!=0){
            yValues.add(new PieEntry(c7.size(), "숙박"));
        }
        if(c8.size()!=0){
            yValues.add(new PieEntry(c8.size(), "경조사"));
        }
        if(c9.size()!=0){
            yValues.add(new PieEntry(c9.size(), "기타"));
        }


        PieDataSet dataSet=new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setValueTextSize(10f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        PieData data=new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        category_piechart.setData(data);
        category_piechart.animateXY(1000, 1000);
        category_piechart.invalidate();
    }

    public void setting_moim_barchart(){

        moim_barchart.addBar(new BarModel("1월", moim_monthtotal.get(0), Color.BLACK));
        moim_barchart.addBar(new BarModel("2월", moim_monthtotal.get(1), Color.BLACK));
        moim_barchart.addBar(new BarModel("3월", moim_monthtotal.get(2), Color.BLACK));
        moim_barchart.addBar(new BarModel("4월", moim_monthtotal.get(3), Color.BLACK));
        moim_barchart.addBar(new BarModel("5월", moim_monthtotal.get(4), Color.BLACK));
        moim_barchart.addBar(new BarModel("6월", moim_monthtotal.get(5), Color.BLACK));
        moim_barchart.addBar(new BarModel("7월", moim_monthtotal.get(6), Color.BLACK));
        moim_barchart.addBar(new BarModel("8월", moim_monthtotal.get(7), Color.BLACK));
        moim_barchart.addBar(new BarModel("9월", moim_monthtotal.get(8), Color.BLACK));
        moim_barchart.addBar(new BarModel("10월", moim_monthtotal.get(9), Color.BLACK));
        moim_barchart.addBar(new BarModel("11월", moim_monthtotal.get(10), Color.BLACK));
        moim_barchart.addBar(new BarModel("12월", moim_monthtotal.get(11), Color.BLACK));



        moim_barchart.startAnimation();
    }
    public void setting_my_barchart(){
        my_barchart.addBar(new BarModel("1월", my_monthtotal.get(0), Color.BLACK));
        my_barchart.addBar(new BarModel("2월", my_monthtotal.get(1), Color.BLACK));
        my_barchart.addBar(new BarModel("3월", my_monthtotal.get(2), Color.BLACK));
        my_barchart.addBar(new BarModel("4월", my_monthtotal.get(3), Color.BLACK));
        my_barchart.addBar(new BarModel("5월", my_monthtotal.get(4), Color.BLACK));
        my_barchart.addBar(new BarModel("6월", my_monthtotal.get(5), Color.BLACK));
        my_barchart.addBar(new BarModel("7월", my_monthtotal.get(6), Color.BLACK));
        my_barchart.addBar(new BarModel("8월", my_monthtotal.get(7), Color.BLACK));
        my_barchart.addBar(new BarModel("9월", my_monthtotal.get(8), Color.BLACK));
        my_barchart.addBar(new BarModel("10월", my_monthtotal.get(9), Color.BLACK));
        my_barchart.addBar(new BarModel("11월", my_monthtotal.get(10), Color.BLACK));
        my_barchart.addBar(new BarModel("12월", my_monthtotal.get(11), Color.BLACK));

        my_barchart.startAnimation();
    }

    public void calculate_next(){
        float total=0;
        if(c1.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c1.size(); i++){
                total=total+c1.get(i).getTotal();
            }
            expect_avg.add((int)total/c1.size());
        }
        total=0;
        if(c2.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c2.size(); i++){
                total=total+c2.get(i).getTotal();
            }
            expect_avg.add((int)total/c2.size());
        }
        total=0;
        if(c3.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c3.size(); i++){
                total=total+c3.get(i).getTotal();
            }
            expect_avg.add((int)total/c3.size());
        }
        total=0;
        if(c4.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c4.size(); i++){
                total=total+c4.get(i).getTotal();
            }
            expect_avg.add((int)total/c4.size());
        }
        total=0;
        if(c5.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c5.size(); i++){
                total=total+c5.get(i).getTotal();
            }
            expect_avg.add((int)total/c5.size());
        }
        total=0;
        if(c6.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c6.size(); i++){
                total=total+c6.get(i).getTotal();
            }
            expect_avg.add((int)total/c6.size());
        }
        total=0;
        if(c7.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c7.size(); i++){
                total=total+c7.get(i).getTotal();
            }
            expect_avg.add((int)total/c7.size());
        }
        total=0;
        if(c8.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c8.size(); i++){
                total=total+c8.get(i).getTotal();
            }
            expect_avg.add((int)total/c8.size());
        }
        total=0;
        if(c9.size()==0){
            expect_avg.add(0);
        }
        else{
            for(int i=0; i<c9.size(); i++){
                total=total+c9.get(i).getTotal();
            }
            expect_avg.add((int)total/c9.size());
        }
        //average
        float x, y, xx, xy;
        float w0, w1;
        x=y=xx=xy=0;
        if(c1.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c1.size(); i++){
                x=x+(i+1);
                y=y+c1.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c1.get(i).getTotal();
            }
            w1=(c1.size()*xy-x*y)/(c1.size()*xx-x*x);
            w0=(y-w1*x)/c1.size();
            expect_regression.add((int)(w0+w1*(c1.size())));
        }
        x=y=xx=xy=0;
        if(c2.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c2.size(); i++){
                x=x+(i+1);
                y=y+c2.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c2.get(i).getTotal();
            }
            w1=(c2.size()*xy-x*y)/(c2.size()*xx-x*x);
            w0=(y-w1*x)/c2.size();
            expect_regression.add((int)(w0+w1*(c2.size())));
        }
        x=y=xx=xy=0;
        if(c3.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c3.size(); i++){
                x=x+(i+1);
                y=y+c3.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c3.get(i).getTotal();
            }
            w1=(c3.size()*xy-x*y)/(c3.size()*xx-x*x);
            w0=(y-w1*x)/c3.size();
            expect_regression.add((int)(w0+w1*(c3.size())));
        }
        x=y=xx=xy=0;
        if(c4.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c4.size(); i++){
                x=x+(i+1);
                y=y+c4.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c4.get(i).getTotal();
            }
            w1=(c4.size()*xy-x*y)/(c4.size()*xx-x*x);
            w0=(y-w1*x)/c4.size();
            expect_regression.add((int)(w0+w1*(c4.size())));
        }
        x=y=xx=xy=0;
        if(c5.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c5.size(); i++){
                x=x+(i+1);
                y=y+c5.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c5.get(i).getTotal();
            }
            w1=(c5.size()*xy-x*y)/(c5.size()*xx-x*x);
            w0=(y-w1*x)/c5.size();
            expect_regression.add((int)(w0+w1*(c5.size())));
        }
        x=y=xx=xy=0;
        if(c6.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c6.size(); i++){
                x=x+(i+1);
                y=y+c6.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c6.get(i).getTotal();
            }
            w1=(c6.size()*xy-x*y)/(c6.size()*xx-x*x);
            w0=(y-w1*x)/c6.size();
            expect_regression.add((int)(w0+w1*(c6.size())));
        }
        x=y=xx=xy=0;
        if(c7.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c7.size(); i++){
                x=x+(i+1);
                y=y+c7.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c7.get(i).getTotal();
            }
            w1=(c7.size()*xy-x*y)/(c7.size()*xx-x*x);
            w0=(y-w1*x)/c7.size();
            expect_regression.add((int)(w0+w1*(c7.size())));
        }
        x=y=xx=xy=0;
        if(c8.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c8.size(); i++){
                x=x+(i+1);
                y=y+c8.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c8.get(i).getTotal();
            }
            w1=(c8.size()*xy-x*y)/(c8.size()*xx-x*x);
            w0=(y-w1*x)/c8.size();
            expect_regression.add((int)(w0+w1*(c8.size())));
        }
        x=y=xx=xy=0;
        if(c9.size()==0){
            expect_regression.add(0);
        }
        else{
            for(int i=0; i<c9.size(); i++){
                x=x+(i+1);
                y=y+c9.get(i).getTotal();
                xx=xx+(i+1)*(i+1);
                xy=xy+(i+1)*c9.get(i).getTotal();
            }
            w1=(c9.size()*xy-x*y)/(c9.size()*xx-x*x);
            w0=(y-w1*x)/c9.size();
            expect_regression.add((int)(w0+w1*(c9.size())));
        }
        //regression
        for(int i=0; i<9; i++){
            expect_total.add((expect_avg.get(i)+expect_regression.get(i))/2);
        }
        //average of avg and regression
    }

    public void setting_expect_barchart(){
        expect_barchart.addBar(new BarModel("문화", expect_total.get(0), Color.BLACK));
        expect_barchart.addBar(new BarModel("레저", expect_total.get(1), Color.BLACK));
        expect_barchart.addBar(new BarModel("식당", expect_total.get(2), Color.BLACK));
        expect_barchart.addBar(new BarModel("카페", expect_total.get(3), Color.BLACK));
        expect_barchart.addBar(new BarModel("여가", expect_total.get(4), Color.BLACK));
        expect_barchart.addBar(new BarModel("술/유흥", expect_total.get(5), Color.BLACK));
        expect_barchart.addBar(new BarModel("숙박", expect_total.get(6), Color.BLACK));
        expect_barchart.addBar(new BarModel("경조사", expect_total.get(7), Color.BLACK));
        expect_barchart.addBar(new BarModel("기타", expect_total.get(8), Color.BLACK));

        expect_barchart.startAnimation();
    }

    public void setting_select_month_btn(){
        month_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout month=(LinearLayout)View.inflate(context, R.layout.select_month, null);
                if(list.size()!=0){
                    list.clear();
                }
                list.add((ImageView)month.findViewById(R.id.b1));
                list.add((ImageView)month.findViewById(R.id.b2));
                list.add((ImageView)month.findViewById(R.id.b3));
                list.add((ImageView)month.findViewById(R.id.b4));
                list.add((ImageView)month.findViewById(R.id.b5));
                list.add((ImageView)month.findViewById(R.id.b6));
                list.add((ImageView)month.findViewById(R.id.b7));
                list.add((ImageView)month.findViewById(R.id.b8));
                list.add((ImageView)month.findViewById(R.id.b9));
                list.add((ImageView)month.findViewById(R.id.b10));
                list.add((ImageView)month.findViewById(R.id.b11));
                list.add((ImageView)month.findViewById(R.id.b12));
                ImageView i1=month.findViewById(R.id.i1);
                ImageView i2=month.findViewById(R.id.i2);
                ImageView i3=month.findViewById(R.id.i3);
                ImageView i4=month.findViewById(R.id.i4);
                ImageView i5=month.findViewById(R.id.i5);
                ImageView i6=month.findViewById(R.id.i6);
                ImageView i7=month.findViewById(R.id.i7);
                ImageView i8=month.findViewById(R.id.i8);
                ImageView i9=month.findViewById(R.id.i9);
                ImageView i10=month.findViewById(R.id.i10);
                ImageView i11=month.findViewById(R.id.i11);
                ImageView i12=month.findViewById(R.id.i12);
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
                requestOptions.skipMemoryCache(false);
                requestOptions.signature(new ObjectKey(System.currentTimeMillis()));
                Glide.with(context).load(R.drawable.one).into(i1);
                Glide.with(context).load(R.drawable.two).into(i2);
                Glide.with(context).load(R.drawable.three).into(i3);
                Glide.with(context).load(R.drawable.four).apply(requestOptions).into(i4);
                Glide.with(context).load(R.drawable.five).into(i5);
                Glide.with(context).load(R.drawable.six).into(i6);
                Glide.with(context).load(R.drawable.seven).into(i7);
                Glide.with(context).load(R.drawable.eight).into(i8);
                Glide.with(context).load(R.drawable.nine).into(i9);
                Glide.with(context).load(R.drawable.ten).into(i10);
                Glide.with(context).load(R.drawable.eleven).into(i11);
                Glide.with(context).load(R.drawable.twelve).into(i12);
                i1.setOnClickListener(monthclick);
                i2.setOnClickListener(monthclick);
                i3.setOnClickListener(monthclick);
                i4.setOnClickListener(monthclick);
                i5.setOnClickListener(monthclick);
                i6.setOnClickListener(monthclick);
                i7.setOnClickListener(monthclick);
                i8.setOnClickListener(monthclick);
                i9.setOnClickListener(monthclick);
                i10.setOnClickListener(monthclick);
                i11.setOnClickListener(monthclick);
                i12.setOnClickListener(monthclick);
                AlertDialog monthdialog=new AlertDialog.Builder(context)
                        .setView(month)
                        .setCancelable(false)
                        .setNeutralButton("전체", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                select_month=0;
                                change_recyclerview_bymonth(select_month);
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", null)
                        .create();
                monthdialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button=((AlertDialog)monthdialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(select_month==-1){
                                    Toast.makeText(AnalysisActivity.this, "월을 선택해주세요.", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    change_recyclerview_bymonth(select_month);
                                    monthdialog.dismiss();
                                }
                            }
                        });
                    }
                });
                monthdialog.show();
            }
        });
    }

    public void setting_select_category_btn(){
        category_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout category=(LinearLayout)View.inflate(context, R.layout.select_category, null);
                if(list2.size()!=0){
                    list2.clear();
                }
                list2.add((ImageView)category.findViewById(R.id.b00));
                list2.add((ImageView)category.findViewById(R.id.b20));
                list2.add((ImageView)category.findViewById(R.id.b30));
                list2.add((ImageView)category.findViewById(R.id.b40));
                list2.add((ImageView)category.findViewById(R.id.b50));
                list2.add((ImageView)category.findViewById(R.id.b60));
                list2.add((ImageView)category.findViewById(R.id.b70));
                list2.add((ImageView)category.findViewById(R.id.b80));
                list2.add((ImageView)category.findViewById(R.id.b90));
                ImageView i1=category.findViewById(R.id.i00);
                ImageView i2=category.findViewById(R.id.i20);
                ImageView i3=category.findViewById(R.id.i30);
                ImageView i4=category.findViewById(R.id.i40);
                ImageView i5=category.findViewById(R.id.i50);
                ImageView i6=category.findViewById(R.id.i60);
                ImageView i7=category.findViewById(R.id.i70);
                ImageView i8=category.findViewById(R.id.i80);
                ImageView i9=category.findViewById(R.id.i90);
                Glide.with(context).load(R.drawable.a1).into(i1);
                Glide.with(context).load(R.drawable.a2).into(i2);
                Glide.with(context).load(R.drawable.a3).into(i3);
                Glide.with(context).load(R.drawable.a4).into(i4);
                Glide.with(context).load(R.drawable.a5).into(i5);
                Glide.with(context).load(R.drawable.a6).into(i6);
                Glide.with(context).load(R.drawable.a7).into(i7);
                Glide.with(context).load(R.drawable.a8).into(i8);
                Glide.with(context).load(R.drawable.a9).into(i9);
                i1.setOnClickListener(categoryclick);
                i2.setOnClickListener(categoryclick);
                i3.setOnClickListener(categoryclick);
                i4.setOnClickListener(categoryclick);
                i5.setOnClickListener(categoryclick);
                i6.setOnClickListener(categoryclick);
                i7.setOnClickListener(categoryclick);
                i8.setOnClickListener(categoryclick);
                i9.setOnClickListener(categoryclick);
                AlertDialog categorydialog=new AlertDialog.Builder(context)
                        .setView(category)
                        .setCancelable(false)
                        .setNeutralButton("전체", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                select_cat=0;
                                change_recyclerview_bycat(select_cat);
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", null)
                        .create();
                categorydialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button=((AlertDialog)categorydialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(select_cat==-1){
                                    Toast.makeText(AnalysisActivity.this, "카테고리를 선택해주세요", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    change_recyclerview_bycat(select_cat);
                                    categorydialog.dismiss();
                                }
                            }
                        });
                    }
                });
                categorydialog.show();
            }
        });
    }

    public View.OnClickListener monthclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch(id){
                case R.id.i1:
                    select_month=1;
                    changecolor(select_month);
                    return;
                case R.id.i2:
                    select_month=2;
                    changecolor(select_month);
                    return;
                case R.id.i3:
                    select_month=3;
                    changecolor(select_month);
                    return;
                case R.id.i4:
                    select_month=4;
                    changecolor(select_month);
                    return;
                case R.id.i5:
                    select_month=5;
                    changecolor(select_month);
                    return;
                case R.id.i6:
                    select_month=6;
                    changecolor(select_month);
                    return;
                case R.id.i7:
                    select_month=7;
                    changecolor(select_month);
                    return;
                case R.id.i8:
                    select_month=8;
                    changecolor(select_month);
                    return;
                case R.id.i9:
                    select_month=9;
                    changecolor(select_month);
                    return;
                case R.id.i10:
                    select_month=10;
                    changecolor(select_month);
                    return;
                case R.id.i11:
                    select_month=11;
                    changecolor(select_month);
                    return;
                case R.id.i12:
                    select_month=12;
                    changecolor(select_month);
                    return;
            }
        }
    };
    public void changecolor(int flag){
        flag=flag-1;
        for(int i=0; i<12; i++){
            if(i==flag){
                list.get(i).setBackgroundColor(Color.YELLOW);
            }
            else{
                list.get(i).setBackgroundColor(Color.WHITE);
            }
        }
    }

    public void change_recyclerview_bymonth(int month){
        if(cat.equals("전체")){
            if(month==0){
                adapter.changeItem(total);
                want_month.setText("전체");
                mon="전체";
            }
            else if(month==1){
                adapter.changeItem(r1);
                want_month.setText("1월");
                mon="01월";
            }
            else if(month==2){
                adapter.changeItem(r2);
                want_month.setText("2월");
                mon="02월";
            }
            else if(month==3){
                adapter.changeItem(r3);
                want_month.setText("3월");
                mon="03월";
            }
            else if(month==4){
                adapter.changeItem(r4);
                want_month.setText("4월");
                mon="04월";
            }
            else if(month==5){
                adapter.changeItem(r5);
                want_month.setText("5월");
                mon="05월";
            }
            else if(month==6){
                adapter.changeItem(r6);
                want_month.setText("6월");
                mon="06월";
            }
            else if(month==7){
                adapter.changeItem(r7);
                want_month.setText("7월");
                mon="07월";
            }
            else if(month==8){
                adapter.changeItem(r8);
                want_month.setText("8월");
                mon="08월";
            }
            else if(month==9){
                adapter.changeItem(r9);
                want_month.setText("9월");
                mon="09월";
            }
            else if(month==10){
                adapter.changeItem(r10);
                want_month.setText("10월");
                mon="10월";
            }
            else if(month==11){
                adapter.changeItem(r11);
                want_month.setText("11월");
                mon="11월";
            }
            else if(month==12){
                adapter.changeItem(r12);
                want_month.setText("12월");
                mon="12월";
            }
        }
        else{
            ArrayList<Pay_History> choice=new ArrayList<>();
            if(month==0){
                for(int i=0; i<total.size(); i++){
                    if(total.get(i).getCategory().equals(cat)){
                        choice.add(total.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("전체");
                mon="전체";
            }
            else if(month==1){
                for(int i=0; i<r1.size(); i++){
                    if(r1.get(i).getCategory().equals(cat)){
                        choice.add(r1.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("1월");
                mon="01월";
            }
            else if(month==2){
                for(int i=0; i<r2.size(); i++){
                    if(r2.get(i).getCategory().equals(cat)){
                        choice.add(r2.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("2월");
                mon="02월";
            }
            else if(month==3){
                for(int i=0; i<r3.size(); i++){
                    if(r3.get(i).getCategory().equals(cat)){
                        choice.add(r3.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("3월");
                mon="03월";
            }
            else if(month==4){
                for(int i=0; i<r4.size(); i++){
                    if(r4.get(i).getCategory().equals(cat)){
                        choice.add(r4.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("4월");
                mon="04월";
            }
            else if(month==5){
                for(int i=0; i<r5.size(); i++){
                    if(r5.get(i).getCategory().equals(cat)){
                        choice.add(r5.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("5월");
                mon="05월";
            }
            else if(month==6){
                for(int i=0; i<r6.size(); i++){
                    if(r6.get(i).getCategory().equals(cat)){
                        choice.add(r6.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("6월");
                mon="06월";
            }
            else if(month==7){
                for(int i=0; i<r7.size(); i++){
                    if(r7.get(i).getCategory().equals(cat)){
                        choice.add(r7.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("7월");
                mon="07월";
            }
            else if(month==8){
                for(int i=0; i<r8.size(); i++){
                    if(r8.get(i).getCategory().equals(cat)){
                        choice.add(r8.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("8월");
                mon="08월";
            }
            else if(month==9){
                for(int i=0; i<r9.size(); i++){
                    if(r9.get(i).getCategory().equals(cat)){
                        choice.add(r9.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("9월");
                mon="09월";
            }
            else if(month==10){
                for(int i=0; i<r10.size(); i++){
                    if(r10.get(i).getCategory().equals(cat)){
                        choice.add(r10.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("10월");
                mon="10월";
            }
            else if(month==11){
                for(int i=0; i<r11.size(); i++){
                    if(r11.get(i).getCategory().equals(cat)){
                        choice.add(r11.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("11월");
                mon="11월";
            }
            else if(month==12){
                for(int i=0; i<r12.size(); i++){
                    if(r12.get(i).getCategory().equals(cat)){
                        choice.add(r12.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_month.setText("12월");
                mon="12월";
            }
        }


        select_month=-1;
    }

    public View.OnClickListener categoryclick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id=v.getId();
            switch(id){
                case R.id.i00:
                    select_cat=1;
                    changecolor2(select_cat);
                    return;
                case R.id.i20:
                    select_cat=2;
                    changecolor2(select_cat);
                    return;
                case R.id.i30:
                    select_cat=3;
                    changecolor2(select_cat);
                    return;
                case R.id.i40:
                    select_cat=4;
                    changecolor2(select_cat);
                    return;
                case R.id.i50:
                    select_cat=5;
                    changecolor2(select_cat);
                    return;
                case R.id.i60:
                    select_cat=6;
                    changecolor2(select_cat);
                    return;
                case R.id.i70:
                    select_cat=7;
                    changecolor2(select_cat);
                    return;
                case R.id.i80:
                    select_cat=8;
                    changecolor2(select_cat);
                    return;
                case R.id.i90:
                    select_cat=9;
                    changecolor2(select_cat);
                    return;
            }
        }
    };

    public void changecolor2(int flag){
        flag=flag-1;
        for(int i=0; i<9; i++){
            if(i==flag){
                list2.get(i).setBackgroundColor(Color.YELLOW);
            }
            else{
                list2.get(i).setBackgroundColor(Color.WHITE);
            }
        }
    }

    public void change_recyclerview_bycat(int c){
        if(mon.equals("전체")){
            if(c==0){
                adapter.changeItem(total);
                want_category.setText("전체");
                cat="전체";
            }
            else if(c==1){
                adapter.changeItem(c1);
                want_category.setText("영화/문화");
                cat="영화/문화";
            }
            else if(c==2){
                adapter.changeItem(c2);
                want_category.setText("레저/스포츠");
                cat="레저/스포츠";
            }
            else if(c==3){
                adapter.changeItem(c3);
                want_category.setText("식당");
                cat="식당";
            }
            else if(c==4){
                adapter.changeItem(c4);
                want_category.setText("카페");
                cat="카페";
            }
            else if(c==5){
                adapter.changeItem(c5);
                want_category.setText("여가");
                cat="여가";
            }
            else if(c==6){
                adapter.changeItem(c6);
                want_category.setText("술/유흥");
                cat="술/유흥";
            }
            else if(c==7){
                adapter.changeItem(c7);
                want_category.setText("숙박");
                cat="숙박";
            }
            else if(c==8){
                adapter.changeItem(c8);
                want_category.setText("경조사");
                cat="경조사";
            }
            else if(c==9){
                adapter.changeItem(c9);
                want_category.setText("기타");
                cat="기타";
            }
        }
        else{
            ArrayList<Pay_History> choice=new ArrayList<>();
            if(c==0){
                for(int i=0; i<total.size(); i++){
                    int stidx=total.get(i).getDate().indexOf("년");
                    int edidx=total.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=total.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(total.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("전체");
                cat="전체";
            }
            else if(c==1){
                for(int i=0; i<c1.size(); i++){
                    int stidx=c1.get(i).getDate().indexOf("년");
                    int edidx=c1.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c1.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c1.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("영화/문화");
                cat="영화/문화";
            }
            else if(c==2){
                for(int i=0; i<c2.size(); i++){
                    int stidx=c2.get(i).getDate().indexOf("년");
                    int edidx=c2.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c2.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c2.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("레저/스포츠");
                cat="레저/스포츠";
            }
            else if(c==3){
                for(int i=0; i<c3.size(); i++){
                    int stidx=c3.get(i).getDate().indexOf("년");
                    int edidx=c3.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c3.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c3.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("식당");
                cat="식당";
            }
            else if(c==4){
                for(int i=0; i<c4.size(); i++){
                    int stidx=c4.get(i).getDate().indexOf("년");
                    int edidx=c4.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c4.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c4.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("카페");
                cat="카페";
            }
            else if(c==5){
                for(int i=0; i<c5.size(); i++){
                    int stidx=c5.get(i).getDate().indexOf("년");
                    int edidx=c5.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c5.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c5.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("여가");
                cat="여가";
            }
            else if(c==6){
                for(int i=0; i<c6.size(); i++){
                    int stidx=c6.get(i).getDate().indexOf("년");
                    int edidx=c6.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c6.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c6.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("술/유흥");
                cat="술/유흥";
            }
            else if(c==7){
                for(int i=0; i<c7.size(); i++){
                    int stidx=c7.get(i).getDate().indexOf("년");
                    int edidx=c7.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c7.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c7.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("숙박");
                cat="숙박";
            }
            else if(c==8){
                for(int i=0; i<c8.size(); i++){
                    int stidx=c8.get(i).getDate().indexOf("년");
                    int edidx=c8.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c8.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c8.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("경조사");
                cat="경조사";
            }
            else if(c==9){
                for(int i=0; i<c9.size(); i++){
                    int stidx=c9.get(i).getDate().indexOf("년");
                    int edidx=c9.get(i).getDate().indexOf("월");
                    int idx=0;
                    String d=c9.get(i).getDate();
                    for(int j=stidx; j<=edidx; j++){
                        if(d.charAt(j)==' '){
                            idx=j+1;
                            break;
                        }
                    }
                    String m=d.substring(idx, edidx+1);
                    if(m.equals(mon)){
                        choice.add(c9.get(i));
                    }
                }
                adapter.changeItem(choice);
                want_category.setText("기타");
                cat="기타";
            }
        }
    }
}