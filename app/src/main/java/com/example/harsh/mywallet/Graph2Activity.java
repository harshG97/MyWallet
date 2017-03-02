package com.example.harsh.mywallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.harsh.mywallet.sql.DatabaseHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graph2Activity extends AppCompatActivity {

    private int[] dates;
    BarChart chart ;

    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;
    ArrayList<String> amount;
    int currMonth,currYear;
    TextView year;
    private DatabaseHandler db;
    private String months[]= {"Jan", "Feb", "Mar", "Apr", "May", "Jun"," Jul", "Aug","Sep", "Oct","Nov", "Dec"};
    private int countInc,countDec = 1;

    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph2);

        db = new DatabaseHandler(this);
        dates = getIntent().getIntArrayExtra("dates");

        year = (TextView) findViewById(R.id.yearGraph);

        year.setText(Integer.toString(dates[2]));

        currYear=dates[2];



        flag = dates[3];
        chart = (BarChart) findViewById(R.id.chart2);
        ArrayList<BarEntry> BARENTRY ;

        BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        AddValuesToBARENTRY(BARENTRY);

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(2000);


    }

    public void AddValuesToBARENTRY(ArrayList<BarEntry> BARENTRY){


            amount = db.getMonthlyBar( currYear);


        int i =0;
        while(i<amount.size()){

            BARENTRY.add(new BarEntry(Float.parseFloat(amount.get(i)), i));
            i++;
        }



    }

    public void AddValuesToBarEntryLabels(){

        int i =0;


//amount.size()
            //int j = dates[4] + 1;
            while (i < 12) {
                BarEntryLabels.add(months[i]);
                ++i;
               // ++j;

             }
    }



    public void yearchange(View v){
        if(v==findViewById(R.id.yearLeft)){
            year.setText(Integer.toString(--currYear));

        }
        else{
            year.setText(Integer.toString(++currYear));

        }

        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        AddValuesToBARENTRY(BARENTRY);
       // AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(2000);
    }
}



