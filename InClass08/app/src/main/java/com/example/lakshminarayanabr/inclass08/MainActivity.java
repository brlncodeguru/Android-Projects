package com.example.lakshminarayanabr.inclass08;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseFragment.OnFragmentInteractionListener,AddExpense.OnFragmentInteractionListener,howExpenseFragment.OnFragmentInteractionListener {
    @Override
    protected void onResume() {
        super.onResume();
//        ExpenseFragment f=(ExpenseFragment)getSupportFragmentManager().findFragmentByTag("expenseFragment");
//        expenseList=f.resetListView(expenseList);
    }

    ArrayList<Expense> expenseList;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseList=new ArrayList<>();
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.relLayoutContainer,new ExpenseFragment(),"expenseFragment").commit();


    }

    @Override
    public void goToAddExpenseFragment() {

        getSupportFragmentManager().beginTransaction().replace(R.id.relLayoutContainer,new AddExpense(),"addexpensefragment").addToBackStack(null).commit();


    }

    @Override
    public void goToShowExpenseFragment(Expense expense) {
        getSupportFragmentManager().beginTransaction().replace(R.id.relLayoutContainer,new howExpenseFragment(),"showexpensefragment").addToBackStack(null).commit();



    }

    @Override
    public void onBackPressed() {

        if(getSupportFragmentManager().getBackStackEntryCount()>0){
        getSupportFragmentManager().popBackStack();
          }else{
          super.onBackPressed();
          }

    }

    @Override
    public ArrayList<Expense> addExpenseandGoToExpenseFragment(Expense expense) {

        if(expense!=null)
        {
         expenseList.add(expense);

//            ExpenseFragment f=(ExpenseFragment)getSupportFragmentManager().findFragmentByTag("expenseFragment");
//            expenseList=f.resetListView(expenseList);
           goToExpenseFragment();
        }
        return expenseList;
    }

    @Override
    public void goToExpenseFragment() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.relLayoutContainer,new ExpenseFragment(),"expenseFragment").addToBackStack(null).commit();
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }
        else
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.relLayoutContainer,new ExpenseFragment(),"expenseFragment").commit();

        }
    }
}
