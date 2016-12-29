package com.example.mohamed.hw06;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class RecyclerFavoritesAdapter extends RecyclerView.Adapter<RecyclerFavoritesAdapter.ViewHolder>  {

    ArrayList<Note> favList;
    static MainActivity activity;
    static RecyclerView mRecyclerView;

    public RecyclerFavoritesAdapter(ArrayList<Note> favList, MainActivity activity,RecyclerView mRecyclerView) {
        this.favList = favList;
        this.activity = activity;
        this.mRecyclerView=mRecyclerView;
    }

    @Override
    public RecyclerFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerfavouritelist_item_row, parent, false);

        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerFavoritesAdapter.ViewHolder holder, int position) {
        Note fav=favList.get(position);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        if(this.activity.temperatureUnit.equalsIgnoreCase("C"))
            holder.txtTemperature.setText(fav.getTemperature()+"\u2103");
        else
        {
            holder.txtTemperature.setText(nf.format((Double.parseDouble(fav.getTemperature())*9/5)+32)+"\u2109");
        }
        holder.location.setText(fav.getCityName()+","+fav.getCountryName());
        holder.txtDate.setText("Updated on:"+fav.getUpdatedDate());
        if(fav.getFavorite().equalsIgnoreCase("YES"))
        {
            holder.imgStar.setImageResource(R.drawable.star_gold);
        }
        else
        {
            holder.imgStar.setImageResource(R.drawable.star_gray);

        }





    }



    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener,ImageView.OnClickListener{
        // each data item is just a string in this case

        TextView location;
        TextView txtDate;
        TextView txtTemperature;
        ImageView imgStar;
        public View linLayItem;
        public ViewHolder(View v) {
            super(v);
            linLayItem = v;
            txtDate=(TextView)v.findViewById(R.id.textViewDate);
            txtTemperature=(TextView)v.findViewById(R.id.textViewTemperature);
            location=(TextView)v.findViewById(R.id.textViewLocation);
            imgStar=(ImageView)v.findViewById(R.id.imgFav);
            imgStar.setOnClickListener(this);






            v.setOnLongClickListener(this);
        }



        @Override
        public boolean onLongClick(View v) {
            Log.d("Demo","OnLong Click");

            int position=mRecyclerView.getChildLayoutPosition(v);
            Note note=favList.get(position);
            favList.remove(position);
            activity.dm.deletenote(note);
            mRecyclerView.getAdapter().notifyDataSetChanged();






            return true;
        }

        @Override
        public void onClick(View v) {
            Log.d("Demo","Adapter on Click");
            Log.d("Demo","OnClick of image");
            int position=mRecyclerView.getChildLayoutPosition((View) v.getParent());
            Note note=favList.get(position);
            ImageView img=(ImageView) v;
            if(note.getFavorite().equalsIgnoreCase("YES"))
            {
                note.setFavorite("NO");
//                img.setBackgroundResource(R.drawable.star_gray);
                img.setImageResource(R.drawable.star_gray);
            }
            else
            {
                note.setFavorite("YES");

//                img.setBackgroundResource(R.drawable.star_gold);
                img.setImageResource(R.drawable.star_gold);

            }
            activity.dm.updateNote(note);
            favList= (ArrayList<Note>) activity.dm.getAllNotes();
            for(int i =0 ; i<favList.size(); i++){


                Note n1=favList.get(i);
                if(n1.getFavorite().equalsIgnoreCase("YES"))
                {
                    favList.remove(i);
                    favList.add(0,n1);
                }


            }
            mRecyclerView.getAdapter().notifyDataSetChanged();




        }
    }


    @Override
    public int getItemCount() {
        return favList.size();
    }
}
