package com.example.lakshminarayanabr.inclass06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements GetNewsAsyncTask.IQueData,GetImageAsyncTask.iImage{

    HashMap<Integer,NewsItem> mp=new HashMap<>();

    RelativeLayout relLayout;
    ScrollView scrollView;
    LinearLayout linearLayout;
    ImageView image;
    TextView textView;
    LinearLayout linLayout2;
    ArrayList<NewsItem> newsItems;

    ProgressBar pgLoadingthumbNail;

    ProgressDialog pgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Loading News");
        pgDialog.show();





        relLayout = new RelativeLayout(this);
        relLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        //ScrollView
        scrollView =new ScrollView(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.setLayoutParams(layoutParams);


        relLayout.addView(scrollView);
//        setContentView(relLayout);

        newsItems = new ArrayList<>();

        new GetNewsAsyncTask(this).execute("http://rss.cnn.com/rss/cnn_tech.rss");

    }

    View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intDetail=new Intent(MainActivity.this,NewsDetails.class);

            intDetail.putExtra("News",mp.get(view.getId()));
            startActivity(intDetail);
        }
    };


    public void createNewsItem(ArrayList<NewsItem> questions){
        pgDialog.dismiss();
        newsItems=questions;


        Collections.sort(newsItems,NewsItem.dateOrder);

        linLayout2=new LinearLayout(this);
        LinearLayout.LayoutParams lbParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linLayout2.setLayoutParams(lbParams);
        scrollView.addView(linLayout2);
        for (NewsItem newsItem: newsItems
                ) {



            LinearLayout linearLayout = new LinearLayout(MainActivity.this);
            LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(lParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setId(newsItems.indexOf(newsItem));
            image = new ImageView(MainActivity.this);
            LinearLayout.LayoutParams limageParams = new LinearLayout.LayoutParams(150,150);
            image.setLayoutParams(limageParams);
//            image.setId(ne);

            textView=new TextView(this);
            LinearLayout.LayoutParams ltextViewParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            textView.setLayoutParams(ltextViewParams);

            textView.setText(newsItem.title);


            linearLayout.addView(image);
            linearLayout.addView(textView);

            new GetImageAsyncTask(MainActivity.this,image).execute(newsItem.getThumbnailImage());


            ((LinearLayout)findViewById(R.id.linLayoutHoriz)).addView(linearLayout);

            mp.put(linearLayout.getId(),newsItem);

            linearLayout.setOnClickListener(listener);
        }





    }

    @Override
    public void setImage(Bitmap b, ImageView v) {
        v.setImageBitmap(b);
        LinearLayout linlayout=(LinearLayout)findViewById(R.id.linLayoutHoriz);
        int count= linlayout.getChildCount();
        for(int i=0;i<count;i++)
        {
            linlayout.getChildAt(i);

        }

    }
}