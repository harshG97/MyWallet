package com.example.harsh.mywallet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.harsh.mywallet.sql.DatabaseHandler;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class PieActivity extends AppCompatActivity {

   // PieChart pieChart ;
    HashMap sumAmount = new HashMap();
int flag = 0;
    private TextView date1, date2;
    String [] category = {"Food", "Entertainment", "Transport", "Social", "Bills", "Education", "Medical", "Other" };

    ArrayList<String> PieEntryLabels ;
    PieDataSet pieDataSet ;
    PieData pieData ;
    private int[] dates;
    private DatabaseHandler db;
    private Calendar calendar;
    private int year1, month1, day1, day2, month2, year2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
         dates = getIntent().getIntArrayExtra("dates");

        PieChart pieChart ;
        ArrayList<Entry> entries ;
        db = new DatabaseHandler(this);
        Log.i("intent",Integer.toString(dates[0]));
        date2 = (TextView) findViewById(R.id.pieDate2);
        date1 = (TextView) findViewById(R.id.pieDate1);
        calendar = Calendar.getInstance();
        year2 = calendar.get(Calendar.YEAR);
        month2 = calendar.get(Calendar.MONTH);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);

        year1 = year2;
        month1 = month2;
        day1 = 1;

        Log.i("year", Integer.toString(year1));

        showDate(year1, month1+1, day1, date1);
        showDate(year2, month2+1, day2, date2);


        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate(date1);

            }
        });


        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setDate(date2);

            }
        });

        pieChart = (PieChart) findViewById(R.id.chart1);

        entries = new ArrayList<>();

        PieEntryLabels = new ArrayList<String>();

        entries = AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(2000);

    }
    private void showDate(int year, int month, int day, TextView date) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        //  expense = db.getExpense(day, month+1, year);
        // mAdapter.notifyDataSetChanged();

    }

    public void setDate(View view) {

        if(view == findViewById(R.id.pieDate1)){
            flag = 1;
        }
        else
            flag = 2;
        showDialog(999);


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            if(flag == 1)
                return new DatePickerDialog(this,
                        myDateListener, year1, month1, day1);
            else
                return new DatePickerDialog(this,
                        myDateListener, year2, month2, day2);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    if(flag == 1) {
                        year1 = arg1;
                        month1 = arg2;
                        day1 = arg3;
                        showDate(arg1, arg2+1, arg3, date1);
                        Log.i("day1", Integer.toString(day1));
                    }
                    else{
                        year2 = arg1;
                        month2 = arg2;
                        day2 = arg3;
                        showDate(arg1, arg2+1, arg3, date2);
                        Log.i("day2", Integer.toString(day2));
                    }




                }
            };


    public ArrayList<Entry> AddValuesToPIEENTRY(){

        ArrayList<Entry> entries = new ArrayList<>();

        //HashMap sumAmount = new HashMap();
        sumAmount = db.getPie(day1,month1+1,year1,day2,month2+1,year2);
        int i =0;
        int c = 0;
        while(i<sumAmount.size()){
            if( Float.parseFloat(sumAmount.get(category[i]).toString()) >0) {
                entries.add(new BarEntry(Float.parseFloat(sumAmount.get(category[i]).toString()), c));
                c++;
            }
            i++;
        }




        return entries;
    }
//"Food", "Entertainment", "Transport", "Social", "Bills", "Education", "Medical", "Other"
    public void AddValuesToPieEntryLabels(){

        int i = 0;

        PieEntryLabels.clear();
        while(i<sumAmount.size()){
            if(Float.parseFloat(sumAmount.get(category[i]).toString()) >0)
                PieEntryLabels.add(category[i]);
            i++;

        }

    }
    public void redraw(View v){
        PieChart pieChart;
        pieChart = (PieChart) findViewById(R.id.chart1);

        ArrayList<Entry> entries = new ArrayList<>();



        entries = AddValuesToPIEENTRY();

        AddValuesToPieEntryLabels();

        pieDataSet = new PieDataSet(entries, "");

        pieData = new PieData(PieEntryLabels, pieDataSet);

        pieDataSet.setColors(ColorTemplate.PASTEL_COLORS);

        pieChart.setData(pieData);

        pieChart.animateY(2000);

    }
}
