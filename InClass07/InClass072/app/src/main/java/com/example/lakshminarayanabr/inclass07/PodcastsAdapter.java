package com.example.lakshminarayanabr.inclass07;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshminarayanabr on 10/3/16.
 */
public class PodcastsAdapter extends ArrayAdapter<Podcast> {

    ArrayList<Podcast> originalList;
    ArrayList<Podcast> displayedList;

    List<Integer> positions;

    int count;




    Context context;
    List<Podcast> objects;
    int res;
    public PodcastsAdapter(Context context, int resource, List<Podcast> objects) {


        super(context, resource, objects);

        this.count=-1;

        this.context=context;
        this.res=resource;
        this.objects=objects;


    }

    @Override
    public void insert(Podcast object, int index) {
        count++;

        super.insert(object, index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(res,parent,false);
        }
        Podcast pod=objects.get(position);


            if(position<=count)
            convertView.setBackgroundColor(Color.GREEN);



        ImageView img= (ImageView) convertView.findViewById(R.id.imageViewThumbNail);


        TextView txttitle=(TextView)convertView.findViewById(R.id.textViewTitle);
        txttitle.setText(pod.title);
        Picasso.with(context).load(pod.thumbnailURL).into(img);








        return convertView;


    }

    public void resetCount(){
        count=-1;
    }


    public void search(CharSequence seq)
    {
        originalList= (ArrayList<Podcast>) objects;
        for(int i=0;i<objects.size();i++)
        {
            Podcast pod=objects.get(i);
            String[] words=pod.title.split(" ");

            for(int j=0;j<words.length;j++)
            {

                if(words.equals(seq))
                {
                    objects.remove(i);




                }

            }
            //if(pod.title.split(" "))


        }
       // if(seq.equals())

    }
}
