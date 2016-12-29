package com.example.lakshminarayanabr.inclass03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Student stud=new Student();
    final static String STUDENT_KEY = "STUDENT";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText editTextName=(EditText)findViewById(R.id.editTextName);
        final EditText editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        final RadioGroup rgDepartment=(RadioGroup)findViewById(R.id.radioGroupDept);
        final RadioButton rbSIS=(RadioButton)findViewById(R.id.radioButtonSIS);
        final RadioButton rbCS=(RadioButton)findViewById(R.id.radioButtonCS);
        final RadioButton rbBIO=(RadioButton)findViewById(R.id.radioButtonBIO);
        final RadioButton rbOthers=(RadioButton)findViewById(R.id.radioButtonOthers);
        final SeekBar skBarMood=(SeekBar)findViewById(R.id.seekBarMood);
        Button btnSubmit=(Button)findViewById(R.id.buttonSubmit);


        skBarMood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rgDepartment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.radioButtonSIS)
            {
                stud.department=rbSIS.getText().toString();

            }
                else if(checkedId==R.id.radioButtonCS)
            {
                stud.department=rbCS.getText().toString();


            }
            else if(checkedId==R.id.radioButtonBIO)
            {
                stud.department=rbBIO.getText().toString();


            }
            else if(checkedId==R.id.radioButtonOthers)
            {
                stud.department=rbOthers.getText().toString();


            }

            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextName.getText().length()==0||editTextEmail.getText().length()==0||rgDepartment.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(MainActivity.this,"Enter the Mandatory Fields (Name ,Email,Department)",Toast.LENGTH_LONG).show();


                }
                else if(!isValidEmail(editTextEmail.getText()))
                {
                    Toast.makeText(MainActivity.this,"Enter a valid Email",Toast.LENGTH_LONG).show();
                }

                else
                {
                    stud.name=editTextName.getText().toString();
                    stud.emailAddress=editTextEmail.getText().toString();
                    stud.setMood(Integer.toString(skBarMood.getProgress()));
                    Intent intentDisplay=new Intent(MainActivity.this,DisplayActivity.class);
                    intentDisplay.putExtra(STUDENT_KEY,stud);
                    startActivity(intentDisplay);


                }
            }
        });


    }
    public  boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
