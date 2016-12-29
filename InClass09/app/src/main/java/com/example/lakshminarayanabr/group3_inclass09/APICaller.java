package com.example.lakshminarayanabr.group3_inclass09;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lakshminarayanabr on 10/31/16.
 */

public class APICaller {

    private OkHttpClient client;
    Gson gson;

    public static User usr;

    ActivityInterface activity;
    ListMessagesInterface activity1;

    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");


    public APICaller(ActivityInterface activity) {
        this.activity = activity;
    }
    public APICaller(ListMessagesInterface activity) {
        this.activity1 = activity;
    }

    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    public void   login(String username,String password)
    {
        gson=new Gson();
        client= new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/login")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

//                System.out.println(response.body().string());

                usr=gson.fromJson(response.body().string(),User.class);
                System.out.println(usr.toString());

               activity.loginCalled(usr);




            }
        });

    }

    public void SignUp(String email,String password,String fname,String lname)
    {
        gson=new Gson();

        client= new OkHttpClient();

        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("password",password)
                .add("fname",fname)
                .add("lname",lname)
                .build();
        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/signup")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                usr=gson.fromJson(response.body().string(),User.class);

                System.out.println(usr.toString());

                activity.loginCalled(usr);


            }
        });



    }

    public void getMessages(String token)
    {
        gson=new Gson();

        client= new OkHttpClient();

        String token1="BEARER "+token;

        System.out.println(token1);


        Request request = new Request.Builder()
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/messages")
                .addHeader("Authorization",token1)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                Messages msges = gson.fromJson(response.body().string(), Messages.class);
                Log.d("test", msges.toString());

                activity1.setMessagesToList(msges.getMessages());


            }
        });




    }

    public void addImage(final String token, String path) {
        gson=new Gson();

        client= new OkHttpClient();

        final String token1="BEARER "+token;


        System.out
                .println(token1);

        System.out
                .println(path);

        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("newFile", "logo.png",
                        RequestBody.create(MEDIA_TYPE_PNG,new File(path)))
                .build();

        Request request = new Request.Builder()
                .header("Authorization",token1)
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/file/add")
                .post(requestBody)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

//                Messages msges = gson.fromJson(response.body().string(), Messages.class);
//                Log.d("test", msges.toString());
//
//                activity1.setMessagesToList(msges.getMessages());

               ImageAdd add=gson.fromJson(response.body().string(),ImageAdd.class);

                if(add.status.equalsIgnoreCase("ok"))
                {
                    addTextorImage(token,"IMAGE","",add.file.getId());
                }
                else
                {

                }



            }
        });



    }

    public void addTextorImage(final String token, String type, String comment, String fileID) {
        gson=new Gson();

        client= new OkHttpClient();

        final String token1="BEARER "+token;

        RequestBody formBody = new FormBody.Builder()
                .add("Type", type)
                .add("Comment",comment)
                .add("FileThumbnailId",fileID)
                .build();
        Request request = new Request.Builder()
                .header("Authorization",token1)
                .url("http://ec2-54-166-14-133.compute-1.amazonaws.com/api/message/add")
                .addHeader("Content-Type", "application/form-data")
                .post(formBody)
                .build();



        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
//                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

//                Messages msges = gson.fromJson(response.body().string(), Messages.class);
//                Log.d("test", msges.toString());
//
//                activity1.setMessagesToList(msges.getMessages());
//                Log.d("Demo223",response.body().string());

               AddTextorImageToList obj=gson.fromJson(response.body().string(),AddTextorImageToList.class);

                Log.d("Demo223",obj.toString());

                if (obj.status.equalsIgnoreCase("ok"))
                {

                    getMessages(token);


                }
                else
                {

                }



            }
        });



    }









    public interface ActivityInterface {

        public void loginCalled(User user);



    }
    public interface ListMessagesInterface {

        public void setMessagesToList(List<ChatMessageDetail> messages);



    }


}

