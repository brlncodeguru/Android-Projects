package com.example.lakshminarayanabr.inclass10;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText emailID;
    EditText password;

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);



    }

    Button login;
    Button createAcct;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailID=(EditText) findViewById(R.id.editTextEmail);
        password=(EditText) findViewById(R.id.editTextPassword);

        login=(Button)findViewById(R.id.buttonLogin);
        createAcct=(Button)findViewById(R.id.buttonCreateAcct);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Toast.makeText(MainActivity.this, "Sign in Done",
                    Toast.LENGTH_SHORT).show();

            Intent intExpList=new Intent(MainActivity.this,ExpenseListActivity.class);
            startActivity(intExpList);

            // User is signed in
        } else {
            // No user is signed in
        }

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Demo", "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d("Demo", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(emailID.getText().toString().length()>0&&password.getText().toString().length()>0)
                {



//                    AuthCredential credential = EmailAuthProvider
//                            .getCredential(emailID.getText().toString(), password.getText().toString());


                    mAuth.signInWithEmailAndPassword(emailID.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if(task.isSuccessful())
                                    {
                                        Log.d("Demo", "signInWithEmail:onComplete:" + task.isSuccessful());
                                        Toast.makeText(MainActivity.this, "Sign in Done",
                                                Toast.LENGTH_SHORT).show();

                                        Intent intExpList=new Intent(MainActivity.this,ExpenseListActivity.class);
                                        startActivity(intExpList);

                                    }
                                    else
                                    {
                                        Log.w("Demo", "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "Sign in Failed:"+task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                }
                else
                {
                    Toast.makeText(MainActivity.this,"Enter the Credentials",Toast.LENGTH_LONG).show();
                }

            }
        });

        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUp=new Intent(MainActivity.this,SignUpActivity.class);
                startActivity(signUp);


            }
        });
    }
}
