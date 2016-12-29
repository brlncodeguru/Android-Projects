package com.example.lakshminarayanabr.inclass11;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatRoomActivity extends AppCompatActivity {
    TextView txtName;
    ImageView logout,send,gallery;

    ListView listView;

    EditText editTextMessage;

    String token;



    MessageListAdapter adapter;

    DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    DatabaseReference mMessagesReference;

    FirebaseStorage mStorage=FirebaseStorage.getInstance();


    ArrayList<MessageDetail> msgList;

    StorageReference storageRef = mStorage.getReferenceFromUrl("gs://firstfirebase-822f5.appspot.com");


    public static  final  int SELECT_PICTURE_REQUEST=100;
    @Override
    protected void onStop() {
        super.onStop();
        user=null;


    }

    @Override
    protected void onStart() {
        super.onStart();
        //user= FirebaseAuth.getInstance().getCurrentUser();
        msgList=new ArrayList<MessageDetail>();
        mMessagesReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                user= FirebaseAuth.getInstance().getCurrentUser();

                final MessageDetail msgDetail=dataSnapshot.getValue(MessageDetail.class);
                DatabaseReference mPostCommentsreference=mMessagesReference.child("PostComments");
                mPostCommentsreference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Comment comment=dataSnapshot.getValue(Comment.class);
                        if(!msgDetail.postcomments.contains(comment))
                            msgDetail.postcomments.add(comment);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(!msgList.contains(msgDetail))
                    msgList.add(msgDetail);

                Log.d("DemoE",msgList.toString());
                if(adapter!=null)
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                MessageDetail msgDetail=dataSnapshot.getValue(MessageDetail.class);
//                if(!msgList.contains(msgDetail)) {
//                    msgList.remove(msgDetail);
//                    msgList.add(msgDetail);
//
//                }
//
//                Log.d("DemoE",msgList.toString());
//                if(adapter!=null)
//                    adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                user= FirebaseAuth.getInstance().getCurrentUser();
                final MessageDetail msgDetail=dataSnapshot.getValue(MessageDetail.class);
                DatabaseReference mPostCommentsreference=mMessagesReference.child("PostComments");
                mPostCommentsreference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Comment comment=dataSnapshot.getValue(Comment.class);
                        if(!msgDetail.postcomments.contains(comment))
                            msgDetail.postcomments.add(comment);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                if(!msgList.contains(msgDetail))
                    msgList.remove(msgDetail);
                Log.d("DemoE",msgList.toString());
                if(adapter!=null)
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });







    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        mMessagesReference=mRootRef.child("Messages");
        msgList=new ArrayList<MessageDetail>();
        txtName=(TextView)findViewById(R.id.textViewName);
        logout=(ImageView)findViewById(R.id.imageViewLogout);
        send=(ImageView)findViewById(R.id.imageViewSend);
        gallery=(ImageView)findViewById(R.id.imageViewGallery);
        listView=(ListView)findViewById(R.id.ListViewMessages);

        txtName.setText(user.getDisplayName());


        editTextMessage=(EditText)findViewById(R.id.editTextMessage);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                finish();



            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDetail msgDetail=new MessageDetail();
                msgDetail.Comment=editTextMessage.getText().toString();
                msgDetail.UserId=user.getEmail();
                msgDetail.Type="Text";
                msgDetail.CreatedAt=String.valueOf(Calendar.getInstance().getTimeInMillis());
                msgDetail.username=user.getDisplayName();






                DatabaseReference reference=mMessagesReference.push();
                msgDetail.Id=reference.getKey();
                reference.setValue(msgDetail);


                editTextMessage.setText("");















            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_PICTURE_REQUEST);
                Log.d("sel", "Selected1");

            }
        });

        mMessagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                msgList.clear();
                for(DataSnapshot msgSnapshot:dataSnapshot.getChildren())
                {

                    final MessageDetail msgDetail=msgSnapshot.getValue(MessageDetail.class);

                    DatabaseReference mPostCommentsreference=mMessagesReference.child("PostComments");
                    mPostCommentsreference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Comment comment=dataSnapshot.getValue(Comment.class);
                            if(!msgDetail.postcomments.contains(comment))
                                msgDetail.postcomments.add(comment);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    if(!msgList.contains(msgDetail))
                    msgList.add(msgDetail);

                }

                adapter=new MessageListAdapter(ChatRoomActivity.this,R.layout.rowitemmessage,msgList);
                listView=(ListView)findViewById(R.id.ListViewMessages);
                adapter.firebaseUser=user;




                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }
    Uri uri_global;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE_REQUEST) {
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

                        MessageDetail msgDetail=new MessageDetail();

                        msgDetail.UserId=user.getEmail();
                        msgDetail.Type="Image";
                        msgDetail.CreatedAt=String.valueOf(Calendar.getInstance().getTimeInMillis());
                        msgDetail.username=user.getDisplayName();
                        msgDetail.FileThumbnailId=downloadUrl.toString();







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

    public void sendCommentForMessage(MessageDetail msgDetail,String comment)
    {
        DatabaseReference childObjRef=mMessagesReference.child(msgDetail.Id);
        DatabaseReference ref=childObjRef.child("PostComments").push();

        Comment com=new Comment();
        com.id=ref.getKey();
        com.comment=comment;
        com.username=msgDetail.username;
        com.userEmail=msgDetail.UserId;
        com.time=String.valueOf(Calendar.getInstance().getTimeInMillis());


        ref.setValue(com);









    }

    private void removeMessage(MessageDetail msg) {
        for (int i = 0; i < adapter.getCount(); i++) {
            if (msg.Id.equals(adapter.getItem(i).Id)) {
                adapter.remove(adapter.getItem(i));
                break;
            }
        }
    }

    public  void deleteMessage(MessageDetail msgDetail)
    {
        String str = msgDetail.Id;
        mMessagesReference.child(str).setValue(null);

    }
}
