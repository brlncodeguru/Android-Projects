package com.example.lakshminarayanabr.group3_inclass09;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.NumberFormat;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lakshminarayanabr on 10/31/16.
 */

public class MessagesListAdapter extends ArrayAdapter<ChatMessageDetail> {

    Context context;
    int resID;
    List<ChatMessageDetail> objects;
    String token;

    public MessagesListAdapter(Context context, int resource, List<ChatMessageDetail> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resID=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(resID,parent,false);
        }
        ChatMessageDetail msgDetail=objects.get(position);

        TextView txtMessage=(TextView)convertView.findViewById(R.id.tvmessage);
        TextView txtName=(TextView)convertView.findViewById(R.id.tvName);
        TextView txtTime=(TextView)convertView.findViewById(R.id.tvTime);


        ImageView imgView=(ImageView)convertView.findViewById(R.id.fileImage);

        txtName.setText(msgDetail.getUserFname()+" "+msgDetail.getUserLname());






        if(msgDetail.getType().equalsIgnoreCase("IMAGE"))
        {

            txtMessage.setVisibility(View.INVISIBLE);
            imgView.setVisibility(View.VISIBLE);

            final String url="http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/"+msgDetail.getFileThumbnailId();


            final String token1="BEARER "+token;

//            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//            clientBuilder.networkInterceptors().add(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request newRequest = chain.request().newBuilder()
//                            .addHeader("Authorization", token1)
//                            .build();
//                    return chain.proceed(newRequest);
//                }
//            });
//
//            OkHttpClient client=clientBuilder.build();
//
//
//            Picasso picasso = new Picasso.Builder(context)
//                    .downloader(new OkHttpDownloader(client))
//                    .build();
//            picasso.load(url).into(imgView);
//




            System.out.println(token1);


            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", token1)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    })
                    .build();

            Picasso picasso = new Picasso.Builder(context)
                    .downloader(new OkHttp3Downloader(client))
                    .build();
            picasso.load(url).into(imgView);









        }
        else if (msgDetail.getType().equalsIgnoreCase("TEXT"))
        {
            txtMessage.setVisibility(View.VISIBLE);
            imgView.setVisibility(View.INVISIBLE);
            txtMessage.setText(msgDetail.getComment());




        }
        else
        {

        }









        return convertView;

    }

}
