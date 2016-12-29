package com.example.lakshminarayanabr.inclass11;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    EditText fname,lname, email, password,repeatpassword;
    Button signup, cancel;

    User usr;


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

        fname= (EditText) findViewById(R.id.editTextFirstName);
        lname=(EditText)findViewById(R.id.editTextLastName);
        email= (EditText) findViewById(R.id.editTextSignUpEmail);
        password= (EditText) findViewById(R.id.editTextSignUppassword);
        repeatpassword=(EditText)findViewById(R.id.editTextrepeatPassword);


        signup= (Button) findViewById(R.id.buttonSignUP);
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

                    //user.updateProfile()

                   // FirebaseUser user = firebaseAuth.getCurrentUser();
                    if(user!=null) {
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(usr.getFname()+" "+usr.getLname()).build();
                        user.updateProfile(profileUpdates);
                    }

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
                String sname,semail,spassword,rpassword;
                sname=fname.getText().toString()+" "+lname.getText().toString();
                semail=email.getText().toString();
                spassword=password.getText().toString();
                rpassword=repeatpassword.getText().toString();

                if (sname.length()>0&&semail.length()>0&&spassword.length()>0&&spassword.equals(rpassword)&&lname.getText().length()>0&&fname.getText().length()>0)
                {
                    // mNewUser.setValue(sname);

                    usr=new User();
                    usr.fname=fname.getText().toString();
                    usr.lname=lname.getText().toString();
                    usr.email=semail;
                    usr.password=spassword;
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
                                DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                                DatabaseReference mUserRef=mRootRef.child("User");
                                DatabaseReference userreference=mUserRef.push();
                                usr.userid=userreference.getKey();
                                mUserRef.setValue(usr);




                                Toast.makeText(SignUpActivity.this,"Successfully Created User",Toast.LENGTH_LONG).show();
                                finish();


                            }
                        }
                    });


                }

                else
                {
                    Toast.makeText(SignUpActivity.this,"All fields must be filled correctly!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

