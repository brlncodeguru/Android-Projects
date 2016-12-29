package com.example.lakshminarayanabr.inclass10;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lakshminarayanabr on 11/7/16.
 */

public class ExpenseAdapter extends ArrayAdapter<Expense> {
        Context mcontext;

        int resID;
        List<Expense> objects;

public ExpenseAdapter(Context context,int resource,List<Expense>objects){
        super(context,resource,objects);

        this.mcontext=context;
        this.resID=resource;
        this.objects=objects;
        }

@Override
public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null)
        {
        LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(mcontext.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(resID,parent,false);
        }

        Expense expense=objects.get(position);

        TextView txtView=(TextView)convertView.findViewById(R.id.textViewExpenseName);
        txtView.setText(expense.name);
        TextView txtAmount=(TextView)convertView.findViewById(R.id.textViewExpenseAmount);
        txtAmount.setText("$"+expense.amount);


        return convertView;
        }
}
