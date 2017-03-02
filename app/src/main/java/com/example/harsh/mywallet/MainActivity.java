package com.example.harsh.mywallet;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.example.harsh.mywallet.Adapter.ExpenseAdapter;
import com.example.harsh.mywallet.models.ExpenseDetail;
import com.example.harsh.mywallet.sql.DatabaseHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.support.v7.recyclerview.R.attr.layoutManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView,amount,wallet;
    private int year, month, day;
    private static final int INT= 1;
    private DatabaseHandler db;
    ExpenseAdapter mAdapter;
    private TextView add;
    List<ExpenseDetail> expense = new ArrayList<>();
    private SharedPreferences sharedpreferences;
    private String bdgt;
    DecimalFormat numberFormat;


    NavigationView navigationView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new DatabaseHandler(this);



        Log.i("hi", "onCreate()");

        add = (TextView) findViewById(R.id.addExpense);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivityForResult(intent, INT);
            }
        });


         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dateView = (TextView) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        wallet = (TextView)findViewById(R.id.wallet);
       // wallet.setText(Double.toString(db.getThisMonth(month,year)));
        sharedpreferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        numberFormat = new DecimalFormat("#.00");
        bdgt= sharedpreferences.getString("budget","0.0");
        wallet.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)-db.getThisMonth(month+1,year)))));

        expense = db.getExpense(day, month+1, year);

        amount = (TextView)findViewById(R.id.amount);
        amount.setText(Double.toString(totalExpense(expense)));
        recyclerView(expense);

     //   final RecyclerView mrecyclerView = (RecyclerView) findViewById(R.id.expenseList);
    //    LinearLayoutManager linearlayoutManager = new LinearLayoutManager(this);
    //    mrecyclerView.setLayoutManager(linearlayoutManager);
    //    DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(),
    //           linearlayoutManager.getOrientation());
     //   mrecyclerView.addItemDecoration(dividerItemDecoration);
    //    mAdapter = new ExpenseAdapter(expense, R.layout.list_row, getApplicationContext());
    //    mrecyclerView.setAdapter(mAdapter);

    }

    public void recyclerView( List<ExpenseDetail> expense){
        final RecyclerView mrecyclerView = (RecyclerView) findViewById(R.id.expenseList);
        LinearLayoutManager linearlayoutManager = new LinearLayoutManager(this);
        mrecyclerView.setLayoutManager(linearlayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mrecyclerView.getContext(),
                linearlayoutManager.getOrientation());
        mrecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new ExpenseAdapter(expense, R.layout.list_row, getApplicationContext());
        mrecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (INT) : {
                if (resultCode == ExpenseActivity.RESULT_OK) {
                    ExpenseDetail e = new ExpenseDetail();
                    ArrayList<String> L = new ArrayList<>();
                    L= data.getStringArrayListExtra("expense");
                    e.setCategory(L.get(0));
                    e.setDetails(L.get(1));
                    e.setAmount(Double.parseDouble(L.get(2)));
                    e.setDay(day);
                    e.setMonth(month+1);
                    e.setYear(year);
                    expense.add(e);//

                    db.addExpense( e );
                    recyclerView(expense);
                    //mAdapter.notifyDataSetChanged();
                    amount.setText(Double.toString(totalExpense(expense)));
                }
                break;
            }
        }
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {

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
                    Log.i("day", Integer.toString(arg3));
                   //expense.clear();
                    Log.i("expenses1",
                            expense.toString());
                    expense = db.getExpense(day, month+1, year);
                    Log.i("expenses2",
                            expense.toString());

                    recyclerView(expense);
                    amount.setText(Double.toString(totalExpense(expense)));
                 //   mAdapter.notifyDataSetChanged();
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
      //  expense = db.getExpense(day, month+1, year);
       // mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.expense) {
            // Handle the camera action
        }
        else if (id == R.id.lendings){
            Intent i = new Intent(MainActivity.this, LendingActivity.class);
            startActivity(i);

        } else if (id == R.id.budget) {
            Intent i = new Intent(MainActivity.this, BudgetActivity.class);
            startActivity(i);

        } else if (id == R.id.report) {

            Intent i = new Intent(MainActivity.this, ReportActivity.class);
            startActivity(i);

        } else if (id == R.id.settings) {

        } else if (id == R.id.about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public double totalExpense(List<ExpenseDetail> expense){
        int i = 0;
        double amount=0;
        while(i<expense.size()) {

            ExpenseDetail e = expense.get(i);
            amount += e.getAmount();
            ++i;
        }
        return (amount);
    }


    @Override
    public void onResume(){
        super.onResume();

        navigationView.getMenu().getItem(0).setChecked(true);
        //wallet.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)-db.getThisMonth(month+1,year)))));



        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        expense = db.getExpense(day, month+1, year);

        mAdapter.notifyDataSetChanged();

        sharedpreferences = getSharedPreferences("budget", Context.MODE_PRIVATE);
        numberFormat = new DecimalFormat("#.00");
        bdgt= sharedpreferences.getString("budget","0.0");
        wallet.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)-db.getThisMonth(month+1,year)))));

    }


}
