package com.example.lakshminarayanabr.expensemanager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final static int REQ_ADD_CODE = 101;
    final static int REQ_Edit_CODE = 102;
    final static int REQ_DELETE_CODE = 103;

    boolean isImageEnabled;

    final static  int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=1000;

    final static String EXPENSE_ARRAYLIST = "Expense_Arraylist";
    final static String IMAGE_ENABLE = "Image_Enable";




    Button btnAdd, btnEdit, btnDelete, btnShow, btnFinish;

    ArrayList<Expense> expenseList = new ArrayList<Expense>(
    );

//    if (checkSelfPermission(MainActivity,Manifest.permission.READ_EXTERNAL_STORAGE)
//    != PackageManager.) {
//
//        // Should we show an explanation?
//        if (shouldShowRequestPermissionRationale(
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            // Explain to the user why we need to read the contacts
//        }
//
//        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
//
//        // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
//        // app-defined int constant
//
//        return;
//    }
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.buttonAddExpense);
        btnEdit = (Button) findViewById(R.id.buttonEditExpense);
        btnDelete = (Button) findViewById(R.id.buttonDeleteExpense);
        btnShow = (Button) findViewById(R.id.buttonShowExpenses);
        btnFinish = (Button) findViewById(R.id.buttonFinish);

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE )!=PackageManager.PERMISSION_GRANTED)
        {


        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        }
        else
        {
            isImageEnabled=true;
        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intAddExp = new Intent(MainActivity.this, AddExpenseActivity.class);
                intAddExp.putParcelableArrayListExtra(EXPENSE_ARRAYLIST, expenseList);
                intAddExp.putExtra(IMAGE_ENABLE,isImageEnabled);
                startActivityForResult(intAddExp, REQ_ADD_CODE);
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intEditExp = new Intent(MainActivity.this, EditExpenseActivity.class);
                intEditExp.putExtra(EXPENSE_ARRAYLIST, expenseList);
                intEditExp.putExtra(IMAGE_ENABLE,isImageEnabled);
                startActivityForResult(intEditExp, REQ_Edit_CODE);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intDeleteExp = new Intent(MainActivity.this, DeleteExpenseActivity.class);
                intDeleteExp.putExtra(EXPENSE_ARRAYLIST, expenseList);

                startActivityForResult(intDeleteExp, REQ_DELETE_CODE);
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(expenseList.size()>0)
                {
                    Intent intShowExp = new Intent(MainActivity.this, ShowExpenseActivity.class);
                    intShowExp.putParcelableArrayListExtra(EXPENSE_ARRAYLIST, expenseList);

                    startActivity(intShowExp);
                }
                else
                    Toast.makeText(MainActivity.this,"Empty Expense List",Toast.LENGTH_LONG).show();



            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_ADD_CODE) {

            if (data != null) {
                expenseList = data.getExtras().getParcelableArrayList(EXPENSE_ARRAYLIST);

            }


        } else if (requestCode == REQ_Edit_CODE) {
            if (data != null) {
                expenseList = data.getExtras().getParcelableArrayList(EXPENSE_ARRAYLIST);

            }

        } else if (requestCode == REQ_DELETE_CODE) {
            if (data != null) {
                expenseList = data.getExtras().getParcelableArrayList(EXPENSE_ARRAYLIST);

            }


        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lakshminarayanabr.expensemanager/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    isImageEnabled=true;


                } else {
                isImageEnabled=false;
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.lakshminarayanabr.expensemanager/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
