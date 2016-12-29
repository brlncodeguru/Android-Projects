package com.example.lakshminarayanabr.homework7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextView txtFullName,txtChatFriends;

    ImageView imgMyPic;

    ArrayList<User> userArrayList;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    DatabaseReference mUserRef=mRootRef.child("ChatUser");

    FirebaseUser user;

    UserlistAdapter adapter;

    GoogleApiClient apiClient;
    String typeofauth;

    ListView listViewUser;

    public static final String USER_OBJ="USEROBJ";
    public static final String LOGGED_USER_OBJ="LOGGEDINUSEROBJ";




    User loggedinUser;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.menu_editprofile)
        {
            Intent intEdit=new Intent(UserListActivity.this,EditUserActivity.class);
            intEdit.putExtra(LOGGED_USER_OBJ,loggedinUser);
            startActivity(intEdit);

        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if(typeofauth.equals("facebook")||typeofauth.equals("google"))
        {

            return  false;

        }
        else
        {
            getMenuInflater().inflate(R.menu.usereditmenu, menu);
        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        user=FirebaseAuth.getInstance().getCurrentUser();
        userArrayList=new ArrayList<>();
        listViewUser=(ListView)findViewById(R.id.listViewMessages);

        typeofauth=getSharedPreferences();

        Log.d("userlist","OnCreateCalled");

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("311996157836-o0a8047a9gidiibfhimeu7gm0q5olfrr.apps.googleusercontent.com").requestEmail().build();

        apiClient=new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


//        if(getIntent().getExtras().getParcelableArrayList(MainActivity.USER_LIST)!=null)
//        {
//            userArrayList=getIntent().getExtras().getParcelableArrayList(MainActivity.USER_LIST);
//            Log.d("userFrnds",userArrayList.toString());
//            int index=-1;
//            for(User usr:userArrayList)
//            {
//                User user1=new User();
//                String[] names=user.getDisplayName().split(" ");
//                usr.fname=names[0];
//                usr.lname=names[1];
//                usr.setProfilePicURL(user.getPhotoUrl().toString());
//                usr.email=user.getEmail();
//                if(usr.equals(user1))
//                {
//                    index=userArrayList.indexOf(usr);
//
//                }
//            }
//            if (index>=0)
//            {
//                loggedinUser=userArrayList.remove(index);
//            }
//
//        }


        adapter=new UserlistAdapter(this,R.layout.userrowitem,userArrayList);
        listViewUser.setAdapter(adapter);

        txtFullName=(TextView)findViewById(R.id.textViewFullName);
        imgMyPic=(ImageView)findViewById(R.id.imageViewMyPic);

//        if(user!=null)
//        {
//            txtFullName.setText("Welcome "+user.getDisplayName());
//            if(user.getPhotoUrl()==null||user.getPhotoUrl().toString().length()<=0)
//            {
//                imgMyPic.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
//            }
//            else
//                Picasso.with(this).load(user.getPhotoUrl()).into(imgMyPic);
////            if(loggedinUser!=null)
////            txtChatFriends.setText("Gender:"+loggedinUser.gender);
//
//
//
//        }


        imgMyPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mUserRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("mUSerRef","UserListActivity");
                user=FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {

                    Log.d("DemoUser",user.getEmail());
                    if(user.getDisplayName()!=null&&user.getEmail()!=null)
                    {
                        User usr=new User();
                        if(user.getDisplayName()!=null)
                        {
                            String[] names=user.getDisplayName().split(" ");
                            usr.fname=names[0];
                            usr.lname=names[1];

                        }
                        if(user.getPhotoUrl()!=null)
                            usr.setProfilePicURL(user.getPhotoUrl().toString());
                        usr.email=user.getEmail();
                        User user1=dataSnapshot.getValue(User.class);
                        if(usr.equals(user1))
                        {
                            loggedinUser=user1;
//                            if(loggedinUser.gender!=null)
//                                txtChatFriends.setText("Gender:"+loggedinUser.gender);
                            loggedinUser = user1;
                            txtFullName.setText("Welcome "+loggedinUser.getFname()+" "+loggedinUser.getLname());

                            if(loggedinUser.getProfilePicURL()==null||loggedinUser.getProfilePicURL().toString().length()<=0)
                            {
                                imgMyPic.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                            }
                            else
                                Picasso.with(UserListActivity.this).load(loggedinUser.getProfilePicURL()).into(imgMyPic);


                        }

                        if(!userArrayList.contains(user1)&&!usr.equals(user1))
                        {
                            userArrayList.add(user1);

                            if(adapter!=null)
                            {
                                adapter.notifyDataSetChanged();
                            }
                            else
                            {
                                adapter=new UserlistAdapter(UserListActivity.this,R.layout.userrowitem,userArrayList);

                            }
                        }

                    }



                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (user.getDisplayName() != null && user.getEmail() != null) {
                        User usr = new User();
                        if (user.getDisplayName() != null) {
                            String[] names = user.getDisplayName().split(" ");
                            usr.fname = names[0];
                            usr.lname = names[1];

                        }
                        if (user.getPhotoUrl() != null)
                            usr.setProfilePicURL(user.getPhotoUrl().toString());
                        usr.email = user.getEmail();
                        User user1 = dataSnapshot.getValue(User.class);
                        if (usr.equals(user1)) {
                            loggedinUser = user1;
                            txtFullName.setText("Welcome "+loggedinUser.getFname()+" "+loggedinUser.getLname());

                            if(loggedinUser.getProfilePicURL()==null||loggedinUser.getProfilePicURL().toString().length()<=0)
                            {
                                imgMyPic.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                            }
                            else
                                Picasso.with(UserListActivity.this).load(loggedinUser.getProfilePicURL()).into(imgMyPic);

                           // txtChatFriends.setText("Gender:"+loggedinUser.gender);
                        }


                    }


                }
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

     listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             User usr=userArrayList.get(position);
             Intent userMessages=new Intent(UserListActivity.this,UserMessagesActivity.class);
             userMessages.putExtra(USER_OBJ,usr);
             userMessages.putExtra(LOGGED_USER_OBJ,loggedinUser);
             startActivity(userMessages);



         }
     });


    }

    @Override
    public void onBackPressed() {
//        FirebaseAuth.getInstance().signOut();

        if(typeofauth.equalsIgnoreCase("facebook"))
        {
            FirebaseAuth.getInstance().signOut();
            LoginManager.getInstance().logOut();
            finish();
        }
        else if(typeofauth.equalsIgnoreCase("google"))
        {
            Auth.GoogleSignInApi.signOut(apiClient).setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(@NonNull Status status) {
                    finish();

                }
            });
        }
        else
        {
            FirebaseAuth.getInstance().signOut();
            finish();
        }


        storeTypeofAuthPrefrences("");




    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
        String edited=sharedPreferences.getString("TypeofAuth","No Type of Auth Available");
        return edited;
    }

}
