package com.example.lakshminarayanabr.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.sax.RootElement;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    Student s;
    EditText editTextName;
    EditText editTextEmail;
     RadioGroup rgDepartment;
     RadioButton rbSIS;
     RadioButton rbCS;
     RadioButton rbBIO;
     RadioButton rbOthers;
     SeekBar skBarMood;
    TextView txtVwDept;
    TextView txtVwMood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit);
                editTextName=new EditText(this);
        editTextEmail=new EditText(this);


        final String editKey;
        if(getIntent().getExtras().getSerializable(DisplayActivity.STUD_KEY)!=null)
        s=(Student)getIntent().getExtras().getSerializable(DisplayActivity.STUD_KEY);
        else
        s=new Student();

        if(getIntent().getExtras().getString(DisplayActivity.EDIT_KEY).length()>0)
        editKey=getIntent().getExtras().getString(DisplayActivity.EDIT_KEY);
        else
        editKey="";

        RelativeLayout relLayout=new RelativeLayout(this);
        relLayout.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        TextView txtHeading=new TextView(this);
        txtHeading.setId(R.id.textViewHeading);
        txtHeading.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtHeading.setText(R.string.string_HeadingEditActivity);
        txtHeading.setGravity(Gravity.CENTER_HORIZONTAL);
        txtHeading.setTextSize(25.0f);





        relLayout.addView(txtHeading);
        if(editKey.equals(DisplayActivity.NAME_KEY))
        {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.textViewHeading);
            params.topMargin=20;
            editTextName.setHint(R.string.string_name_placeholder);
            editTextName.setLayoutParams(params);
            int maxLength = 20;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(maxLength);
            editTextName.setFilters(FilterArray);            relLayout.addView(editTextName);
            editTextName.setText(s.name);






        }
        else if(editKey.equals(DisplayActivity.EMAIL_KEY))
        {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.textViewHeading);
            params.topMargin=20;
            editTextEmail.setHint(R.string.string_email_placeholder);
            editTextEmail.setLayoutParams(params);
            int maxLength = 20;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(maxLength);
            editTextEmail.setFilters(FilterArray);
            relLayout.addView(editTextEmail);
            editTextEmail.setText(s.emailAddress);




        }
        else if(editKey.equals(DisplayActivity.DEPT_KEY))
        {
            txtVwDept=new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.textViewHeading);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.topMargin=30;

            txtVwDept.setId(R.id.textViewDepartment);
            txtVwDept.setText(R.string.string_Department);
            txtVwDept.setTextSize(20.0f);
            txtVwDept.setLayoutParams(params);
            relLayout.addView(txtVwDept);

            rgDepartment=new RadioGroup(this);
            RelativeLayout.LayoutParams paramsRG = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsRG.addRule(RelativeLayout.BELOW,R.id.textViewDepartment);
            paramsRG.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            rgDepartment.setId(R.id.radioGroupDept);
            rgDepartment.setOrientation(LinearLayout.VERTICAL);

            rbSIS=new RadioButton(this);
            rbSIS.setId(R.id.radioButtonSIS);
            rbSIS.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            rbSIS.setText(R.string.string_SIS);
            rbSIS.setTextSize(20.0f);
            rgDepartment.addView(rbSIS);

            rbCS=new RadioButton(this);
            rbCS.setId(R.id.radioButtonCS);
            rbCS.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            rbCS.setText(R.string.string_CS);
            rbCS.setTextSize(20.0f);
            rgDepartment.addView(rbCS);

            rbBIO=new RadioButton(this);
            rbBIO.setId(R.id.radioButtonBIO);
            rbBIO.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            rbBIO.setText(R.string.string_BIO);
            rbBIO.setTextSize(20.0f);
            rgDepartment.addView(rbBIO);

            rbOthers=new RadioButton(this);
            rbOthers.setId(R.id.radioButtonOthers);
            rbOthers.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT));
            rbOthers.setText(R.string.string_Others);
            rbOthers.setTextSize(20.0f);
            rgDepartment.addView(rbOthers);

            rgDepartment.setLayoutParams(paramsRG);
            relLayout.addView(rgDepartment);

            if(s.department.equals(getString(R.string.string_SIS)))
            {
                rbSIS.setChecked(true);

            }
            else if(s.department.equals(getString(R.string.string_CS)))
            {
                rbCS.setChecked(true);

            }
            else if(s.department.equals(getString(R.string.string_BIO)))
            {
                rbBIO.setChecked(true);

            }
            else if(s.department.equals(getString(R.string.string_Others)))
            {
                rbOthers.setChecked(true);

            }








        }
        else if(editKey.equals(DisplayActivity.MOOD_KEY))
        {

            txtVwMood=new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.textViewHeading);
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.topMargin=200;

            txtVwMood.setId(R.id.textViewMood);
            txtVwMood.setText(R.string.string_mood);
            txtVwMood.setTextSize(20.0f);
            txtVwMood.setLayoutParams(params);
            relLayout.addView(txtVwMood);

            skBarMood=new SeekBar(this);
            skBarMood.setMax(100);
            RelativeLayout.LayoutParams paramsSB = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            paramsSB.addRule(RelativeLayout.BELOW,R.id.textViewMood);
            paramsSB.topMargin=10;
            paramsSB.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            skBarMood.setId(R.id.seekBarMood);
            skBarMood.setLayoutParams(paramsSB);
            relLayout.addView(skBarMood);
            skBarMood.setProgress(Integer.parseInt(s.mood));


        }


        Button btnSave=new Button(this);
//        btnSave.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        btnSave.setText(R.string.string_save);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params.addRule(ViewGroup.LayoutParams.MATCH_PARENT,);
        btnSave.setLayoutParams(params);



        relLayout.addView(btnSave);
        setContentView(relLayout);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editKey.equals(DisplayActivity.NAME_KEY))
                {
                    if(editTextName.getText().toString().length()>0)
                    {
                        s.name=editTextName.getText().toString();
                        Intent intentData=new Intent();
                        if(s==null)
                        {


                            setResult(Activity.RESULT_CANCELED);
                        }
                        else
                        {
                            intentData.putExtra(com.example.lakshminarayanabr.inclass03.DisplayActivity.STUD_KEY,s);


                            setResult(Activity.RESULT_OK,intentData);

                        }



                        finish();


                    }
                    else
                    {
                        Toast.makeText(EditActivity.this,"Enter a valid Name",Toast.LENGTH_LONG).show();

                    }
                }

                else if(editKey.equals(DisplayActivity.EMAIL_KEY))
                { if(isValidEmail(editTextEmail.getText().toString()))
                {
                    if(editTextEmail.getText().toString().length()>0)
                    {

                        s.emailAddress=editTextEmail.getText().toString();
                        Intent intentData=new Intent();
                        if(s==null)
                        {


                            setResult(Activity.RESULT_CANCELED);
                        }
                        else
                        {
                            intentData.putExtra(com.example.lakshminarayanabr.inclass03.DisplayActivity.STUD_KEY,s);


                            setResult(Activity.RESULT_OK,intentData);

                        }



                        finish();


                    }

                }
                else
                {
                    Toast.makeText(EditActivity.this,"Enter a valid Email",Toast.LENGTH_LONG).show();


                }

                }
                else if(editKey.equals(DisplayActivity.DEPT_KEY))
                {
                    RadioButton rb=(RadioButton)findViewById(rgDepartment.getCheckedRadioButtonId());
                    s.department=rb.getText().toString();
                    Intent intentData=new Intent();
                    if(s==null)
                    {


                        setResult(Activity.RESULT_CANCELED);
                    }
                    else
                    {
                        intentData.putExtra(com.example.lakshminarayanabr.inclass03.DisplayActivity.STUD_KEY,s);


                        setResult(Activity.RESULT_OK,intentData);

                    }



                    finish();


                }
                else
                {
                    s.mood=String.valueOf(skBarMood.getProgress());
                    Intent intentData=new Intent();
                    if(s==null)
                    {


                        setResult(Activity.RESULT_CANCELED);
                    }
                    else
                    {
                        intentData.putExtra(com.example.lakshminarayanabr.inclass03.DisplayActivity.STUD_KEY,s);


                        setResult(Activity.RESULT_OK,intentData);

                    }



                    finish();



                }






            }
        });






    }
    public  boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
