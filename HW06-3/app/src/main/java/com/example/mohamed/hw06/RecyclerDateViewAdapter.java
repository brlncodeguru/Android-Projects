package com.example.mohamed.hw06;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.v7.widget.RecyclerView.*;
import static java.lang.Math.floor;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class RecyclerDateViewAdapter extends RecyclerView.Adapter<RecyclerDateViewAdapter.ViewHolder> implements View.OnClickListener {


    static ArrayList<WeatherDay> weatherDays;

    static RecyclerView mRecyclerView;
    static CityWeatherActivity context;


    public RecyclerDateViewAdapter(ArrayList<WeatherDay> weathers,RecyclerView rView,CityWeatherActivity context) {
        this.weatherDays = weathers;
        this.mRecyclerView=rView;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // each data item is just a string in this case

        ImageView imgCondition;
        TextView txtDate;
        TextView txtTemperature;
        public View linLayItem;
        public ViewHolder(View v) {
            super(v);
            linLayItem = v;
            txtDate=(TextView)v.findViewById(R.id.textViewDate);
            txtTemperature=(TextView)v.findViewById(R.id.textViewTemperatue);
            imgCondition=(ImageView)v.findViewById(R.id.imageViewCondition);



            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Log.d("Dem","inside view holder");

            int itemPosition = mRecyclerView.getChildLayoutPosition(v);
            WeatherDay day=weatherDays.get(itemPosition);
            LinearLayout linearLayout= (LinearLayout) context.findViewById(R.id.linLayout);
            if(linearLayout.getChildCount()==4)
            {
                linearLayout.removeViewAt(3);
                linearLayout.removeViewAt(2);

            }


            TextView textView=new TextView(context);
            LinearLayout.LayoutParams ltextViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(ltextViewParams);

            String dateText="";
            SimpleDateFormat df=new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date=df.parse(day.date);
                SimpleDateFormat wf=new SimpleDateFormat("MMM dd,yyyy");
                dateText=wf.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }



            textView.setText("Three hourly forecast for "+dateText);
            linearLayout.addView(textView);
            Log.d("demo", String.valueOf(linearLayout.getChildCount()));

            RecyclerView hourwiseRecycler = new RecyclerView(context);
            LinearLayoutManager linMgr = new LinearLayoutManager(context);
            linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

         RecycleViewDayAdapter adapter=new RecycleViewDayAdapter(day.weathers,context);

            hourwiseRecycler.setHasFixedSize(true);
            hourwiseRecycler.setAdapter(adapter);
            hourwiseRecycler.setLayoutManager(linMgr);
            hourwiseRecycler.setBackgroundColor(Color.WHITE);


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);

            hourwiseRecycler.setLayoutParams(params);
            hourwiseRecycler.setLongClickable(true);
            linearLayout.addView(hourwiseRecycler);





        }
    }

    @Override
    public RecyclerDateViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewdate_item_row, parent, false);

        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(RecyclerDateViewAdapter.ViewHolder holder, int position) {


        WeatherDay weatherDay=weatherDays.get(position);
        int numofObjs=weatherDay.weathers.size();
        int median=numofObjs/2;
        Weather weather=weatherDay.weathers.get(median);
        String temperature="";
        double temp=0.00;
        for(Weather weather1:weatherDay.weathers)
        {




            temp+=Double.valueOf(weather1.temperature);


        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        temperature=nf.format(temp/weatherDay.weathers.size());
        String dateText="";
        SimpleDateFormat df=new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date=df.parse(weather.date);
            SimpleDateFormat wf=new SimpleDateFormat("MMM dd,yyyy");
            dateText=wf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String url="http://openweathermap.org/img/w/"+weather.getWeatherIcon()+".png";
        Picasso.with(holder.imgCondition.getContext()).load(url).into(holder.imgCondition);
        holder.txtDate.setText(dateText);
        if(this.context.temperatureUnit.equalsIgnoreCase("C"))
        holder.txtTemperature.setText(temperature+"\u2103");
        else
        {
            holder.txtTemperature.setText(nf.format((Double.parseDouble(temperature)*9/5)+32)+"\u2109");
        }




    }

    @Override
    public int getItemCount() {
        return weatherDays.size();
    }

    @Override
    public void onClick(View v) {
        Log.d("Dem","outside view holder");
    }
}
