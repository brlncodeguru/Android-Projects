package com.example.lakshminarayanabr.homework7;

import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();

    private FirebaseAuth.AuthStateListener mAuthListener;

    public static String USER_LIST="USERLIST";

    EditText emailID;
    EditText password;

    Button login;
    Button createAcct;

    String  fGender;
    String  gGender;

    GoogleApiClient apiClient;

    SignInButton signInButton;

    String typeOfAuth="";

    ArrayList<User> users;

    private static final int RC_CODE=900;
    String token="123";

    ArrayList<User> userArrayList;


    @Override
    protected void onResume() {
        super.onResume();
//        mAuth.addAuthStateListener(mAuthListener);






    }

    CallbackManager mCallbackManager;
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

    @Override
    protected void onPause() {
        super.onPause();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);
        emailID = (EditText) findViewById(R.id.editTextEmailAddress);
        password = (EditText) findViewById(R.id.editTextPassword);

        login = (Button) findViewById(R.id.buttonLogin);
        createAcct = (Button) findViewById(R.id.buttonSignup);

        users=new ArrayList<>();

        userArrayList=new ArrayList<>();
//        Log.d("mAuth",mAuth.getCurrentUser().getDisplayName());
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null&&(typeOfAuth.equalsIgnoreCase("facebook")||typeOfAuth.equalsIgnoreCase("google"))) {
                    // User is signed in
                    Log.d("Demo", "onAuthStateChanged:signed_in:" + user.getUid());
                    Log.d("Photo", "onAuthStateChanged:signed_in:" + user.getPhotoUrl());

                    String convertedAgain=getSharedPreferences();
                    ArrayList<String> strings;
                    Gson gson=new Gson();
                    storeTypeofAuthPrefrences(typeOfAuth);

                    Log.d("StoredUsers",convertedAgain);


                    if(convertedAgain=="No Users Available")
                    {
                        strings=new ArrayList<>();
                    }
                    else
                    {
                        strings= (ArrayList<String>) gson.fromJson(convertedAgain,new TypeToken<ArrayList<String>>(){

                        }.getType());

                    }
                    Log.d("StoredStrings",strings.toString());
                    if(strings.size()==0)
                    {
                        users.clear();
                    }
                    else
                    {
                        for(int x=0;x<strings.size();x++)
                        {
                            User userObj=gson.fromJson(strings.get(x),User.class);
                            if (!users.contains(userObj))
                                users.add(userObj);

                        }


                    }
                    Log.d("UsersList",users.toString());
                    if(typeOfAuth.equalsIgnoreCase("facebook")||typeOfAuth.equalsIgnoreCase("google"))
                    {
                        User usr=new User();
                        String[] names=user.getDisplayName().split(" ");
                        usr.fname=names[0];
                        usr.lname=names[1];
                        usr.setProfilePicURL(user.getPhotoUrl().toString());
                        usr.email=user.getEmail();
                        if(typeOfAuth.equalsIgnoreCase("facebook"))
                        {
                            usr.gender=fGender;
                        }
                        else if(typeOfAuth.equalsIgnoreCase("google"))
                        {

                        }
                        Log.d("Users", String.valueOf(users.contains(usr)));

                        if(!users.contains(usr))
                        {
                            Log.d("Users",usr.toString());


                            DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
                            DatabaseReference mUserRef=mRootRef.child("ChatUser");
                            DatabaseReference userreference=mUserRef.push();
                            usr.userid=userreference.getKey();
                            userreference.setValue(usr);




                        }
                        typeOfAuth="";

                    }


//                    Log.d("Intent", "AuthListener");
//                    if (mAuthListener != null) {
//                        mAuth.removeAuthStateListener(mAuthListener);
//                    }
                    Intent intent=new Intent(MainActivity.this,UserListActivity.class);
                    intent.putParcelableArrayListExtra(USER_LIST,userArrayList);

                    startActivity(intent);
                }
                else if(user != null&&(typeOfAuth.equalsIgnoreCase("Email")))
                {
                    storeTypeofAuthPrefrences(typeOfAuth);

                    typeOfAuth="";
                    Intent intent=new Intent(MainActivity.this,UserListActivity.class);
                    intent.putParcelableArrayListExtra(USER_LIST,userArrayList);

                    startActivity(intent);
                }

                else {
                    // User is signed out
                    Log.d("Demo", "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };





//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user != null) {
//            // User is signed in
//            Log.d("Demo", "onAuthStateChanged:signed_in:" + user.getUid());
//
//            Intent intent=new Intent(MainActivity.this,UserListActivity.class);
//            startActivity(intent);
//
//        } else {
//            // User is signed out
//            Log.d("Demo", "onAuthStateChanged:signed_out");
//        }


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(new Scope(Scopes.PROFILE))
                .requestScopes(new Scope(Scopes.PLUS_LOGIN)).requestIdToken("311996157836-o0a8047a9gidiibfhimeu7gm0q5olfrr.apps.googleusercontent.com").requestEmail().build();

        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).addApi(Plus.API).build();
//        statusview=(TextView)findViewById(R.id.textView);
        signInButton=(SignInButton)findViewById(R.id.buttonGoogleLogin);
//        mAuth.addAuthStateListener(mAuthListener);
        typeOfAuth=getTypeOfAuthSharedPreferences();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mAuth.addAuthStateListener(mAuthListener);


                Intent signinIntent=Auth.GoogleSignInApi.getSignInIntent(apiClient);
                startActivityForResult(signinIntent,RC_CODE);

            }
        });



        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.buttonFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Demo", "facebook:onSuccess:" + loginResult);
                typeOfAuth="facebook";
//                mAuth.addAuthStateListener(mAuthListener);

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                   String Name = object.getString("name");

                                    fGender=object.getString("gender");



//                                    FEmail = object.getString("email");
//                                    Log.v("Email = ", " " + FEmail);
//                                    Toast.makeText(getApplicationContext(), "Name " + Name, Toast.LENGTH_LONG).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d("Demo", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Demo", "facebook:onError", error);
                // ...
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (emailID.getText().toString().length() > 0 && password.getText().toString().length() > 0) {


//                    AuthCredential credential = EmailAuthProvider
//                            .getCredential(emailID.getText().toString(), password.getText().toString());


                    mAuth.signInWithEmailAndPassword(emailID.getText().toString(), password.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        mAuth.addAuthStateListener(mAuthListener);

                                        Log.d("Demo", "signInWithEmail:onComplete:" + task.isSuccessful());
                                        Toast.makeText(MainActivity.this, "Sign in Done",
                                                Toast.LENGTH_SHORT).show();
                                        typeOfAuth="Email";
                                        Log.d("Intent", "signincomplete");
//                                        if (mAuthListener != null) {
//                                            mAuth.removeAuthStateListener(mAuthListener);
//                                        }
//                                        Intent intExpList = new Intent(MainActivity.this, UserListActivity.class);
//                                        intExpList.putParcelableArrayListExtra(USER_LIST,userArrayList);
//                                        startActivity(intExpList);

                                    } else {
                                        Log.w("Demo", "signInWithEmail:failed", task.getException());
                                        Toast.makeText(MainActivity.this, "Sign in Failed:" + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });


                } else {
                    Toast.makeText(MainActivity.this, "Enter the Credentials", Toast.LENGTH_LONG).show();
                }

            }
        });

        createAcct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (mAuthListener != null) {
//                    mAuth.removeAuthStateListener(mAuthListener);
//                }
                Intent signUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(signUp);


            }
        });

        DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
        DatabaseReference mUserRef=mRootRef.child("ChatUser");
        mUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User usr=dataSnapshot.getValue(User.class);
                Log.d("mUSerRef","MainActivity");

                String convertedAgain=getSharedPreferences();

                Log.d("StoredUsers",convertedAgain);
                ArrayList<String> strings;
                Gson gson=new Gson();


                if(convertedAgain=="No Users Available")
                {
                    strings=new ArrayList<>();
                }
                else
                {
                    strings= (ArrayList<String>) gson.fromJson(convertedAgain,new TypeToken<ArrayList<String>>(){

                    }.getType());

                }

                if(strings.size()==0)
                {
                    users.add(usr);
                    String converted=gson.toJson(usr);
                    strings.add(converted);
                    String convertString=gson.toJson(strings);

                    storePrefrences(convertString);

                }
                else
                {
                    for(int x=0;x<strings.size();x++)
                    {
                        User userObj=gson.fromJson(strings.get(x),User.class);
                        if (!users.contains(userObj))
                            users.add(userObj);

                    }

                    if(!users.contains(usr))
                    {


                        users.add(usr);
                        String converted=gson.toJson(usr);
                        strings.add(converted);
                        String convertString=gson.toJson(strings);

                        storePrefrences(convertString);



                    }

                }

//                if(!userArrayList.contains(usr))
//                {
//                    userArrayList.add(usr);
//
//
//                }







            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Demo", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Profile profile = Profile.getCurrentProfile();








        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Demo", "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Demo", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            typeOfAuth="";

                        }

                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_CODE&& resultCode==RESULT_OK) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();

//                statusview.setText("Welcome "+acct.getDisplayName());


                firebaseAuthWithGoogle(acct);

            } else {
                Toast.makeText(MainActivity.this, "Error Logging in", Toast.LENGTH_LONG).show();

            }
        }
        else
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, "Authentication failed.Error in Connection"+connectionResult.getErrorMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        Log.d("Demo", "firebaseAuthWithGoogle:" + acct.getId());
        typeOfAuth="google";



//        Log.d("DemoGrantedScopes",acct.getGrantedScopes().toString());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Demo", "signInWithCredential:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w("Demo", "signInWithCredential", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            typeOfAuth="";
                        }
                        else
                        {

                        }
                        // ...
                    }
                });
    }

    public void storePrefrences(String converted )
    {
        Log.d("SHared",converted);
        SharedPreferences sharedPreferences=getSharedPreferences("CHAT_USER",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Users",converted);
        editor.commit();

    }

    public void storeTypeofAuthPrefrences(String converted )
    {
        Log.d("SHared",converted);
        SharedPreferences sharedPreferences=getSharedPreferences("CHAT_USER",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("TypeofAuth",converted);
        editor.commit();

    }

    public String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("CHAT_USER",MODE_PRIVATE);
        String edited=sharedPreferences.getString("Users","No Users Available");
        return edited;
    }
    public String getTypeOfAuthSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences("CHAT_USER",MODE_PRIVATE);
        String edited=sharedPreferences.getString("TypeofAuth","No Type of Auth Available");
        return edited;
    }

//    class GetGendersTask extends AsyncTask<GoogleSignInAccount, Void, List<Person.Gender>> {
//        @Override
//        protected List<Person.Gender> doInBackground(GoogleSignInAccount... googleSignInAccounts) {
//            List<Person.Gender> genderList = new ArrayList<>();
//            try {
//                HttpTransport httpTransport = new NetHttpTransport();
//                JacksonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//
//                //Redirect URL for web based applications.
//                // Can be empty too.
//                String redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
//
//                // Exchange auth code for access token
//                GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
//                        httpTransport,
//                        jsonFactory,
//                        getApplicationContext().getString(R.string.server_client_id),
//                        getApplicationContext().getString(R.string.server_client_secret),
//                        googleSignInAccounts[0].getServerAuthCode(),
//                        redirectUrl
//                ).execute();
//
//                GoogleCredential credential = new GoogleCredential.Builder()
//                        .setClientSecrets(
//                                getApplicationContext().getString(R.string.server_client_id),
//                                getApplicationContext().getString(R.string.server_client_secret)
//                        )
//                        .setTransport(httpTransport)
//                        .setJsonFactory(jsonFactory)
//                        .build();
//
//                credential.setFromTokenResponse(tokenResponse);
//
//                People peopleService = new People.Builder(httpTransport, jsonFactory, credential)
//                        .setApplicationName("My Application Name")
//                        .build();
//
//                // Get the user's profile
//                Person profile = peopleService.people().get("people/me").execute();
//                genderList.addAll(profile.getGenders());
//            }
//            catch (IOException e) {
//                e.printStackTrace();
//            }
//            return genderList;
//        }
//
//        @Override
//        protected void onPostExecute(List<Person.Gender> genders) {
//            super.onPostExecute(genders);
//            // iterate through the list of Genders to
//            // get the gender value (male, female, other)
//            for (Person.Gender gender : genders) {
//                String genderValue = gender.getValue();
//            }
//        }
//    }

}
