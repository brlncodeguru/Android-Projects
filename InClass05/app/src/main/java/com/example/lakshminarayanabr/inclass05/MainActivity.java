package com.example.lakshminarayanabr.inclass05;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;

    ArrayList<String> imageurl;
    private static int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new ProgressDialog(this);
        final String[] arr = {"UNCC","Android","Winter","Aurora","Wonders"};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnected();
        findViewById(R.id.buttonGO).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              index = 0 ;
                ((ImageButton)findViewById(R.id.imageButtonPrev)).setEnabled(true);
                ((ImageButton)findViewById(R.id.imageButtonNext)).setEnabled(true);

                if (! isConnected()){
                    Toast.makeText(MainActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();
                    return;

                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose a Keyword");

                builder.setCancelable(false);
                builder.setItems(R.array.keywords, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ((TextView) findViewById(R.id.textSearchKeyword)).setText(arr[which]);
                    new ShowImage(MainActivity.this).execute(arr[which]);

                    }
                });
                builder.create().show();

            }
        });
        findViewById(R.id.imageButtonNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (++index > imageurl.size()-1)
                {
                    index = 0;

                }
                setImage();
            }
        });
        findViewById(R.id.imageButtonPrev).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (--index < 0)
                {
                    index = imageurl.size()-1;

                }
                setImage();
            }
        });
    }
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ntinfo = cm.getActiveNetworkInfo();
        if (ntinfo != null && ntinfo.isConnected()) {
            return true;
        }
        return false;
    }

        public void setImage()
        {
            if(imageurl.size() == 1)
            {
                ((ImageButton)findViewById(R.id.imageButtonPrev)).setEnabled(false);
                ((ImageButton)findViewById(R.id.imageButtonNext)).setEnabled(false);

            }
            else if(imageurl.size()==0)
            {
                ((ImageButton)findViewById(R.id.imageButtonPrev)).setEnabled(false);
                ((ImageButton)findViewById(R.id.imageButtonNext)).setEnabled(false);
                Toast.makeText(MainActivity.this,"No Images Found" , Toast.LENGTH_LONG).show();

            }
            new CreateBitmap(MainActivity.this).execute(imageurl.get(index));
            Log.d("Demo",imageurl.toString());


        }
}
