package com.example.lakshminarayanabr.inclass04;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.lakshminarayanabr.inclass04.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
        TextView txtPassCount,txtPassLength,txtPassword;
    SeekBar skBarPassCount,skBarPassLength;

    static final int PROGRESS_START=0x11;

    static final int PROGRESS_VAL= 0x01;
    static final int PASS_VAL=0x10;

    Button btnThread,btnAsync;

    ExecutorService threadPool;

    Handler handler;
    AlertDialog.Builder adPasswod;
    ProgressDialog pgPassword;



    ArrayList<String> passwordsRetrievedArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        passwordsRetrievedArray=new ArrayList<String>();

        adPasswod=new AlertDialog.Builder(this);
        adPasswod.setCancelable(false);




        txtPassCount=(TextView)findViewById(R.id.textViewPasswordCountValue);
        txtPassLength=(TextView)findViewById(R.id.textViewPasswordLengthVal);
        txtPassword=(TextView)findViewById(R.id.textViewPasswordValue);

        skBarPassCount=(SeekBar)findViewById(R.id.seekBarPasswordsCount);
        skBarPassLength=(SeekBar)findViewById(R.id.seekBarPasswordLength);

        txtPassCount.setText(String.valueOf(skBarPassCount.getProgress()+1));

        txtPassLength.setText(String.valueOf(skBarPassLength.getProgress()+8));

        skBarPassCount.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txtPassCount.setText(String.valueOf(skBarPassCount.getProgress()+1));



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        skBarPassLength.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                txtPassLength.setText(String.valueOf(skBarPassLength.getProgress()+8));



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        btnThread=(Button)findViewById(R.id.buttonThread);
        btnAsync=(Button)findViewById(R.id.buttonAsync);

        btnThread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                threadPool= Executors.newFixedThreadPool(2);

                threadPool.execute(new PasswordRetreiver(skBarPassCount.getProgress()+1,skBarPassLength.getProgress()+8));



            }
        });
        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Password().execute(skBarPassCount.getProgress()+1,skBarPassLength.getProgress()+8);

            }
        });
        handler=new Handler(new Handler.Callback()
        {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what)
                {
                    case PROGRESS_START:
                        pgPassword=new ProgressDialog(MainActivity.this);
                        pgPassword.setMessage("Generating Passwords");
                        pgPassword.setMax(100);
                        pgPassword.setCancelable(false);
                        pgPassword.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        pgPassword.show();
                        break;

                    case PROGRESS_VAL:

                        pgPassword.setProgress((Integer)msg.obj);

                        break;

                    case PASS_VAL:
                        pgPassword.dismiss();
                        Bundle bnd=msg.getData();
                        passwordsRetrievedArray=bnd.getStringArrayList("Passwords");

                        adPasswod.setTitle("Passwords");
//                        adPasswod.setSingleChoiceItems((passwordsRetrievedArray.toArray(new String[passwordsRetrievedArray.size()])), -1, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                txtPassword.setText(passwordsRetrievedArray.get(which));
//
//
//
//                            }
//                        }).show();

                        adPasswod.setItems((passwordsRetrievedArray.toArray(new String[passwordsRetrievedArray.size()])), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                txtPassword.setText(passwordsRetrievedArray.get(which));

                            }
                        }).show();




                        break;

                }






                return true;
            }
        });



    }

    class Password extends AsyncTask<Integer, Integer, List<String>> {

        ArrayList<String> passwords=new ArrayList<String>();
        @Override
        protected void onPreExecute() {
            pgPassword = new ProgressDialog(MainActivity.this);
            pgPassword.setMessage("Generating Passwords");
            pgPassword.setMax(100);
            pgPassword.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pgPassword.setCancelable(false);
            pgPassword.show();
        }

        @Override
        protected List<String> doInBackground(Integer... params) {
            int count = params[0];
            int length = params[1];
            for (int i = 0; i < count; i++) {
                passwords.add(Util.getPassword(length));
                publishProgress((i + 1) * 100 / count);
            }
            return passwords;
        }

        @Override
        protected void onPostExecute(List<String> passwords) {
            pgPassword.dismiss();

            final String[] passwordsArray = passwords.toArray(new String[passwords.size()]);


            AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
            b.setTitle("Passwords");
            /*for (int i = 0; i < expenseNames.length; i++) {
                expenseNames[i] = expenses.get(i).getName();
            }*/

            b.setItems(passwordsArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txtPassword.setText(passwordsArray[which]);
                }
            });
            b.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            pgPassword.setProgress(values[0]);
        }
    }


//    class Password extends AsyncTask<Integer,Integer,List<String>>
//    {
//        @Override
//        protected List<String> doInBackground(Integer... params) {
//
//
//            return null;
//        }
//    }

    class PasswordRetreiver implements Runnable{

        int passwordsCount;
        int passwordLength;



        public PasswordRetreiver(int passwordsCount, int passwordLength) {
            this.passwordsCount = passwordsCount;
            this.passwordLength = passwordLength;
        }

        @Override
        public void run() {
            Util objUtil=new Util();
            ArrayList<String> passwordsArray=new ArrayList<String>();
            Message msg=new Message();

//                Bundle bndle=new Bundle();
//                bndle.putInt("Progress",(i*100/passwordsCount));
//                msg.setData(bndle);
            msg.what=PROGRESS_START;
            handler.sendMessage(msg);


            for(int i=0;i<passwordsCount;i++)
            {
               String pass= objUtil.getPassword(passwordLength);

                passwordsArray.add(pass);
                Message msgUpdate=new Message();

//                Bundle bndle=new Bundle();
//                bndle.putInt("Progress",(i*100/passwordsCount));
//                msg.setData(bndle);
                msgUpdate.obj=((i+1)*100)/passwordsCount;
                msgUpdate.what=PROGRESS_VAL;
                handler.sendMessage(msgUpdate);




            }

            Message msgPass=new Message();

            Bundle bndle=new Bundle();
            bndle.putStringArrayList("Passwords",passwordsArray);
//            bndle.putInt("Progress",);
            msgPass.setData(bndle);
            msgPass.what=PASS_VAL;
            handler.sendMessage(msgPass);

        }


    }
}
