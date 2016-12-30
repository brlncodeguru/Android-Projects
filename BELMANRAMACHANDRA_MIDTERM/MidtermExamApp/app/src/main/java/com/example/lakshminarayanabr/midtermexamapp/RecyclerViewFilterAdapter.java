package com.example.lakshminarayanabr.midtermexamapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class RecyclerViewFilterAdapter extends RecyclerView.Adapter<RecyclerViewFilterAdapter.ViewHolder> {
    ArrayList<AppObject> filterList;
    static MainActivity activity;
    static RecyclerView mRecyclerView;

    public RecyclerViewFilterAdapter(ArrayList<AppObject> filterList,MainActivity activity,RecyclerView mRecyclerView) {
        this.filterList = filterList;
        this.activity=activity;
        this.mRecyclerView=mRecyclerView;
    }

    @Override
    public RecyclerViewFilterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewitembottom, parent, false);

        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewFilterAdapter.ViewHolder holder, int position) {
        AppObject object=filterList.get(position);


        holder.txtAppName.setText(object.appName);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);

        Double priceDoubleVal=Double.parseDouble(object.price);
        holder.txtPrice.setText("Price:"+object.getPriceUNIT()+" "+String.valueOf(priceDoubleVal));

        if(Double.parseDouble(nf.format(priceDoubleVal))>=0&&Double.parseDouble(nf.format(priceDoubleVal))<=1.99)
        {
            holder.imgDollar.setImageResource(R.mipmap.price_low);

        }
        else if(Double.parseDouble(nf.format(priceDoubleVal))>=2.00&&Double.parseDouble(nf.format(priceDoubleVal))<=5.99)
        {
            holder.imgDollar.setImageResource(R.mipmap.price_medium);

        }
        else
        {
            holder.imgDollar.setImageResource(R.mipmap.price_high);

        }

        Picasso.with(activity).load(object.getThumbnailImageURL()).into(holder.imgThumbnail);

        holder.imgTrash.setImageResource(R.mipmap.delete_icon);



    }

    @Override
    public int getItemCount() {
        return filterList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements ImageView.OnClickListener {
        // each data item is just a string in this case

        TextView txtAppName;
        TextView txtPrice;
        ImageView imgDollar;
        ImageView imgTrash;
        public View linLayItem;
        ImageView imgThumbnail;


        public ViewHolder(View v) {
            super(v);
            linLayItem = v;
           txtAppName=(TextView)v.findViewById(R.id.textViewAppName);
            txtPrice=(TextView)v.findViewById(R.id.textViewPrice);
            imgDollar=(ImageView)v.findViewById(R.id.imageViewDollarSymbol);
            imgThumbnail=(ImageView)v.findViewById(R.id.imageViewLargeImage);


            imgTrash=(ImageView)v.findViewById(R.id.imageViewTrash);
            imgTrash.setOnClickListener(this);




        }




        @Override
        public void onClick(View v) {
            int position=mRecyclerView.getChildLayoutPosition((View) v.getParent());
            AppObject obj=filterList.get(position);
            activity.dm.deletenote(obj);

            filterList=new ArrayList<>(activity.dm.getAllNotes());
            if (filterList.size()>0) {
                activity.txtErrorMsg.setVisibility(View.INVISIBLE);
            }
            else
            {
                activity.txtErrorMsg.setVisibility(View.VISIBLE);
            }
            activity.appObjectArrayList.add(obj);
            activity.adapter.notifyDataSetChanged();
            mRecyclerView.getAdapter().notifyDataSetChanged();


        }
    }
}
