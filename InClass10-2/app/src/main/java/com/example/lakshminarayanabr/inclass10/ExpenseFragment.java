package com.example.lakshminarayanabr.inclass10;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class ExpenseFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    ArrayList<Expense> expenses;
    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_expense, container, false);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView img= (ImageView) getActivity().findViewById(R.id.imageViewAdd);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToAddExpenseFragment();
            }
        });

        final ExpenseListActivity activity=(ExpenseListActivity) getActivity();
        if(activity.expenseList==null||activity.expenseList.size()==0)
        {


        }
        activity.mExpenseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               activity.expenseList.clear();

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Expense expense=postSnapshot.getValue(Expense.class);
                    if(expense.userid.equals(activity.user.getUid())&&!activity.expenseList.contains(expense))
                    {
                        Log.d("Demo",activity.expenseList.toString());
                        activity.expenseList.add(expense);
                    }
                }
                Log.d("Demo",activity.expenseList.toString());

                resetListView(activity.expenseList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        resetListView(activity.expenseList);



    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {

        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void goToAddExpenseFragment();
        void goToShowExpenseFragment(Expense expense);

    }

    public ArrayList<Expense> resetListView(final ArrayList<Expense> expensesList)
    {
        expenses=expensesList;

        if(expensesList.size()>0)
        {
            final TextView errorMessage=(TextView)getView().findViewById(R.id.textViewErrorMsg);
            final ListView lv=(ListView)getView().findViewById(R.id.listViewExpenses);
            final ExpenseAdapter adapter=new ExpenseAdapter(getActivity(),R.layout.itemrowlayout,expenses);
            lv.setAdapter(adapter);
            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    Expense exp=expenses.remove(position);







                    final ExpenseListActivity activity=(ExpenseListActivity) getActivity();
                    activity.mExpenseRef.child(exp.expenseID).removeValue(new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Toast.makeText(activity,"Expense Deleted",Toast.LENGTH_LONG).show();


                            adapter.notifyDataSetChanged();
                            if(expenses.size()>0)
                            {
                                errorMessage.setVisibility(View.INVISIBLE);
                                lv.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                errorMessage.setVisibility(View.VISIBLE);
                                lv.setVisibility(View.INVISIBLE);
                            }
                        }
                    });

//                    activity.mExpenseRef.removeValue();
//                    activity.expenseList=new ArrayList<Expense>();
//
//
//
//                    for(Expense exp :expenses)
//                    {
//                        activity.mExpenseRef.setValue(exp);
//
//
//
//
//                    }
//                    activity.mRootRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(DataSnapshot dataSnapshot) {
//                            for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                                Expense exp=postSnapshot.getValue(Expense.class);
//                                if(exp.userid.equals(activity.user.getUid()))
//                                {
//                                    activity.expenseList.add(exp);
//
//                                }
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//
//                        }
//                    });
//
//




                    return true;
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ExpenseListActivity activity=(ExpenseListActivity) getActivity();
                    activity.position=position;
                    Expense exp= expensesList.get(position);
                    mListener.goToShowExpenseFragment(exp);
                }
            });

            errorMessage.setVisibility(View.INVISIBLE);
            lv.setVisibility(View.VISIBLE);

        }
        else
        {
            TextView errorMessage=(TextView)getView().findViewById(R.id.textViewErrorMsg);
            ListView lv=(ListView)getView().findViewById(R.id.listViewExpenses);

            errorMessage.setVisibility(View.VISIBLE);
            lv.setVisibility(View.INVISIBLE);
        }
        return expenses;

    }

}
