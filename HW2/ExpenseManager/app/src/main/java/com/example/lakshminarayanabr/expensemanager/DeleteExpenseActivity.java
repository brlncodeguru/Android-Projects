package com.example.lakshminarayanabr.expensemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class DeleteExpenseActivity extends AppCompatActivity {
    EditText editTxtExpenseName, editTextExpenseAmount, editTextExpenseDate;

    Button btnDelete, btnCancel, btnSelect;

    ImageView imgCalender, imgReceipt;

    Spinner spinner;


    Boolean isImageEnabled;

    ArrayList<Expense> expenseList = new ArrayList<Expense>();
    Expense expSown;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_expense);

        if (getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAYLIST) != null) {
            expenseList = getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_ARRAYLIST);

        }



        editTxtExpenseName = (EditText) findViewById(R.id.editTextExpenseName);
        editTxtExpenseName.setEnabled(false);
        editTextExpenseAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextExpenseAmount.setEnabled(false);

        editTextExpenseDate = (EditText) findViewById(R.id.editTextDate);
        editTextExpenseDate.setEnabled(false);


        imgCalender = (ImageView) findViewById(R.id.imageViewCalendar);
        imgReceipt = (ImageView) findViewById(R.id.imageViewReceipt);

        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stringarray_ExpenseCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Category");

        spinner.setAdapter(adapter);
        spinner.setEnabled(false);

        btnDelete = (Button) findViewById(R.id.buttonDelete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(expSown!=null)
            {
              if(expenseList.remove(expSown))
             {
                  Intent intDelete=new Intent();
                 intDelete.putParcelableArrayListExtra(MainActivity.EXPENSE_ARRAYLIST,expenseList);
                setResult(Activity.RESULT_OK,intDelete);

             }
}
                else
                Toast.makeText(DeleteExpenseActivity.this,"No Expense to Delete",Toast.LENGTH_LONG).show();



                finish();






            }
        });

        btnCancel = (Button) findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSelect = (Button) findViewById(R.id.buttonSelectExpense);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expenseList.size()>0)
                {
                    final ArrayList<String> expNames = new ArrayList<String>();
                    for (Expense exp : expenseList) {
                        expNames.add(exp.expenseName);

                    }
                    Collections.sort(expNames);

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(DeleteExpenseActivity.this);
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setItems(expNames.toArray(new String[expenseList.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (Expense exp : expenseList) {
                                if (expNames.get(which) == exp.expenseName) {
                                    index = expenseList.indexOf(exp);
                                }
                            }

                            if (index >= 0 && index < expenseList.size()) {
                                expSown = expenseList.get(index);

                                editTxtExpenseName.setText(expSown.expenseName);
                                editTextExpenseAmount.setText(expSown.amount.toString());
                                editTextExpenseDate.setText(expSown.expenseDate);

                                ArrayAdapter<CharSequence> arrSpinner = (ArrayAdapter<CharSequence>) spinner.getAdapter();
                                int position = arrSpinner.getPosition(expSown.category);


                                spinner.setSelection(position);

                                if (expSown.imgURI != null) {
                                    try {

                                        Uri imageUri = Uri.parse(expSown.imgURI);
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(DeleteExpenseActivity.this.getContentResolver(), Uri.parse(expSown.imgURI));
                                        imgReceipt.setImageBitmap(bitmap);

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }


                            dialog.dismiss();
                        }
                    }).show();


                }
                else
                    Toast.makeText(DeleteExpenseActivity.this,"Empty Expense List",Toast.LENGTH_LONG).show();


            }
        });


    }
}

