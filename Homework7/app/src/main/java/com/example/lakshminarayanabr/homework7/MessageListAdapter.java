package com.example.lakshminarayanabr.homework7;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.ocpsoft.pretty.time.PrettyTime;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lakshminarayanabr on 11/21/16.
 */

public class MessageListAdapter extends ArrayAdapter<UserMessage> {
    Context context;
    int resID;
    List<UserMessage> objects;
    User loggedinUser;
    User friendUser;


    public MessageListAdapter(Context context, int resource, List<UserMessage> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resID=resource;
        this.objects=objects;
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resID,parent,false);
        }
        final UserMessage msgDetail=objects.get(position);

        if(msgDetail.readStatus.equalsIgnoreCase("unread")&&msgDetail.toUserId.equals(loggedinUser.userid))
        {
            convertView.setBackgroundColor(Color.GREEN);
        }
        else
        {
            convertView.setBackgroundColor(Color.WHITE);

        }

        TextView txtMessage=(TextView)convertView.findViewById(R.id.tvmessage);
        TextView txtName=(TextView)convertView.findViewById(R.id.tvName);
        TextView txtTime=(TextView)convertView.findViewById(R.id.tvTime);


        ImageView imgView=(ImageView)convertView.findViewById(R.id.fileImage);

        if(msgDetail.fromUserId.equals(loggedinUser.userid))
        {
            txtName.setText("You");
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity= Gravity.RIGHT;
            txtMessage.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.gravity= Gravity.RIGHT;

            imgView.setLayoutParams(layoutParams1);




        }
        else
        {
            txtName.setText(friendUser.getFname()+" "+friendUser.getLname());

            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.gravity= Gravity.LEFT;
            txtMessage.setLayoutParams(layoutParams);

            LinearLayout.LayoutParams layoutParams1=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams1.gravity= Gravity.LEFT;
            imgView.setLayoutParams(layoutParams1);



        }



        PrettyTime p=new PrettyTime();
        txtTime.setText(p.format(new Date(Long.parseLong(msgDetail.CreatedAt))));







        if(msgDetail.Type.equalsIgnoreCase("IMAGE"))
        {

            txtMessage.setVisibility(View.GONE);
            imgView.setVisibility(View.VISIBLE);

            Picasso.with(context).load(msgDetail.FileThumbnailId).into(imgView);




        }
        else if (msgDetail.Type.equalsIgnoreCase("TEXT"))
        {
            txtMessage.setVisibility(View.VISIBLE);
            imgView.setVisibility(View.GONE);
            txtMessage.setText(msgDetail.Comment);




        }
        else
        {

        }

        Log.d("COMMENT",msgDetail.toString());









        return convertView;

    }

}
