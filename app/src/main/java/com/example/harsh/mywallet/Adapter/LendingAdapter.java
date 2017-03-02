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
import com.example.harsh.mywallet.models.Lending;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Harsh on 25-01-2017.
 */

public class LendingAdapter extends RecyclerView.Adapter<LendingAdapter.LendingViewHolder>{
       // private List<ExpenseDetail> expense = new ArrayList<>();
        private int rowLayout;
        private Context context;
    private List<Lending> lendings;



        public  class LendingViewHolder extends RecyclerView.ViewHolder   {

            TextView to;

            TextView lent;

            TextView bal;






            public LendingViewHolder(View v) {
                super(v);

                to = (TextView) v.findViewById(R.id.to);
                lent =(TextView) v.findViewById(R.id.lent);
                bal = (TextView) v.findViewById(R.id.balance);

            }



        }





        public LendingAdapter(List<Lending> lendings, int rowLayout, Context context) {
            this.lendings = lendings;
            this.rowLayout = rowLayout;
            this.context = context;
        }

        @Override
        public com.example.harsh.mywallet.Adapter.LendingAdapter.LendingViewHolder onCreateViewHolder(ViewGroup parent,
                                                                                                      int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
            return new com.example.harsh.mywallet.Adapter.LendingAdapter.LendingViewHolder(view);
        }


        @Override
        public void onBindViewHolder(LendingViewHolder holder, final int position) {

            holder.to.setText(lendings.get(position).getTo());
            holder.lent.setText(Double.toString(lendings.get(position).getAmount()));
            holder.bal.setText("Rs. "+ Double.toString(lendings.get(position).getAmount() - lendings.get(position).getPaid()) );


        }



        @Override
        public int getItemCount() {
            return lendings.size() ;
        }



}
