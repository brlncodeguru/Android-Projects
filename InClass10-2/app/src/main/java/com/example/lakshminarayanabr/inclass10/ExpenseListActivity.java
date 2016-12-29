package com.example.lakshminarayanabr.inclass10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ExpenseListActivity extends AppCompatActivity implements ExpenseFragment.OnFragmentInteractionListener,AddExpense.OnFragmentInteractionListener,howExpenseFragment.OnFragmentInteractionListener  {


    ArrayList<Expense> expenseList;
    int position;
    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();

    DatabaseReference mExpenseRef=mRootRef.child("Expenses");



    FirebaseUser user;

    @Override
    protected void onStop() {
        super.onStop();
        user=null;

    }

    @Override
    protected void onStart() {
        super.onStart();
        user= FirebaseAuth.getInstance().getCurrentUser();

        mExpenseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                Expense expense=dataSnapshot.getValue(Expense.class);
                if(expense.userid.equals(user.getUid())&&!expenseList.contains(expense))
                expenseList.add(expense);

                Log.d("DemoE",expenseList.toString());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                Expense expense=dataSnapshot.getValue(Expense.class);
                if(expense.userid.equals(user.getUid())&&expenseList.contains(expense))
                    expenseList.remove(expense);
                Log.d("DemoE",expenseList.toString());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







            }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_list);
        user= FirebaseAuth.getInstance().getCurrentUser();


        expenseList=new ArrayList<>();

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
            FirebaseAuth.getInstance().signOut();

        }

    }

    @Override
    public ArrayList<Expense> addExpenseandGoToExpenseFragment(Expense expense) {

        if(expense!=null)
        {

            expense.userid=user.getUid();


          DatabaseReference  reference= mExpenseRef.push();
            expense.expenseID=reference.getKey();
                  reference.setValue(expense);



            goToExpenseFragment();




//            ExpenseFragment f=(ExpenseFragment)getSupportFragmentManager().findFragmentByTag("expenseFragment");
//            expenseList=f.resetListView(expenseList);

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
