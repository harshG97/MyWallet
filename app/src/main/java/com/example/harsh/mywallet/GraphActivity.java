package com.example.harsh.mywallet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.harsh.mywallet.sql.DatabaseHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private int[] dates;
    //BarChart chart ;
    //BarChart chart ;
    //chart = (BarChart) findViewById(R.id.chart1);
    ArrayList<String> BarEntryLabels ;
    BarDataSet Bardataset ;
    BarData BARDATA ;

    ArrayList<String> amount;
    int currMonth,currYear;
    TextView month,year;
    private DatabaseHandler db;
    private String months[]= {"Jan", "Feb", "Mar", "Apr", "May", "Jun"," Jul", "Aug","Sep", "Oct","Nov", "Dec"};
    private int countInc,countDec = 1;

    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        db = new DatabaseHandler(this);
        dates = getIntent().getIntArrayExtra("dates");

        month = (TextView)findViewById(R.id.month);
        year = (TextView) findViewById(R.id.year);

        month.setText(months[dates[1]]);
        year.setText(Integer.toString(dates[2]));

        currMonth= dates[1];
        currYear=dates[2];



        BarChart chart ;
        flag = dates[3];
        chart = (BarChart) findViewById(R.id.chart1);
        ArrayList<BarEntry> BARENTRY ;

        //BarChart chart ;

        //BARENTRY = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();

        BARENTRY=AddValuesToBARENTRY();
        Log.i("entry",BARENTRY.toString());

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(2000);
        //chart.clear();


    }

    public ArrayList<BarEntry> AddValuesToBARENTRY(){

        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        if(flag==2)
        amount = db.getMonthlyBar( currYear);

        else
            amount = db.getbar(currMonth+ 1, currYear);

        Log.i("amount",amount.toString());
        int i =0;
        while(i<amount.size()){

            BARENTRY.add(new BarEntry(Float.parseFloat(amount.get(i)), i));
            i++;
        }

        return BARENTRY;



    }

    public void AddValuesToBarEntryLabels(){

        int i =0;
        BarEntryLabels.clear();
        if(flag ==1) {
            while (i < amount.size()) {
                BarEntryLabels.add(Integer.toString(i + 1));
                ++i;
            }
        }

        else {


            int j = dates[1] + 1;
            while (i < amount.size()) {
                BarEntryLabels.add(months[j % 12]);
                ++i;
                ++j;
            }
        }

        Log.i("labels", BarEntryLabels.toString());



    }

    public  void monthChange(View v){

        if(v == findViewById(R.id.left1)){
            month.setText(months[ (currMonth-1 )< 0 ? 12+ currMonth-1 : currMonth-1 ]  );
            if(currMonth-1 < 0){
                currMonth = 12+ currMonth-1;
            }
            else
                currMonth = currMonth-1;

        }

        else {
            month.setText(months[currMonth + 1 > 11 ? 12 - (currMonth + 1) : currMonth + 1]);
            if(currMonth + 1 > 11)
                currMonth = 12 - (currMonth + 1);
            else
                currMonth = currMonth + 1;

        }
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();

       // chart.clear();
        BarChart chart ;
        chart = (BarChart) findViewById(R.id.chart1);
        BARENTRY=AddValuesToBARENTRY();
        Log.i("entry",BARENTRY.toString());

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(2000);
    }

    public void yearChange(View v){
        if(v==findViewById(R.id.left2)){
            year.setText(Integer.toString(--currYear));

        }
        else{
            year.setText(Integer.toString(++currYear));

        }

        ArrayList<BarEntry> BARENTRY = new ArrayList<>();

       // chart.clear();
        BarChart chart ;
        chart = (BarChart) findViewById(R.id.chart1);

        BARENTRY= AddValuesToBARENTRY();
        Log.i("entry",BARENTRY.toString());

        AddValuesToBarEntryLabels();

        Bardataset = new BarDataSet(BARENTRY, "Projects");

        BARDATA = new BarData(BarEntryLabels, Bardataset);

        Bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(BARDATA);

        chart.animateY(2000);
    }
}
