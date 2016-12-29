package com.example.lakshminarayanabr.group3_inclass09;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Authenticator;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

public class MainActivity extends AppCompatActivity implements  APICaller.ActivityInterface {


    private String credentials;

    EditText email;
    EditText password;

    Button btnLogin,btnSignUp;

    final static  int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE=1000;

    public static final String USER_OBJ="USEROBJ";
    public static final String TOKEN_STR="TOKEN";
    public static final String CHATAPP="CHATAPP";



    User usr;

    APICaller caller =new APICaller(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email=(EditText)findViewById(R.id.editTextEmail);
        password=(EditText)findViewById(R.id.editTextPassword);

        btnLogin=(Button)findViewById(R.id.buttonlogin);
        btnSignUp=(Button)findViewById(R.id.buttonSignup);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE )!= PackageManager.PERMISSION_GRANTED)
        {


            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        }
        else
        {

        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signIntent=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signIntent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(email.getText().toString().length()>0&&password.getText().toString().length()>0)
                {
                    caller.login(email.getText().toString(),password.getText().toString());

                }
                else
                {
                    Toast.makeText(MainActivity.this,"Enter the login credentials",Toast.LENGTH_LONG).show();
                }
            }
        });







//        caller.SignUp("lax23@test.net","lax1267","lakshmi1","narayan1");

//        System.out.println(caller.usr.toString());








    }
    public void storePrefrences(String converted )
    {
        SharedPreferences sharedPreferences=getSharedPreferences(CHATAPP,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(TOKEN_STR,converted);
        editor.commit();

    }

    public String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(CHATAPP,MODE_PRIVATE);
        String edited=sharedPreferences.getString(TOKEN_STR,"No Token Available");
        return edited;
    }

    @Override
    public void loginCalled(final User user) {

        if(user.getStatus().equalsIgnoreCase("ok"))
        {


            runOnUiThread(new Runnable() {
                public void run() {

                    storePrefrences(user.token);
                    Intent chatActivity=new Intent(MainActivity.this,ChatActivity.class);
                    chatActivity.putExtra(USER_OBJ,user);
                    startActivity(chatActivity);
                }
            });


        }
        else
        {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(MainActivity.this,"Error logging in with the login credentials",Toast.LENGTH_LONG).show();
                }
            });

        }

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






                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
        }
    }

}
