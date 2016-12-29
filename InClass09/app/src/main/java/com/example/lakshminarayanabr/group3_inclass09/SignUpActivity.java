package com.example.lakshminarayanabr.group3_inclass09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity implements APICaller.ActivityInterface  {

    EditText email,fname,lname,password,repeatpassword;

    Button btnSubmit,btnCancel;

    APICaller caller=new APICaller(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email=(EditText)findViewById(R.id.editTextEmail);
        fname=(EditText)findViewById(R.id.editTextFn);
        lname=(EditText)findViewById(R.id.editTextLastName);
        password=(EditText)findViewById(R.id.editTextPassword);
        repeatpassword=(EditText)findViewById(R.id.editTextRepeatPassword);

        findViewById(R.id.buttonSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().length()>0 && fname.getText().toString().length()>0 && lname.getText().toString().length()>0 && password.getText().toString().length()>5 && password.getText().toString().equals(repeatpassword.getText().toString())
                )
                {
                    caller.SignUp(email.getText().toString(),password.getText().toString(),fname.getText().toString(),lname.getText().toString());

                }
                else
                {
                    Toast.makeText(SignUpActivity.this,"Error in Sign Up Details",Toast.LENGTH_LONG).show();
                }

            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            finish();
            }
        });



    }

       public void storePrefrences(String converted )
    {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.CHATAPP,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MainActivity.TOKEN_STR,converted);
        editor.commit();

    }

    public String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.CHATAPP,MODE_PRIVATE);
        String edited=sharedPreferences.getString(MainActivity.TOKEN_STR,"No Token Available");
        return edited;
    }
    @Override
    public void loginCalled(final User user) {

        if(user.getStatus().equalsIgnoreCase("ok"))
        {


            runOnUiThread(new Runnable() {
                public void run() {
                    Intent chatActivity=new Intent(SignUpActivity.this,ChatActivity.class);
                    chatActivity.putExtra(MainActivity.USER_OBJ,user);
                    startActivity(chatActivity);
                }
            });


        }
        else
        {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(SignUpActivity.this,"Error Signing up with the credentials",Toast.LENGTH_LONG).show();
                }
            });

        }

    }

}
