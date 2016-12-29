package com.example.lakshminarayanabr.inclass08;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
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

        MainActivity activity=(MainActivity)getActivity();
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

                    expenses.remove(position);
                    MainActivity activity=(MainActivity)getActivity();
                    activity.expenseList=expenses;

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

                    return false;
                }
            });
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MainActivity activity=(MainActivity)getActivity();
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
