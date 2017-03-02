package com.example.harsh.mywallet;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class BudgetActivity extends AppCompatActivity {

    EditText budget;
    TextView daily,weekly,yearly;
    String bdgt;
    DecimalFormat numberFormat = new DecimalFormat("#.00");

    private SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        sharedpreferences = getSharedPreferences("budget", Context.MODE_PRIVATE);

        bdgt= sharedpreferences.getString("budget","0.0");

        daily = (TextView) findViewById(R.id.edDaily);
        weekly = (TextView)findViewById(R.id.edWeekly);
        yearly = (TextView)findViewById(R.id.edYearly);

        budget = (EditText)findViewById(R.id.edMonthly);
       // bdgt = budget.getText().toString();

        budget.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)))));
        daily.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)/30))));
        weekly.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)/30*7))));
        yearly.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)*12))));





    }

    public void setBudget(View v){
        bdgt = budget.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("budget",budget.getText().toString());
        editor.commit();

        daily.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)/30))));
        weekly.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)/30*7))));
        yearly.setText(Double.toString(Double.valueOf(numberFormat.format(Double.parseDouble(bdgt)*12))));

        Toast.makeText(getApplicationContext(), "Done!",Toast.LENGTH_SHORT).show();

    }
}
