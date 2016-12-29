package com.example.lakshminarayanabr.homework7;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {


    EditText fname,lname, email, password,repeatpassword;
    Button signup, cancel;

    ImageView imageView;

    Switch aSwitch;

    Uri downloadUrl;

   User usr;
    FirebaseStorage mStorage=FirebaseStorage.getInstance();

    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://firstfirebase-822f5.appspot.com");


    public static  final  int SELECT_PICTURE_REQUEST=100;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListner;

    boolean isAuthListenerCalled=false;


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
        lname=(EditText)findViewById(R.id.editTextLastname);
        email= (EditText) findViewById(R.id.editTextEmailAddress);
        password= (EditText) findViewById(R.id.editTextpassword);
        repeatpassword=(EditText)findViewById(R.id.editTextRepeatPassword);

        imageView=(ImageView)findViewById(R.id.imageView);
        aSwitch=(Switch)findViewById(R.id.switch2Gender);

        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    aSwitch.setText("M");
                }
                else
                {
                    aSwitch.setText("F");

                }
            }
        });


        signup= (Button) findViewById(R.id.buttonSignUp);
        cancel= (Button) findViewById(R.id.buttonCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_PICTURE_REQUEST);
            }
        });
        mAuthListner= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(isAuthListenerCalled)
        {
                return;
            }
                else
{
    final FirebaseUser user=firebaseAuth.getCurrentUser();
    if (user!=null)
    {//user is signed in
        isAuthListenerCalled=true;

        //user.updateProfile()

        // FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null&&usr!=null) {
            if(uri_global!=null)
            {

                StorageReference imagesRef = storageRef.child("images/"+user.getUid());
                UploadTask uploadTask = imagesRef.putFile(uri_global);

// Register observers to listen for when the download is done or if it fails
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                    uri_global=null;
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        downloadUrl = taskSnapshot.getDownloadUrl();
                        if(usr!=null)
                            usr.setProfilePicURL(downloadUrl.toString());



                        if(uri_global!=null&&usr!=null)
                        {
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(usr.getFname()+" "+usr.getLname()).setPhotoUri(downloadUrl).build();
                            uri_global=null;
                            user.updateProfile(profileUpdates).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        if(usr!=null)
                                        {
                                            DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                                            DatabaseReference mUserRef=mRootRef.child("ChatUser");
                                            DatabaseReference userreference=mUserRef.push();
                                            usr.userid=userreference.getKey();
                                            userreference.setValue(usr);
                                            Toast.makeText(SignUpActivity.this,"Successfully Created User",Toast.LENGTH_LONG).show();
                                            finish();
                                            isAuthListenerCalled=false;
//                                                          usr=null;
                                        }

                                    }
                                }
                            });
                        }











                    }
                });




            }
            else
            {
                if(usr!=null)
                {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(usr.getFname()+" "+usr.getLname()).build();

                    user.updateProfile(profileUpdates).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                if(usr!=null)
                                {
                                    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                                    DatabaseReference mUserRef=mRootRef.child("ChatUser");
                                    DatabaseReference userreference=mUserRef.push();
                                    usr.userid=userreference.getKey();
                                    userreference.setValue(usr);
                                    Toast.makeText(SignUpActivity.this,"Successfully Created User",Toast.LENGTH_LONG).show();
                                    finish();
                                    usr=null;
                                    isAuthListenerCalled=false;
                                }
                            }
                        }
                    });




                }

            }


        }

        Log.d("demo","onAuthStateChanged:signed_in:"+user.getUid());
    }
    else
    {
        Log.d("demo","User is signed out");
    }
}

            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sname,semail,spassword,rpassword;
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
                    usr.gender=aSwitch.getText().toString();
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
                                if(uri_global!=null)
                                {


                                }
                                else
                                {

                                }









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

    Uri uri_global;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                if(data!=null)
                {
                    uri_global = data.getData();
                    Log.d("debug", uri_global.toString());
                    Bitmap bitmap = null;
                    uri_global = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_global);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }



                }




                // Cursor to get image uri to display




            }

        }
    }

    public String getPath(Uri uri) {
        String res = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if(cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
