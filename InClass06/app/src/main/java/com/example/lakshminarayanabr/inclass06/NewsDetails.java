package com.example.lakshminarayanabr.inclass06;

/**
 * Created by lakshminarayanabr on 9/27/16.
 */
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class NewsDetails extends AppCompatActivity implements GetImageAsyncTask.iImage {

    TextView desc;
    ImageView image;

    TextView title,pubDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        desc=(TextView)findViewById(R.id.descriptionText);
        image=(ImageView)findViewById(R.id.imageView);
        title=(TextView)findViewById(R.id.storyTitle);
        pubDate=(TextView)findViewById(R.id.storyDate);






        if(getIntent().getExtras().getParcelable("News")!=null)
        {



            NewsItem news=getIntent().getExtras().getParcelable("News");
            Log.d("demo", news.toString());
            desc.setText(news.getDescription());
            Log.d("Demo",news.getLargeImage().toString());

            new GetImageAsyncTask(this,image).execute(news.getLargeImage());
            Date date=null;
            String formattedDate="";
            SimpleDateFormat fmt = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
            fmt.setTimeZone(TimeZone.getTimeZone("GMT"));

            SimpleDateFormat writeFormat=new SimpleDateFormat("MM/dd/yyyy HH:mm a");
            writeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            try {
                if(news.getDate()!=null)
                formattedDate = writeFormat.format(fmt.parse(news.getDate()));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            title.setText(news.getTitle());

//            Log.d("Demo",date.toString());

            pubDate.setText(formattedDate);


            Log.d("Demo",news.getDate());


        }

        else
        {
            Log.d("Demo","Null");
        }


    }

    @Override
    public void setImage( Bitmap b, ImageView image) {
        image.setImageBitmap(b);
    }
}
