package com.example.lakshminarayanabr.inclass07;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pgDialog;
    ListView listView;
    EditText searchTextBox;
    Button btnGo,btnClear;
    PodcastsAdapter adapter;
    ArrayList<Podcast> orgPodcasst;
    ArrayList<Podcast> podcastArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Loading News");
        pgDialog.show();


        if(isConnected())
        {

            searchTextBox=(EditText) findViewById(R.id.editTextSearch);
            new GeneratePodcastsFromURL(this,"https://itunes.apple.com/us/rss/toppodcasts/limit=30/json").execute("https://itunes.apple.com/us/rss/toppodcasts/limit=30/json");

            listView=(ListView)findViewById(R.id.listViewTopPodcasts);







            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intDetail=new Intent(MainActivity.this,ItemDetailActivity.class);
                    intDetail.putExtra("Podcast",podcastArrayList.get(position));
                    startActivity(intDetail);

                }
            });

            btnGo=(Button)findViewById(R.id.buttonGo);
            btnClear=(Button)findViewById(R.id.buttonClear);


            btnGo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(searchTextBox.getText()!=null)
                    search(searchTextBox.getText().toString());
                    else
                        Toast.makeText(MainActivity.this,"Enter Search text",Toast.LENGTH_LONG).show();



                }
            });
            btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchTextBox.setText("");
                    adapter.resetCount();
                    adapter = new PodcastsAdapter(MainActivity.this, R.layout.itemrowlayout,orgPodcasst);
                    adapter.setNotifyOnChange(true);
                    listView.setAdapter(adapter);
                }
            });




        }




    }

    public void search(String seq) {

        for (int i = 0; i < podcastArrayList.size(); i++) {
            Podcast pod = podcastArrayList.get(i);
            String[] words = pod.title.split(" ");

            for (int j = 0; j < words.length; j++) {

                if (words[j].equalsIgnoreCase(seq)) {
                    adapter.remove(pod);
                    adapter.insert(pod, 0);

                    adapter.notifyDataSetChanged();


                    listView.setAdapter(adapter);

                    break;



                }

            }
            //if(pod.title.split(" "))


        }
    }


    private boolean isConnected(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ntinfo = cm.getActiveNetworkInfo();
        if (ntinfo != null && ntinfo.isConnected()) {
            return true;
        }
        return false;
    }
}
