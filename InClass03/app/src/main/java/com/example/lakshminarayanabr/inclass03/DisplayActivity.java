package com.example.lakshminarayanabr.inclass03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
 final static int REQ_CODE=100;
    final static String EDIT_KEY="EDITKEY";
    final static String STUD_KEY="STUD";
    final static String NAME_KEY="NAME";
    final static String EMAIL_KEY="EMAIL";
    final static String DEPT_KEY="DEPARTMENT";
    final static String MOOD_KEY="MOOD";
     TextView txtName;
     TextView txtEmail;
     TextView txtDept;
     TextView txtMood;
    Student std;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
         txtName=(TextView)findViewById(R.id.textViewNameValue);
        txtEmail=(TextView)findViewById(R.id.textViewEmailValue);
        txtDept=(TextView)findViewById(R.id.textViewDeptValue);
        txtMood=(TextView)findViewById(R.id.textViewMoodValue);

        Button btnName=(Button)findViewById(R.id.buttonNameEdit);
        Button btnEmail=(Button)findViewById(R.id.buttonEmailEdit);
        Button btnDept=(Button)findViewById(R.id.buttonDeptEdit);
        Button btnMood=(Button)findViewById(R.id.buttonMoodEdit);

        std=(Student)getIntent().getExtras().getSerializable(MainActivity.STUDENT_KEY);
        txtName.setText(std.name);
        txtEmail.setText(std.emailAddress);
        txtDept.setText(std.department);
        txtMood.setText(std.mood +" % Positive");

        btnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit=new Intent("com.example.lakshminarayanabr.inclass03.intent.action.EDITVIEW");
                intentEdit.putExtra(EDIT_KEY,NAME_KEY);
                intentEdit.putExtra(STUD_KEY,std);


                startActivityForResult(intentEdit,REQ_CODE);


            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit=new Intent("com.example.lakshminarayanabr.inclass03.intent.action.EDITVIEW");
                intentEdit.putExtra(EDIT_KEY,EMAIL_KEY);
                intentEdit.putExtra(STUD_KEY,std);


                startActivityForResult(intentEdit,REQ_CODE);


            }
        });
        btnDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit=new Intent("com.example.lakshminarayanabr.inclass03.intent.action.EDITVIEW");
                intentEdit.putExtra(EDIT_KEY,DEPT_KEY);
                intentEdit.putExtra(STUD_KEY,std);


                startActivityForResult(intentEdit,REQ_CODE);


            }
        });
        btnMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit=new Intent("com.example.lakshminarayanabr.inclass03.intent.action.EDITVIEW");
                intentEdit.putExtra(EDIT_KEY,MOOD_KEY);
                intentEdit.putExtra(STUD_KEY,std);


                startActivityForResult(intentEdit,REQ_CODE);


            }
        });











    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(resultCode== Activity.RESULT_OK && data !=null && data.getExtras().getSerializable(STUD_KEY)!=null)
            {
                if(requestCode==REQ_CODE)
                {
                    txtName=(TextView)findViewById(R.id.textViewNameValue);
                    txtEmail=(TextView)findViewById(R.id.textViewEmailValue);
                    txtDept=(TextView)findViewById(R.id.textViewDeptValue);
                    txtMood=(TextView)findViewById(R.id.textViewMoodValue);

                    Student s=(Student)data.getExtras().getSerializable(STUD_KEY);
                    std=s;
                txtName.setText(s.name);
                txtEmail.setText(s.emailAddress);
                txtDept.setText(s.department);
                txtMood.setText(s.mood +" % Positive");

            }
        }
    }
}
