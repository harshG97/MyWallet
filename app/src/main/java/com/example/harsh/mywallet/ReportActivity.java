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

import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {

    private TextView date1, date2, chart1,chart2,chart3;
    private DatePicker datePicker;
    private Calendar calendar;
    private int year1, month1, day1, day2, month2, year2;
    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);



        chart1 =  (TextView) findViewById(R.id.chart1);
        chart2 = (TextView)findViewById(R.id.chart2);
        chart3 = (TextView )findViewById(R.id.chart3);

        calendar = Calendar.getInstance();
        year2 = calendar.get(Calendar.YEAR);
        month2 = calendar.get(Calendar.MONTH);
        day2 = calendar.get(Calendar.DAY_OF_MONTH);

      //  year1 = year2;
      //  month1 = month2;
      //  day1 = 1;

        Log.i("year", Integer.toString(year1));

        /*
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
        */

        chart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ReportActivity.this, PieActivity.class);
                i.putExtra("dates", new int[] {day2,month2,year2});
                startActivity(i);
            }
        });

        chart2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, GraphActivity.class);
                i.putExtra("dates", new int[] {day2,month2,year2,1});
                startActivity(i);

            }
        });

        chart3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportActivity.this, Graph2Activity.class);
                i.putExtra("dates", new int[] {day2,month2,year2,2});
                startActivity(i);

            }
        });


    }

    /*private void showDate(int year, int month, int day, TextView date) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        //  expense = db.getExpense(day, month+1, year);
        // mAdapter.notifyDataSetChanged();

    }

    public void setDate(View view) {

        if(view == findViewById(R.id.date1)){
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
                    myDateListener, year2, month2, day2);
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
            */

}
