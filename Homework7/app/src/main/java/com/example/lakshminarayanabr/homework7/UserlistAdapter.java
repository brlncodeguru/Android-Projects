package com.example.lakshminarayanabr.homework7;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lakshminarayanabr on 11/20/16.
 */

public class UserlistAdapter extends ArrayAdapter<User> {
    Context context;
    int res;
    List<User> userList;

    public UserlistAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
        this.context=context;
        this.res=resource;
        this.userList=objects;



    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(res,parent,false);
        }
        User user=userList.get(position);
        ImageView img=(ImageView)convertView.findViewById(R.id.imageViewProfilePic);
        TextView txt=(TextView)convertView.findViewById(R.id.textViewFullName);

        txt.setText(user.getFname()+" "+user.getLname());

        if(user.getProfilePicURL()==null||user.getProfilePicURL().length()<=0)
        {
            img.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
        }
        else
        {
            Picasso.with(context).load(user.getProfilePicURL()).into(img);

        }




        return convertView;
    }
}
