package com.example.lakshminarayanabr.triviaquiz;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    ArrayList<Question> questionList=new ArrayList<Question>();

    int index;
    RelativeLayout relLayout;

    TextView txtVw,txtVwTimer,txtVwQuestion;

    ScrollView scrllVwChoices;
     LinearLayout linlayout;

    Button btnNext,btnQuit;

    RadioGroup rgChoices;
    RadioButton radioButton;
    Question ques;

    ProgressBar pgBar;
    TextView txtLoading;
    ImageView imgVw;
    int correctAnswers=0;

    CountDownTimer timer = null;

    public boolean isPaused=false;

    public long timeRemaining;


    public static String NUMCORRECT="NumberOfCorrect";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Demo","Trivia");



        if(getIntent().getParcelableArrayListExtra(MainActivity.QUESTIONS)!=null)
        {
            questionList=getIntent().getParcelableArrayListExtra(MainActivity.QUESTIONS);
            index=0;
           ques=questionList.get(index);
           relLayout=new RelativeLayout(this);
            relLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

            //question number
            txtVw=new TextView(this);
            RelativeLayout.LayoutParams lparams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            lparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lparams.leftMargin=30;
            lparams.topMargin=30;
            txtVw.setLayoutParams(lparams);
            txtVw.setId(R.id.txtViewQno);
            txtVw.setText("Q"+ques.id);
            relLayout.addView(txtVw);

            //Timer
            txtVwTimer=new TextView(this);
            RelativeLayout.LayoutParams lTimerparams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            lTimerparams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lTimerparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lTimerparams.rightMargin=30;
            lTimerparams.topMargin=30;
            txtVwTimer.setLayoutParams(lTimerparams);
            txtVw.setId(R.id.txtViewTimer);
            txtVwTimer.setText("00:00");
            relLayout.addView(txtVwTimer);


            timeRemaining=2*60*1000;


            timer =new CountDownTimer(timeRemaining,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(isPaused)
                    {
                        cancel();

                    }
                    else
                    {
                        txtVwTimer.setText(millisUntilFinished/1000 +"seconds");

                        timeRemaining=millisUntilFinished;


                    }


                }

                @Override
                public void onFinish() {
                    startStatsActivity(correctAnswers,questionList);

                }
            }.start();


            // ImageView
            imgVw=new ImageView(this);
            RelativeLayout.LayoutParams lImageParams=new RelativeLayout.LayoutParams(300,300);
            lImageParams.addRule(RelativeLayout.BELOW,txtVw.getId());
            lImageParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            imgVw.setId(R.id.imgViewQues);
            lImageParams.topMargin=20;
            imgVw.setLayoutParams(lImageParams);
            relLayout.addView(imgVw);
            imgVw.setImageResource(R.drawable.trivia);

            //pgBar
            pgBar=new ProgressBar(this);
            RelativeLayout.LayoutParams lpgBarparams=new RelativeLayout.LayoutParams(300,300);
            lpgBarparams.addRule(RelativeLayout.BELOW,txtVw.getId());
            lpgBarparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            pgBar.setId(R.id.progressBar);
            lpgBarparams.topMargin=20;
            pgBar.setLayoutParams(lpgBarparams);
            relLayout.addView(pgBar);


            txtLoading=new TextView(this);
            RelativeLayout.LayoutParams loadingParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            loadingParams.addRule(RelativeLayout.BELOW,pgBar.getId());
            loadingParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
            txtLoading.setLayoutParams(loadingParams);
            txtLoading.setText("Loading Image");
            relLayout.addView(txtLoading);









            // Question
            txtVwQuestion=new TextView(this);
            RelativeLayout.LayoutParams lQuestionParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            lQuestionParams.addRule(RelativeLayout.BELOW,imgVw.getId());
            lQuestionParams.topMargin=40;
            txtVwQuestion.setLayoutParams(lQuestionParams);
            txtVwQuestion.setId(R.id.txtViewQuestion);
            txtVwQuestion.setText(ques.questionText);
            relLayout.addView(txtVwQuestion);

            //ScrollView
            scrllVwChoices=new ScrollView(this);
            RelativeLayout.LayoutParams lscrollParams=new RelativeLayout.LayoutParams(800,800);
            lscrollParams.addRule(RelativeLayout.BELOW,txtVwQuestion.getId());
            lscrollParams.topMargin=10;
            scrllVwChoices.setLayoutParams(lscrollParams);
            scrllVwChoices.setId(R.id.scrollVwChoice);

            //add choices
            linlayout=new LinearLayout(this);
            LinearLayout.LayoutParams llParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            linlayout.setLayoutParams(llParams);
            linlayout.setId(R.id.linLayout);
//            addRadioButtons(ques,linlayout);

            rgChoices=new RadioGroup(this);
            LinearLayout.LayoutParams lrgParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            rgChoices.setLayoutParams(lrgParams);
            rgChoices.setId(ques.id);
            rgChoices.setOrientation(LinearLayout.VERTICAL);


            for(String choice : ques.choices )
            {
                RadioButton radioButton=new RadioButton(this);
                radioButton.setId(ques.choices.indexOf(choice)+1);
                LinearLayout.LayoutParams lrbParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                radioButton.setLayoutParams(lrbParams);
                radioButton.setText(choice);
                rgChoices.addView(radioButton);




            }
            linlayout.addView(rgChoices);
            scrllVwChoices.addView(linlayout);

            relLayout.addView(scrllVwChoices);







            //btnNext
            btnNext=new Button(this);
            RelativeLayout.LayoutParams btnNextparams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            btnNextparams.addRule(RelativeLayout.BELOW,scrllVwChoices.getId());
            btnNextparams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnNextparams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            btnNext.setLayoutParams(btnNextparams);
            btnNext.setText(R.string.string_Next);
            relLayout.addView(btnNext);

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Demo","Answer Choice is:"+ques.answerChoice+"CheckeditemID"+rgChoices.getCheckedRadioButtonId());

                    if(ques.answerChoice==rgChoices.getCheckedRadioButtonId())
                    {

                        correctAnswers+=1;
                    }
                    if(index==questionList.size()-1)
                    {
                        index=0;

                        startStatsActivity(correctAnswers,questionList);





                    }
                    else if(index<questionList.size()-1)
                    {
                        index+=1;
                        ques=questionList.get(index);
                        isPaused=true;
                        if(isConnected())
                            new ImageDonwnLoader(TriviaActivity.this).execute(ques.imageURL);
                        else
                            Toast.makeText(TriviaActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();


                        txtVw.setText("Q"+ques.id);
                        txtVwQuestion.setText(ques.questionText);

                        addRadioButtons(ques,linlayout);


                    }
                    Log.d("Demo","Correc Answrs:"+String.valueOf(correctAnswers));


                }
            });

            //btnQuit
            btnQuit=new Button(this);
            RelativeLayout.LayoutParams btnQuitParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            btnQuitParams.addRule(RelativeLayout.BELOW,scrllVwChoices.getId());
            btnQuitParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            btnQuitParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            btnQuit.setText(R.string.string_Quit);
            btnQuit.setLayoutParams(btnQuitParams);
            relLayout.addView(btnQuit);

            btnQuit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent mainActivity=new Intent(TriviaActivity.this,MainActivity.class);
                    startActivity(mainActivity);
                    finish();
                }
            });





















            setContentView(relLayout);
            isPaused=true;
            if(isConnected())
            new ImageDonwnLoader(this).execute(ques.imageURL);
            else
                Toast.makeText(TriviaActivity.this,"No Network Connection",Toast.LENGTH_LONG).show();




        }
        else
        {
            Toast.makeText(TriviaActivity.this,"No Questions Available",Toast.LENGTH_LONG).show();
        }

    }

    public void startStatsActivity(int correctAnswers,ArrayList<Question> questionList)
    {
        Intent statsActivity=new Intent(this,StatsActivity.class);
        statsActivity.putExtra(NUMCORRECT,correctAnswers);
        statsActivity.putParcelableArrayListExtra(MainActivity.QUESTIONS,questionList);
        startActivity(statsActivity);
        finish();

    }
    public void addRadioButtons(Question ques,LinearLayout linlayout)
    {

        ViewGroup layout = (ViewGroup) findViewById(R.id.scrollVwChoice);
        View command = layout.findViewById(R.id.linLayout);
        layout.removeView(command);


        linlayout=new LinearLayout(this);
        LinearLayout.LayoutParams llParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        linlayout.setLayoutParams(llParams);
        linlayout.setId(R.id.linLayout);
        rgChoices=new RadioGroup(this);
        LinearLayout.LayoutParams lrgParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        rgChoices.setLayoutParams(lrgParams);
        rgChoices.setId(ques.id);
        rgChoices.setOrientation(LinearLayout.VERTICAL);


        for(String choice : ques.choices )
        {
            RadioButton radioButton=new RadioButton(this);
            radioButton.setId(ques.choices.indexOf(choice)+1);
            LinearLayout.LayoutParams lrbParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(lrbParams);
            radioButton.setText(choice);
            rgChoices.addView(radioButton);



        }
        linlayout.addView(rgChoices);
        layout.addView(linlayout);

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
