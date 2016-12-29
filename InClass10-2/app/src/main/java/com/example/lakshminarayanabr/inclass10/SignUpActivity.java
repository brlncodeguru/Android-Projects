package com.example.lakshminarayanabr.inclass10;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    EditText name, email, password;
    Button signup, cancel;




    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListner;


    protected void onStop() {
        super.onStop();
        if (mAuthListner != null) {
            mAuth.removeAuthStateListener(mAuthListner);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name= (EditText) findViewById(R.id.editTextFullName);
        email= (EditText) findViewById(R.id.editTextEmail);
        password= (EditText) findViewById(R.id.editTextPassword);

        signup= (Button) findViewById(R.id.buttonSignUp);
        cancel= (Button) findViewById(R.id.buttonCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mAuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user=firebaseAuth.getCurrentUser();
                if (user!=null)
                {//user is signed in
                    Log.d("demo","onAuthStateChanged:signed_in:"+user.getUid());
                }
                else
                {
                    Log.d("demo","User is signed out");
                }
            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sname,semail,spassword;
                sname=name.getText().toString();
                semail=email.getText().toString();
                spassword=password.getText().toString();

                if (sname.length()>0&&semail.length()>0&&spassword.length()>0)
                {
                    // mNewUser.setValue(sname);
                    mAuth.createUserWithEmailAndPassword(semail,spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(SignUpActivity.this,"Error: "+task.getException(), Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                Log.d("demo", "createUserWithEmail:onComplete:" + task.isSuccessful());
                                Toast.makeText(SignUpActivity.this,"Successfully Created User",Toast.LENGTH_LONG).show();
                                finish();


                            }
                        }
                    });


                }

                else
                {
                    Toast.makeText(SignUpActivity.this,"All fields must be filled!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
