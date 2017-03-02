package com.example.harsh.mywallet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayList<String> expense = new ArrayList<>();
    private EditText category;
    private EditText detail;
    private EditText amount;
    private Button btn;
    private String det = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

       // category = (EditText) findViewById(R.id.category_in);
        detail = (EditText) findViewById(R.id.detail_in);
        amount = (EditText) findViewById(R.id.amount_in);
        btn = (Button)findViewById(R.id.save);

        Spinner dropdown = (Spinner)findViewById(R.id.spinner1);
        String[] items = new String[]{"Food", "Entertainment", "Transport", "Social", "Bills", "Education", "Medical", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(this);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //expense.add(category.getText().toString());
                det = detail.getText().toString();
                expense.add(1,det);
                expense.add(2,amount.getText().toString());

                Intent resultIntent = new Intent();
                resultIntent.putStringArrayListExtra("expense", expense);
                setResult(ExpenseActivity.RESULT_OK, resultIntent);
                finish();
            }

        });
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        expense.add(0,(parent.getItemAtPosition(pos)).toString());
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}

