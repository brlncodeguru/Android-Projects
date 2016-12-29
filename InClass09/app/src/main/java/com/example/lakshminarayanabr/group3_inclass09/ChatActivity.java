package com.example.lakshminarayanabr.group3_inclass09;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ChatActivity extends AppCompatActivity implements  APICaller.ListMessagesInterface {

    TextView txtName;
    ImageView logout,send,gallery;

    ListView listView;

    EditText editTextMessage;

    String token;
    APICaller caller=new APICaller(this);

    User user;
    MessagesListAdapter adapter;

    public static  final  int SELECT_PICTURE_REQUEST=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtName=(TextView)findViewById(R.id.textViewName);
        logout=(ImageView)findViewById(R.id.imageViewLogout);
        send=(ImageView)findViewById(R.id.imageViewSend);
        gallery=(ImageView)findViewById(R.id.imageViewGallery);
        listView=(ListView)findViewById(R.id.ListViewMessages);


        editTextMessage=(EditText)findViewById(R.id.editTextMessage);

        if(getIntent().getExtras().getSerializable(MainActivity.USER_OBJ)!=null)
        {
            user=(User)getIntent().getExtras().getSerializable(MainActivity.USER_OBJ);

            txtName.setText(user.getUserFname()+" "+user.getUserLname());

            caller.getMessages(getSharedPreferences());

        }


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.CHATAPP,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.remove(MainActivity.TOKEN_STR);
                editor.commit();

                finish();



            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                caller.addTextorImage(getSharedPreferences(),"TEXT",editTextMessage.getText().toString(),"");






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









    }

    public void storePrefrences(String converted )
    {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.CHATAPP,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(MainActivity.TOKEN_STR,converted);
        editor.commit();

    }

    public String getSharedPreferences()
    {
        SharedPreferences sharedPreferences=getSharedPreferences(MainActivity.CHATAPP,MODE_PRIVATE);
        String edited=sharedPreferences.getString(MainActivity.TOKEN_STR,"No Token Available");
        return edited;
    }

    @Override
    public void setMessagesToList(final List<ChatMessageDetail> messages) {


        runOnUiThread(new Runnable() {
            public void run() {
                adapter=new MessagesListAdapter(ChatActivity.this,R.layout.rowitemmessages,messages);
                listView=(ListView)findViewById(R.id.ListViewMessages);
                adapter.token=getSharedPreferences();


                listView.setAdapter(adapter);

            }
        });




    }

    Uri uri_global;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("data", data.getDataString());
        Log.d("reg", String.valueOf(requestCode));
        Log.d("sel", String.valueOf(resultCode));
        if (requestCode == SELECT_PICTURE_REQUEST) {
            if (resultCode == RESULT_OK) {
                uri_global = data.getData();
                Log.d("debug", uri_global.toString());

                uri_global = data.getData();
//                photo = (Bitmap) data.getExtras().get("data");

                // Cursor to get image uri to display


                caller.addImage(getSharedPreferences(),getPath(uri_global));
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
