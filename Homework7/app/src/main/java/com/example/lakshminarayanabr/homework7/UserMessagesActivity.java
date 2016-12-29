package com.example.lakshminarayanabr.homework7;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class UserMessagesActivity extends AppCompatActivity {

    TextView txtName;
    ImageView imgProfilePic;

    ImageView imgSendMessage;
    ImageView imgUploadPic;

    ListView listViewMessages;

    EditText editTextMessage;

    ArrayList<UserMessage> userMessageArrayList;

    FirebaseUser user;

    User loogedInUser;
    User friendUser;


    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();

    FirebaseStorage mStorage=FirebaseStorage.getInstance();


    DatabaseReference mMessagesReference=mRootRef.child("ChatMessages");
    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://firstfirebase-822f5.appspot.com");

    MessageListAdapter messageListAdapter;



    public static  final  int SELECT_PICTURE_MESSAGE_REQUEST=200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_messages);

        txtName=(TextView)findViewById(R.id.textViewFriendFullName);

        imgProfilePic=(ImageView)findViewById(R.id.imageViewFriendPic);

        imgSendMessage=(ImageView)findViewById(R.id.imageViewSendMesssage);

        imgUploadPic=(ImageView)findViewById(R.id.imageViewImageUpload);

        editTextMessage=(EditText)findViewById(R.id.editTextMessage);

        listViewMessages=(ListView)findViewById(R.id.listViewMessages);
        userMessageArrayList=new ArrayList<>();


        if(getIntent().getExtras().getParcelable(UserListActivity.USER_OBJ)!=null&&getIntent().getExtras().getParcelable(UserListActivity.LOGGED_USER_OBJ)!=null)
        {
            loogedInUser=getIntent().getExtras().getParcelable(UserListActivity.LOGGED_USER_OBJ);
            friendUser=getIntent().getExtras().getParcelable(UserListActivity.USER_OBJ);

            txtName.setText(friendUser.getFname()+" "+friendUser.getLname());
            if(friendUser.getProfilePicURL()==null||friendUser.getProfilePicURL().toString().length()<=0)
            {
                imgProfilePic.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
            }
            else
                Picasso.with(this).load(friendUser.getProfilePicURL()).into(imgProfilePic);

            Log.d("LoggedinUser",loogedInUser.toString());
            Log.d("friendUser",friendUser.toString());




        }
        mMessagesReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                UserMessage message=dataSnapshot.getValue(UserMessage.class);
                if(!userMessageArrayList.contains(message))
                {
                    if((message.fromUserId.equals(loogedInUser.userid)||message.fromUserId.equals(friendUser.userid))&&(message.toUserId.equals(loogedInUser.userid)||message.toUserId.equals(friendUser.userid)))
                    userMessageArrayList.add(message);
                }
                if(messageListAdapter!=null)
                    messageListAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                UserMessage message=dataSnapshot.getValue(UserMessage.class);
                if(userMessageArrayList.contains(message))
                {
                    if((message.fromUserId.equals(loogedInUser.userid)||message.fromUserId.equals(friendUser.userid))&&(message.toUserId.equals(loogedInUser.userid)||message.toUserId.equals(friendUser.userid))) {
                        int index=userMessageArrayList.indexOf(message);
                        userMessageArrayList.set(index,message);
                    }

                }
                if(messageListAdapter!=null)
                    messageListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                UserMessage message=dataSnapshot.getValue(UserMessage.class);
                if(userMessageArrayList.contains(message))
                {
                    if((message.fromUserId.equals(loogedInUser.userid)||message.fromUserId.equals(friendUser.userid))&&(message.toUserId.equals(loogedInUser.userid)||message.toUserId.equals(friendUser.userid)))
                        userMessageArrayList.remove(message);
                }
                if(messageListAdapter!=null)
                    messageListAdapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        messageListAdapter=new MessageListAdapter(UserMessagesActivity.this,R.layout.messagerowitem,userMessageArrayList);
        messageListAdapter.loggedinUser=loogedInUser;
        messageListAdapter.friendUser=friendUser;
        listViewMessages.setAdapter(messageListAdapter);

        listViewMessages.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                UserMessage userMessage=userMessageArrayList.get(position);
                if(loogedInUser.userid.equals(userMessage.fromUserId))
                {
                    String str = userMessage.Id;
                    mMessagesReference.child(str).setValue(null);
                }





                return false;
            }
        });

        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                UserMessage userMessage=userMessageArrayList.get(position);

                if(userMessage.readStatus.equalsIgnoreCase("unread")&&userMessage.toUserId.equals(loogedInUser.userid))
                {
                    userMessage.readStatus="read";
                    String str = userMessage.Id;
                    mMessagesReference.child(str).setValue(userMessage);

                }


            }
        });







        imgSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextMessage.getText().toString().length()>0)
                {
                    UserMessage msgDetail=new UserMessage();
                    msgDetail.Comment=editTextMessage.getText().toString();
                    msgDetail.fromUserId=loogedInUser.getUserid();
                    msgDetail.toUserId=friendUser.getUserid();
//                    msgDetail.UserId=user.getEmail();
                    msgDetail.Type="Text";
                    msgDetail.CreatedAt=String.valueOf(Calendar.getInstance().getTimeInMillis());
                    msgDetail.readStatus="unread";
//                    msgDetail.username=user.getDisplayName();

                    DatabaseReference reference=mMessagesReference.push();
                    msgDetail.Id=reference.getKey();
                    reference.setValue(msgDetail);


                    editTextMessage.setText("");

                }




            }
        });

        imgUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_PICTURE_MESSAGE_REQUEST);
                Log.d("sel", "Selected1");

            }
        });







    }
    Uri uri_global;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE_MESSAGE_REQUEST) {
            if (resultCode == RESULT_OK) {
                uri_global = data.getData();
                Log.d("debug", uri_global.toString());

                uri_global = data.getData();
                // Bitmap photo = (Bitmap) data.getExtras().get("data");

                StorageReference imagesRef = storageRef.child("images/"+uri_global.getLastPathSegment());
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
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();

                        UserMessage msgDetail=new UserMessage();

                        msgDetail.fromUserId=loogedInUser.getUserid();
                        msgDetail.toUserId=friendUser.getUserid();
//                    msgDetail.UserId=user.getEmail();
                        msgDetail.Type="Image";
                        msgDetail.CreatedAt=String.valueOf(Calendar.getInstance().getTimeInMillis());
                        msgDetail.FileThumbnailId=downloadUrl.toString();
                        msgDetail.readStatus="unread";
//                    msgDetail.username=user.getDisplayName();








                        DatabaseReference reference=mMessagesReference.push();
                        msgDetail.Id=reference.getKey();
                        reference.setValue(msgDetail);






                    }
                });






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
