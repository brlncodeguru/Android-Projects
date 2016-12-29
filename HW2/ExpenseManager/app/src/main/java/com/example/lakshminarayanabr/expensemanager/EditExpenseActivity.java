package com.example.lakshminarayanabr.expensemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.StringTokenizer;

public class EditExpenseActivity extends AppCompatActivity {

    EditText editTxtExpenseName, editTextExpenseAmount, editTextExpenseDate;

    Button btnSave,btnCancel,btnSelect;

    ImageView imgCalender, imgReceipt;

    Spinner spinner;


    Boolean isImageEnabled;

    ArrayList<Expense> expenseList=new ArrayList<Expense>();
        Expense expSown;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        if(getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAYLIST)!=null)
        {
            expenseList=getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_ARRAYLIST);

        }


        {
            isImageEnabled=getIntent().getExtras().getBoolean(MainActivity.IMAGE_ENABLE);

        }


        editTxtExpenseName=(EditText)findViewById(R.id.editTextExpenseName);
        editTextExpenseAmount=(EditText)findViewById(R.id.editTextAmount);
        editTextExpenseDate=(EditText)findViewById(R.id.editTextDate);

        imgCalender=(ImageView)findViewById(R.id.imageViewCalendar);
        imgReceipt=(ImageView)findViewById(R.id.imageViewReceipt);

        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stringarray_ExpenseCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Category");

        spinner.setAdapter(adapter);
        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(EditExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(year,monthOfYear,dayOfMonth);
                        final int mYear = c.get(Calendar.YEAR);
                        final int mMonth = c.get(Calendar.MONTH);
                        final int mDay = c.get(Calendar.DAY_OF_MONTH);
                        if(dayOfMonth<10&&monthOfYear+1<10)
                        {
                            editTextExpenseDate.setText(new StringBuilder()
                                    // Month is 0 based so add 1
                                    .append("0").append(mMonth + 1).append("/").append("0").append(mDay).append("/")
                                    .append(mYear).toString());
                        }
                        else if(monthOfYear+1<10)
                        {
                            editTextExpenseDate.setText(new StringBuilder()
                                    // Month is 0 based so add 1
                                    .append("0").append(mMonth + 1).append("/").append(mDay).append("/")
                                    .append(mYear).toString());
                        }
                        else if(dayOfMonth<10)
                        {
                            editTextExpenseDate.setText(new StringBuilder()
                                    // Month is 0 based so add 1
                                    .append(mMonth + 1).append("/").append("0").append(mDay).append("/")
                                    .append(mYear).toString());
                        }


                        else
                        {
                            editTextExpenseDate.setText(new StringBuilder()
                                    // Month is 0 based so add 1
                                    .append(mMonth + 1).append("/").append(mDay).append("/")
                                    .append(mYear).toString());
                        }


                    }
                },mYear,mMonth,mDay);

                dialog.show();


//                DatePickerDialog dialog = new DatePickerDialog


            }
        });

        imgReceipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isImageEnabled)
                {
                    AlertDialog.Builder getImageDialog=new AlertDialog.Builder(EditExpenseActivity.this);
                    getImageDialog.setTitle("Choose Image from");

                    final CharSequence[] dialogItems={getResources().getString(R.string.string_Gallery)};
                    getImageDialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                        if(which==1)
//                        {
//                            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
//
//                        }
                            if(which==0)
                            {
                                Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(i, AddExpenseActivity.GALLERY_PIC_REQUEST);


                            }
                            dialog.dismiss();
                        }
                    }).show();

                }




            }
        });
        btnSave=(Button)findViewById(R.id.buttonSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expSown.imgURI!=null)
                {
                    String expName=editTxtExpenseName.getText().toString();



                    if(expName.length()>0&& editTextExpenseAmount.getText().toString().length()>0 && checkDateFormat(editTextExpenseDate.getText().toString()))
                    {
                        Double expAmount=Double.valueOf(editTextExpenseAmount.getText().toString());

                        String date=editTextExpenseDate.getText().toString();

                        String category=spinner.getSelectedItem().toString();

                        Expense objExpense=new Expense();
                        objExpense.expenseName=expName;
                        objExpense.expenseDate=date;
                        objExpense.amount=expAmount;
                        objExpense.category=category;
                        if(expSown.imgURI!=null)
                            objExpense.imgURI=expSown.imgURI;

                        expenseList.set(expenseList.indexOf(expSown),objExpense);


                        Intent intExpense=new Intent();
                        intExpense.putParcelableArrayListExtra(MainActivity.EXPENSE_ARRAYLIST,expenseList);

                        setResult(Activity.RESULT_OK,intExpense);

                        finish();



                    }

                    else
                    {

                        if(editTxtExpenseName.getText().toString().length()<=0)
                        {
                            Toast.makeText(EditExpenseActivity.this,"Enter Expense Name",Toast.LENGTH_LONG).show();

                        }
                        else if(editTextExpenseAmount.getText().toString().length()<=0)
                        {
                            Toast.makeText(EditExpenseActivity.this,"Enter Amount Information(Number or Decimal)",Toast.LENGTH_LONG).show();


                        }
                        else if(editTextExpenseDate.getText().toString().length()<=0)
                        {
                            Toast.makeText(EditExpenseActivity.this,"Enter Date Information",Toast.LENGTH_LONG).show();


                        }
                        else if(!checkDateFormat(editTextExpenseDate.getText().toString()))
                        {
                            Toast.makeText(EditExpenseActivity.this,"Enter Valid Date in MM/DD/YYYY format",Toast.LENGTH_LONG).show();


                        }
                        else
                            Toast.makeText(EditExpenseActivity.this,"Enter Valid Information",Toast.LENGTH_LONG).show();
                    }

                }
                else
                {
                    Toast.makeText(EditExpenseActivity.this,"No Expense Selected to Update",Toast.LENGTH_LONG).show();
                }

            }
        });

        btnCancel=(Button)findViewById(R.id.buttonCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSelect=(Button)findViewById(R.id.buttonSelectExpense);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expenseList.size()>0)
                {
                    final ArrayList<String> expNames=new ArrayList<String>();
                    for(Expense exp : expenseList)
                    {
                        expNames.add(exp.expenseName);

                    }
                    Collections.sort(expNames);

                    final AlertDialog.Builder dialogBuilder=new AlertDialog.Builder(EditExpenseActivity.this);
                    dialogBuilder.setCancelable(false);
                    dialogBuilder.setItems( expNames.toArray(new String[expenseList.size()]), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for(Expense exp : expenseList)
                            {
                                if(expNames.get(which)==exp.expenseName)
                                {
                                    index=expenseList.indexOf(exp);
                                }
                            }

                            if(index>=0&&index<expenseList.size())
                            {
                                expSown=expenseList.get(index);

                                editTxtExpenseName.setText(expSown.expenseName);
                                editTextExpenseAmount.setText(expSown.amount.toString());
                                editTextExpenseDate.setText(expSown.expenseDate);

                                ArrayAdapter<CharSequence> arrSpinner=(ArrayAdapter<CharSequence>)spinner.getAdapter();
                                int position=arrSpinner.getPosition(expSown.category);


                                spinner.setSelection(position);

                                if(expSown.imgURI!=null)
                                {
                                    try {

                                        Uri imageUri=Uri.parse(expSown.imgURI);
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(EditExpenseActivity.this.getContentResolver(), Uri.parse(expSown.imgURI));
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
                    Toast.makeText(EditExpenseActivity.this,"Empty Expense List",Toast.LENGTH_LONG).show();



            }
        });





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AddExpenseActivity.RESULT_OK) {
            if (requestCode == AddExpenseActivity.CAMERA_PIC_REQUEST) {
//                File f = new File(Environment.getExternalStorageDirectory()
//                        .toString());
//                for (File temp : f.listFiles()) {
//                    if (temp.getName().equals("temp.jpg")) {
//                        f = temp;
//                        break;
//                    }
//                }
//                try {
//                    Bitmap bm;
//                    BitmapFactory.Options btmapOptions = new BitmapFactory.Options();
//                    btmapOptions.inSampleSize = 2;
//                    bm = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                            btmapOptions);
//
//                    // bm = Bitmap.createScaledBitmap(bm, 70, 70, true);
//                    imgReceipt.setImageBitmap(bm);
//
//                    String path = android.os.Environment
//                            .getExternalStorageDirectory()
//                            + File.separator
//                            + "test";
//                    f.delete();
//                    OutputStream fOut = null;
//                    File file = new File(path, String.valueOf(System
//                            .currentTimeMillis()) + ".jpg");
//                    fOut = new FileOutputStream(file);
//                    bm.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
//                    fOut.flush();
//                    fOut.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            } else if (requestCode == AddExpenseActivity.GALLERY_PIC_REQUEST) {
                Uri selectedImageUri = data.getData();

                expSown.imgURI=selectedImageUri.toString();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    imgReceipt.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }


    }
    public String getPath(Uri uri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    public boolean checkDateFormat(String inputDate)
    {
        if(inputDate == null){
            return false;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);

        try {

            //if not valid, it will throw ParseException
            Date date = sdf.parse(inputDate);
            System.out.println(date);

        } catch (ParseException e) {

            e.printStackTrace();
            return false;
        }

        return true;




    }

}
