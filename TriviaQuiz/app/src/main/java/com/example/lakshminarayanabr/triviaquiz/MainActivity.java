package com.example.lakshminarayanabr.triviaquiz;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ProgressBar pgBar;
    TextView txtVieHeading,txtLoading;
    RelativeLayout relLayout;
    ImageView imgVwTrivia;
    Button btnStartTrivia,btnQuit;

    ArrayList<Question> questionList=new ArrayList<Question>();

    final static String QUESTIONS="QUESTIONS";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url="http://dev.theappsdr.com/apis/trivia_json/index.php";


        relLayout=(RelativeLayout)findViewById(R.id.relativeLayoutMain);

        txtVieHeading= (TextView) findViewById(R.id.textViewHeading);
        pgBar=(ProgressBar)findViewById(R.id.progressBarLoading);
        txtLoading=(TextView)findViewById(R.id.textViewLoadingMessage);
        imgVwTrivia=(ImageView)findViewById(R.id.ImgTrivia);
        btnStartTrivia=(Button)findViewById(R.id.buttonStartTrivia);
        btnQuit=(Button)findViewById(R.id.buttonQuit);







//        pgBar=new ProgressBar(this,null,R.attr.spinnerStyle);
//        pgBar.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        RelativeLayout.LayoutParams lparams= (RelativeLayout.LayoutParams) pgBar.getLayoutParams();
//        lparams.addRule(RelativeLayout.BELOW,txtVieHeading.getId());
//      lparams.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.CENTER_VERTICAL);
//        pgBar.setLayoutParams(lparams);
        pgBar.setMax(100);
       pgBar.setVisibility(View.INVISIBLE);
        txtLoading.setVisibility(View.INVISIBLE);
        btnStartTrivia.setEnabled(false);
        imgVwTrivia.setVisibility(View.INVISIBLE);

        btnStartTrivia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intTrivia=new Intent(MainActivity.this,com.example.lakshminarayanabr.triviaquiz.TriviaActivity.class);
                intTrivia.putParcelableArrayListExtra(QUESTIONS,questionList);
                startActivity(intTrivia);
                finish();

            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                System.exit(0);

               finish();

            }
        });








        if(isConnected())
            new QuestionsJSONDownloader(this,"http://dev.theappsdr.com/apis/trivia_json/index.php").execute(url);
        else
            Toast.makeText(MainActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();

    }
    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ntinfo = cm.getActiveNetworkInfo();
        if (ntinfo != null && ntinfo.isConnected()) {
            return true;
        }
        return false;
    }
}
