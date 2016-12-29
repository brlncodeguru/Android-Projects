package com.example.mohamed.hw06;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class RecycleViewDayAdapter extends RecyclerView.Adapter<RecycleViewDayAdapter.ViewHolder> {


    ArrayList<Weather> weathers;
    CityWeatherActivity context;

    public RecycleViewDayAdapter(ArrayList<Weather> weathers,CityWeatherActivity context) {
        this.weathers = weathers;
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        // each data item is just a string in this case

        ImageView imgIcon;
        TextView txtTime,txtCondition,txtHumidity,txtPressure,txtWind;
        TextView txtTemperature;

        public View linLayItem;
        public ViewHolder(View v) {
            super(v);
            linLayItem = v;
            txtTemperature=(TextView)v.findViewById(R.id.textViewTemperature);
            imgIcon=(ImageView)v.findViewById(R.id.imageViewIcon);
            txtCondition=(TextView)v.findViewById(R.id.textViewCondition);
            txtHumidity=(TextView)v.findViewById(R.id.textViewHumidity);
            txtPressure=(TextView)v.findViewById(R.id.textViewPressure);
            txtWind=(TextView)v.findViewById(R.id.textViewWind);
            txtTime=(TextView)v.findViewById(R.id.textViewTime);






            v.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewday_item_row, parent, false);
        return new ViewHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Weather weather=weathers.get(position);
        String url="http://openweathermap.org/img/w/"+weather.getWeatherIcon()+".png";
        Picasso.with(holder.imgIcon.getContext()).load(url).into(holder.imgIcon);
        holder.txtTime.setText(weather.time);
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        nf.setGroupingUsed(false);
        if(this.context.temperatureUnit.equalsIgnoreCase("C"))
            holder.txtTemperature.setText(weather.temperature+"\u2103");
        else
        {
            holder.txtTemperature.setText(nf.format((Double.parseDouble(weather.temperature)*9/5)+32)+"\u2109");
        }

        holder.txtCondition.setText("Condition :"+weather.condition);
        holder.txtPressure.setText("Pressure :"+weather.pressure);
        holder.txtHumidity.setText("Humidity :"+weather.humidity);
        holder.txtWind.setText("Wind :"+weather.windSspeed+","+weather.windDirection);
    }

    @Override
    public int getItemCount() {
        return weathers.size();
    }
}
