package com.example.harsh.mywallet.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harsh.mywallet.R;
import com.example.harsh.mywallet.models.ExpenseDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh on 16-01-2017.
 */

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>  {
    private List<ExpenseDetail> expense = new ArrayList<>();
    private int rowLayout;
    private Context context;



    public  class ExpenseViewHolder extends RecyclerView.ViewHolder   {

        TextView category;

        TextView detail;

        TextView amount;

        ImageView image;




        public ExpenseViewHolder(View v) {
            super(v);

            category = (TextView) v.findViewById(R.id.category);
            detail =(TextView) v.findViewById(R.id.detail);
            amount = (TextView) v.findViewById(R.id.amount);
            image = (ImageView) v.findViewById(R.id.catImage);

        }



    }





    public ExpenseAdapter(List<ExpenseDetail> expense, int rowLayout, Context context) {
        this.expense = expense;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ExpenseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, final int position) {

        holder.category.setText(expense.get(position).getCategory());
        holder.detail.setText(expense.get(position).getDetails());
        holder.amount.setText("Rs. "+ Double.toString(expense.get(position).getAmount()));
        switch (expense.get(position).getCategory()){
            case "Food" : holder.image.setImageResource(R.drawable.food);
                            break;
            case "Transport" : holder.image.setImageResource(R.drawable.trans);
                                break;
            case "Medical" : holder.image.setImageResource(R.drawable.med1);
                                break;
            case " Bills" : holder.image.setImageResource(R.drawable.bill);
                break;
            case "Entertainment" : holder.image.setImageResource(R.drawable.ent);
                break;
            case "Other" : holder.image.setImageResource(R.drawable.other);
                break;
            case "Education" : holder.image.setImageResource(R.drawable.edu);
                break;
            case "Social" : holder.image.setImageResource(R.drawable.social);
                break;
        }

    }



    @Override
    public int getItemCount() {
        return expense.size() ;
    }
}

