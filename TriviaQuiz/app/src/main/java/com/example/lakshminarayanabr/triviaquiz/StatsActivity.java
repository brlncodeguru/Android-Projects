package com.example.lakshminarayanabr.triviaquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {
    TextView txtPercent;
    ProgressBar pgBar;
    Button btntryAgain,btnQuit;

    int correctAnswers;
    int numberOfQues;

    ArrayList<Question> quesList=new ArrayList<Question>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        if(getIntent().getParcelableArrayListExtra(MainActivity.QUESTIONS)!=null)
        {
            quesList=getIntent().getParcelableArrayListExtra(MainActivity.QUESTIONS);
            numberOfQues=quesList.size();

        }
        correctAnswers=getIntent().getIntExtra(TriviaActivity.NUMCORRECT,0);

        pgBar=(ProgressBar)findViewById(R.id.progressBarResult);
        pgBar.setMax(100);
        pgBar.setProgress(correctAnswers*100/numberOfQues);

        txtPercent=(TextView)findViewById(R.id.textViewPercent);
        txtPercent.setText(pgBar.getProgress()+"%");

        btntryAgain=(Button)findViewById(R.id.buttonTryAgain);
        btntryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent triviaActivity=new Intent(StatsActivity.this,TriviaActivity.class);
                triviaActivity.putParcelableArrayListExtra(MainActivity.QUESTIONS,quesList);
                startActivity(triviaActivity);
                finish();
            }
        });

        btnQuit=(Button)findViewById(R.id.buttonQuit);
        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity=new Intent(StatsActivity.this,MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });



    }
}
