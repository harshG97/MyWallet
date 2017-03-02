package com.example.harsh.mywallet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harsh.mywallet.Adapter.LendingAdapter;
import com.example.harsh.mywallet.models.Lending;
import com.example.harsh.mywallet.sql.DatabaseHandler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LendingActivity extends AppCompatActivity {

    TextView date;
    EditText to, amt;
    RecyclerView list;
    private Calendar calendar;
    private int year,month,day;
    double amount;
    String lentTo;
    Lending newLend, L;
    List<Lending> Lendings ;
    private DatabaseHandler db;
    LendingAdapter mAdapter;
    TextView add ;
    ImageView dateChange;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending);

        dateChange = (ImageView) findViewById(R.id.calImage);
        dateChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lentDateChange();
            }
        });
        add = (TextView) findViewById(R.id.add_txtV);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addLending();
            }
        });
        db = new DatabaseHandler(this);
        date = (TextView) findViewById(R.id.lendDate);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        to = (EditText)findViewById(R.id.lendTo);
        amt = (EditText)findViewById(R.id.lendAmt);


        Lendings = new ArrayList<>();
        Lendings = db.getLendings();
        recyclerView(Lendings);
    }

    @SuppressWarnings("deprecation")
    public void lentDateChange(){
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    year = arg1;
                    month = arg2 ;
                    day = arg3;

                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        date.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
        //  expense = db.getExpense(day, month+1, year);
        // mAdapter.notifyDataSetChanged();
    }

    public void addLending(){
        amount = Double.parseDouble(amt.getText().toString());
        lentTo = to.getText().toString();
        newLend = new Lending(day,month+1,year,lentTo,amount,0);

        db = new DatabaseHandler(this);
        db.addLending(newLend);

        Lendings = db.getLendings();
        recyclerView(Lendings);


    }

    public void recyclerView( List<Lending> LendList){
        final RecyclerView mrecyclerView = (RecyclerView) findViewById(R.id.lendList);
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(linearlayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(),
                linearlayoutManager.getOrientation());
        mrecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new LendingAdapter(LendList, R.layout.lend_row, getApplicationContext());
        mrecyclerView.setAdapter(mAdapter);
    }


}
