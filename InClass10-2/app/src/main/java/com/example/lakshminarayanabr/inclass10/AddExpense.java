package com.example.lakshminarayanabr.inclass10;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpense.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AddExpense extends Fragment {
    ArrayList<Expense> expenses;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final EditText editExpenseName=(EditText)getActivity().findViewById(R.id.editTextExpenseName);
        final EditText editExpenseAmount=(EditText)getActivity().findViewById(R.id.editTextAmount);

        final Spinner spinner = (Spinner) getActivity().findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.stringarray_ExpenseCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Category");



        spinner.setAdapter(adapter);

        Button btnSubmit=(Button)getActivity().findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editExpenseName.getText().toString().length()<=0)
                {
                    Toast.makeText(getActivity(),"Enter the name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(editExpenseAmount.getText().toString().length()<=0)
                {
                    Toast.makeText(getActivity(),"Enter the Expense Amount",Toast.LENGTH_LONG).show();

                    return;
                }

                Expense exp=new Expense();
                exp.name=editExpenseName.getText().toString();
                exp.amount=editExpenseAmount.getText().toString();
                exp.category=spinner.getSelectedItem().toString();
                Calendar c=Calendar.getInstance();
                StringBuilder sb=new StringBuilder();
                sb.append(c.get(Calendar.MONTH)+"/"+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.YEAR));
                exp.date=sb.toString();




                expenses=mListener.addExpenseandGoToExpenseFragment(exp);






            }
        });

        Button btnCancel=(Button)getActivity().findViewById(R.id.buttonCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goToExpenseFragment();

            }
        });


    }

    private OnFragmentInteractionListener mListener;

    public AddExpense() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment




        return inflater.inflate(R.layout.fragment_add_expense, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
        }
    }

    @Override
    public void onAttach(Context context) {
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
        ArrayList<Expense> addExpenseandGoToExpenseFragment(Expense expense);

        void goToExpenseFragment();

    }}
