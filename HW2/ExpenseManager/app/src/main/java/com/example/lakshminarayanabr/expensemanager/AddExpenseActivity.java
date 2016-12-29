package com.example.lakshminarayanabr.expensemanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
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

import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddExpenseActivity extends AppCompatActivity{

    static final  int CAMERA_PIC_REQUEST=100;
    static  final int GALLERY_PIC_REQUEST=101;

    EditText editTxtExpenseName, editTextExpenseAmount, editTextExpenseDate;

    Button btnAddExpense;

    ImageView imgCalender, imgReceipt;

    private DatePicker dpResult;

    Uri uriReceipt;


    static final int DATE_DIALOG_ID = 999;


    Spinner spinner;

    Boolean isImageEnabled;

    private int year;
    private int month;
    private int day;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    ArrayList<Expense> expenseList=new ArrayList<Expense>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        if(getIntent().getExtras().getSerializable(MainActivity.EXPENSE_ARRAYLIST)!=null)
        {
        expenseList=getIntent().getExtras().getParcelableArrayList(MainActivity.EXPENSE_ARRAYLIST);

        }


        {
            isImageEnabled=getIntent().getExtras().getBoolean(MainActivity.IMAGE_ENABLE);

        }




        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.stringarray_ExpenseCategories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setPrompt("Select Category");

        spinner.setAdapter(adapter);

        editTxtExpenseName = (EditText) findViewById(R.id.editTextExpenseName);

        editTextExpenseAmount = (EditText) findViewById(R.id.editTextAmount);

        editTextExpenseDate = (EditText) findViewById(R.id.editTextAEDate);


        btnAddExpense = (Button) findViewById(R.id.buttonAddExpense);


        imgCalender = (ImageView) findViewById(R.id.imageViewCalendar);

        imgReceipt = (ImageView) findViewById(R.id.imageViewReceipt);



        imgCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                    AlertDialog.Builder getImageDialog=new AlertDialog.Builder(AddExpenseActivity.this);
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
                                startActivityForResult(i, GALLERY_PIC_REQUEST);


                            }
                            dialog.dismiss();
                        }
                    }).show();

                }




            }
        });
        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

                    if(uriReceipt!=null)
                    objExpense.imgURI=uriReceipt.toString();

                    expenseList.add(objExpense);


                    Intent intExpense=new Intent();
                    intExpense.putParcelableArrayListExtra(MainActivity.EXPENSE_ARRAYLIST,expenseList);

                    setResult(Activity.RESULT_OK,intExpense);

                    finish();



                }

                else
                {

                    if(editTxtExpenseName.getText().toString().length()<=0)
                    {
                        Toast.makeText(AddExpenseActivity.this,"Enter Expense Name",Toast.LENGTH_LONG).show();

                    }
                    else if(editTextExpenseAmount.getText().toString().length()<=0)
                    {
                        Toast.makeText(AddExpenseActivity.this,"Enter Amount Information(Number or Decimal)",Toast.LENGTH_LONG).show();


                    }
                    else if(editTextExpenseDate.getText().toString().length()<=0)
                    {
                        Toast.makeText(AddExpenseActivity.this,"Enter Date Information",Toast.LENGTH_LONG).show();


                    }
                    else if(!checkDateFormat(editTextExpenseDate.getText().toString()))
                    {
                        Toast.makeText(AddExpenseActivity.this,"Enter Valid Date in MM/DD/YYYY format",Toast.LENGTH_LONG).show();


                    }
                    else
                    Toast.makeText(AddExpenseActivity.this,"Enter Valid Information",Toast.LENGTH_LONG).show();
                }






            }
        });






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == AddExpenseActivity.RESULT_OK) {
            if (requestCode == CAMERA_PIC_REQUEST) {
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
            } else if (requestCode == GALLERY_PIC_REQUEST) {
                Uri selectedImageUri = data.getData();

                uriReceipt=selectedImageUri;

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

//    @Override
//    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//
//        int mYear = year;
//        int mMonth = monthOfYear;
//        int mDay = dayOfMonth;
//        setDate(new StringBuilder()
//                // Month is 0 based so add 1
//                .append(mMonth + 1).append("/").append(mDay).append("/")
//                .append(mYear).append(" ").toString());
//
//
//    }
//    public void setDate(String Dte){
//
//        EditText editTextExpenseDate = (EditText) findViewById(R.id.editTextAEDate);
//
//        editTextExpenseDate.setText(Dte);
//    }
}
