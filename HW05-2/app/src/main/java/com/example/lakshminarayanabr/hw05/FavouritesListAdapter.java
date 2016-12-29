package com.example.lakshminarayanabr.hw05;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lakshminarayanabr on 10/8/16.
 */
public class FavouritesListAdapter extends ArrayAdapter <Weather>{
    Context context;
    List<Weather> objects;
    int res;

    public FavouritesListAdapter(Context context, int resource, List<Weather> objects) {
        super(context, resource, objects);
        this.context=context;
        this.res=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(res,parent,false);
        }
        Weather pod=objects.get(position);

        TextView txttitle=(TextView)convertView.findViewById(R.id.textViewTemp);
        txttitle.setText(pod.temperature+" \u2109");
        TextView txttitl=(TextView)convertView.findViewById(R.id.textViewCity);
        txttitl.setText(pod.cityName+",");
        TextView txttit=(TextView)convertView.findViewById(R.id.textViewState);
        txttit.setText(pod.stateNmae);
        TextView txtti=(TextView)convertView.findViewById(R.id.textViewDate);
        txtti.setText("Updated on :"+pod.date);










        return convertView;





    }
}
