package com.example.lakshminarayanabr.midtermexamapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class TopListAdapter extends ArrayAdapter<AppObject> {
    int resID;
    Context activity;
    List<AppObject> objects;


    public TopListAdapter(Context context, int resource, List<AppObject> objects) {
        super(context, resource, objects);

        this.resID=resource;
        this.activity=context;
        this.objects=objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)activity.getSystemService(activity.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resID,parent,false);
        }
        AppObject object=objects.get(position);

        ImageView img= (ImageView) convertView.findViewById(R.id.imageViewThumbNail);
        TextView txtAppName=(TextView)convertView.findViewById(R.id.textViewAppName);
        TextView txtPrice=(TextView)convertView.findViewById(R.id.textViewPrice);

        ImageView imgDollar=(ImageView)convertView.findViewById(R.id.imageViewDollar);

        txtAppName.setText(object.appName);
        txtPrice.setText("Price:"+object.getPriceUNIT()+" "+object.getPrice());
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        Double priceDoubleVal=Double.parseDouble(object.price);

        if(Double.parseDouble(nf.format(priceDoubleVal))>=0&&Double.parseDouble(nf.format(priceDoubleVal))<=1.99)
        {
            imgDollar.setImageResource(R.mipmap.price_low);

        }
        else if(Double.parseDouble(nf.format(priceDoubleVal))>=2.00&&Double.parseDouble(nf.format(priceDoubleVal))<=5.99)
        {
            imgDollar.setImageResource(R.mipmap.price_medium);

        }
        else
        {
            imgDollar.setImageResource(R.mipmap.price_high);

        }

        Picasso.with(activity).load(object.getThumbnailImageURL()).into(img);








        return convertView;
    }
}
