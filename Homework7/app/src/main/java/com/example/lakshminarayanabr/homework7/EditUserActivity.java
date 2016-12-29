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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URI;

public class EditUserActivity extends AppCompatActivity {

    EditText fname,lname, email, password,repeatpassword;
    Button signup, cancel;

    ImageView imageView;

    Switch aSwitch;

    User usr;
    FirebaseStorage mStorage=FirebaseStorage.getInstance();

    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://firstfirebase-822f5.appspot.com");


    public static  final  int SELECT_PICTURE_REQUEST=100;

    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener mAuthListner;

    public DatabaseReference mUserRefChild;

    Uri downloadUrl;

    User loggedinUser;


    protected void onStop() {
        super.onStop();
//        if (mAuthListner != null) {
//            mAuth.removeAuthStateListener(mAuthListner);
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListner);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        if(getIntent().getExtras().getParcelable(UserListActivity.LOGGED_USER_OBJ)!=null)
        {
            loggedinUser=(User)getIntent().getExtras().getParcelable(UserListActivity.LOGGED_USER_OBJ);

        }


        fname= (EditText) findViewById(R.id.editTextFirstName);
        lname=(EditText)findViewById(R.id.editTextLastname);
        email= (EditText) findViewById(R.id.editTextEmailAddress);
        password= (EditText) findViewById(R.id.editTextpassword);
        repeatpassword=(EditText)findViewById(R.id.editTextRepeatPassword);

        imageView=(ImageView)findViewById(R.id.imageView);
        aSwitch=(Switch)findViewById(R.id.switch2Gender);
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

        if(loggedinUser!=null)
        {
            fname.setText(loggedinUser.getFname());
            lname.setText(loggedinUser.getLname());
            email.setText(loggedinUser.getEmail());
            password.setText(loggedinUser.getPassword());

            if(loggedinUser.getProfilePicURL()==null||loggedinUser.getProfilePicURL().toString().length()<=0)
            {
                imageView.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
            }
            else
                Picasso.with(this).load(loggedinUser.getProfilePicURL()).into(imageView);

            if(loggedinUser.getGender().toString().equalsIgnoreCase("M"))
            {
               aSwitch.setChecked(true);
                aSwitch.setText("M");

            }
            else
            {
                aSwitch.setChecked(false);
                aSwitch.setText("F");
            }


        }




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
//        mAuthListner= new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//
//                FirebaseUser user=firebaseAuth.getCurrentUser();
//                if (user!=null)
//                {//user is signed in
//
//                    //user.updateProfile()
//
//                    // FirebaseUser user = firebaseAuth.getCurrentUser();
//                    if(user!=null) {
//                        if(uri_global!=null)
//                        {
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(usr.getFname()+" "+usr.getLname()).setPhotoUri(uri_global).build();
//                            user.updateProfile(profileUpdates);
//
//                        }
//                        else
//                        {
//                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(usr.getFname()+" "+usr.getLname()).build();
//
//                            user.updateProfile(profileUpdates);
//
//                        }
//
//
//                    }
//
//                    Log.d("demo","onAuthStateChanged:signed_in:"+user.getUid());
//                }
//                else
//                {
//                    Log.d("demo","User is signed out");
//                }
//            }
//        };

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

                    final FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if (user!=null)
                    {//user is signed in



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
                                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                        downloadUrl = taskSnapshot.getDownloadUrl();
                                        usr.setProfilePicURL(downloadUrl.toString());

                                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(usr.getFname()+" "+usr.getLname()).setPhotoUri(downloadUrl).build();
                                        user.updateProfile(profileUpdates).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Demo", "User profile updated.");

                                                    user.updateEmail(semail).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d("Demo", "User Email updated.");

                                                                user.updatePassword(spassword).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            Log.d("Demo", "User password updated.");

                                                                            DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                                                                            DatabaseReference mUserRef=mRootRef.child("ChatUser");
                                                                            DatabaseReference userreference=mUserRef.child(loggedinUser.getUserid());
                                                                            usr.userid=userreference.getKey();
                                                                            userreference.setValue(usr);



                                                                            Toast.makeText(EditUserActivity.this,"Successfully Updated User",Toast.LENGTH_LONG).show();
                                                                            finish();



                                                                        }

                                                                    }
                                                                });


                                                            }

                                                        }
                                                    });


                                                }



                                            }
                                        });













                                    }
                                });


                            }
                            else
                            {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(usr.getFname()+" "+usr.getLname()).setPhotoUri(Uri.parse(loggedinUser.getProfilePicURL())).build();

                                user.updateProfile(profileUpdates).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("Demo", "User profile updated.");

                                            user.updateEmail(semail).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d("Demo", "User Email updated.");

                                                        user.updatePassword(spassword).addOnCompleteListener(EditUserActivity.this, new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Log.d("Demo", "User password updated.");

                                                                    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                                                                    DatabaseReference mUserRef=mRootRef.child("ChatUser");
                                                                    DatabaseReference userreference=mUserRef.child(loggedinUser.getUserid());
                                                                    usr.userid=userreference.getKey();
                                                                    usr.setProfilePicURL(loggedinUser.getProfilePicURL());
                                                                    userreference.setValue(usr);


                                                                    Toast.makeText(EditUserActivity.this,"Successfully Updated User",Toast.LENGTH_LONG).show();
                                                                    finish();


                                                                }

                                                            }
                                                        });


                                                    }

                                                }
                                            });


                                        }



                                    }
                                });



                            }




                    }

//                        Log.d("demo","onAuthStateChanged:signed_in:"+user.getUid());
                    }



                else
                {
                    Toast.makeText(EditUserActivity.this,"All fields must be filled correctly!",Toast.LENGTH_SHORT).show();
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
