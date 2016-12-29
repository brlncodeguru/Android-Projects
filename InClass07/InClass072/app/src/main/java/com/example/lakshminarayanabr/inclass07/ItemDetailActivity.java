package com.example.lakshminarayanabr.inclass07;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ItemDetailActivity extends AppCompatActivity {

    TextView title,dateText,summaryText;
    ImageView largeImage;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssz");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        largeImage=(ImageView)findViewById(R.id.imageViewLargeImage);

        title=(TextView)findViewById(R.id.textViewTitle);
        dateText=(TextView)findViewById(R.id.textViewDate);
        summaryText=(TextView)findViewById(R.id.textViewSummaryText);
        if(getIntent().getSerializableExtra("Podcast")!=null)
        {


            Podcast pod=(Podcast)getIntent().getSerializableExtra("Podcast");
            title.setText(pod.title);

            Date date= null;
            SimpleDateFormat writeFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm a");
            String relDate=" ";
            try {
                date = df.parse(pod.releaseDate);
                relDate=writeFormat.format(date);

            } catch (ParseException e) {
                e.printStackTrace();
            }
            dateText.setText("Released on  "+relDate);

            summaryText.setText(pod.summary);

            Picasso.with(this).load(pod.LargeImageURL).into(largeImage);


        }

    }
}
