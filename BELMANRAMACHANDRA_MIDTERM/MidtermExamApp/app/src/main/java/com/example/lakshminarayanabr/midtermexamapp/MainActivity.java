package com.example.lakshminarayanabr.midtermexamapp;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    GetAppDataFromAPI objGetAppData;
    ListView listTop;
    Switch sw;
    ImageView imgRefresh;

    ProgressDialog pgDialog;

    ArrayList<AppObject> filteredList;
     RelativeLayout relLayout;
    TextView txtErrorMsg;
    DBDataManager dm;
    TopListAdapter adapter;




    ArrayList<AppObject> appObjectArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pgDialog=new ProgressDialog(this);
        pgDialog.setMessage("Loading...");


        listTop=(ListView)findViewById(R.id.listViewApp);
        sw=(Switch)findViewById(R.id.switchSorting);
        sw.setText("Descending");
        relLayout=(RelativeLayout)findViewById(R.id.relLayout);
        txtErrorMsg=(TextView)findViewById(R.id.textView);

        dm=new DBDataManager(this);
        filteredList=new ArrayList<>(dm.getAllNotes());
        if (filteredList.size()>0)
        {
            txtErrorMsg.setVisibility(View.INVISIBLE);
            if(relLayout.getChildCount()==6)
            {
                relLayout.removeViewAt(5);
            }


            final RecyclerView filterRecycler = new RecyclerView(this);
            LinearLayoutManager linMgr = new LinearLayoutManager(this);
            linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

            RecyclerViewFilterAdapter adapter=new RecyclerViewFilterAdapter(filteredList,this,filterRecycler);


            filterRecycler.setHasFixedSize(true);
            filterRecycler.setAdapter(adapter);
            filterRecycler.setLayoutManager(linMgr);
            filterRecycler.setBackgroundColor(Color.WHITE);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW,R.id.textViewFilteredList);
            params.topMargin=20;


            filterRecycler.setLayoutParams(params);

            relLayout.addView(filterRecycler);


        }
        else
        {
            txtErrorMsg.setVisibility(View.VISIBLE);
        }
        sw.setChecked(false);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    sw.setText("Ascending");
                    Collections.sort(appObjectArrayList, new Comparator<AppObject>(){
                        public int compare(AppObject emp1, AppObject emp2) {
                            // ## Ascending order
                            return emp1.price.compareTo(emp2.price); // To compare string values
                            // return Integer.valueOf(emp1.getId()).compareTo(emp2.getId()); // To compare integer values

                            // ## Descending order
                            // return emp2.getFirstName().compareToIgnoreCase(emp1.getFirstName()); // To compare string values
                            // return Integer.valueOf(emp2.getId()).compareTo(emp1.getId()); // To compare integer values
                        }
                    });
                    adapter.notifyDataSetChanged();


                }
                else
                {

                    sw.setText("Descending");
                    Collections.sort(appObjectArrayList, new Comparator<AppObject>(){
                        public int compare(AppObject emp1, AppObject emp2) {
                            // ## Ascending order
                            return emp2.price.compareTo(emp1.price); // To compare string values
                            // return Integer.valueOf(emp1.getId()).compareTo(emp2.getId()); // To compare integer values

                            // ## Descending order
                            // return emp2.getFirstName().compareToIgnoreCase(emp1.getFirstName()); // To compare string values
                            // return Integer.valueOf(emp2.getId()).compareTo(emp1.getId()); // To compare integer values
                        }
                    });
                    adapter.notifyDataSetChanged();

                }
            }
        });
        imgRefresh=(ImageView)findViewById(R.id.imageViewRefresh);
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filteredList=new ArrayList<AppObject>(dm.getAllNotes());
                if (filteredList.size()>0)
                {
                    txtErrorMsg.setVisibility(View.INVISIBLE);
                    if(relLayout.getChildCount()==6)
                    {
                        relLayout.removeViewAt(5);
                    }


                    final RecyclerView filterRecycler = new RecyclerView(MainActivity.this);
                    LinearLayoutManager linMgr = new LinearLayoutManager(MainActivity.this);
                    linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

                    RecyclerViewFilterAdapter adapter=new RecyclerViewFilterAdapter(filteredList,MainActivity.this,filterRecycler);


                    filterRecycler.setHasFixedSize(true);
                    filterRecycler.setAdapter(adapter);
                    filterRecycler.setLayoutManager(linMgr);
                    filterRecycler.setBackgroundColor(Color.WHITE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW,R.id.textViewFilteredList);
                    params.topMargin=20;


                    filterRecycler.setLayoutParams(params);

                    relLayout.addView(filterRecycler);


                }

                objGetAppData= (GetAppDataFromAPI) new GetAppDataFromAPI(MainActivity.this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");


            }
        });


        imgRefresh.setImageResource(R.mipmap.refresh_icon);
        objGetAppData= (GetAppDataFromAPI) new GetAppDataFromAPI(this).execute("https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json");
        listTop.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AppObject obj=appObjectArrayList.get(position);
                appObjectArrayList.remove(position);

                dm.saveNote(obj);
                filteredList=new ArrayList<>(dm.getAllNotes());
                if (filteredList.size()>0)
                {
                    txtErrorMsg.setVisibility(View.INVISIBLE);
                    if(relLayout.getChildCount()==6)
                    {
                        relLayout.removeViewAt(5);
                    }


                    final RecyclerView filterRecycler = new RecyclerView(MainActivity.this);
                    LinearLayoutManager linMgr = new LinearLayoutManager(MainActivity.this);
                    linMgr.setOrientation(LinearLayoutManager.HORIZONTAL);

                    RecyclerViewFilterAdapter adapter=new RecyclerViewFilterAdapter(filteredList,MainActivity.this,filterRecycler);


                    filterRecycler.setHasFixedSize(true);
                    filterRecycler.setAdapter(adapter);
                    filterRecycler.setLayoutManager(linMgr);
                    filterRecycler.setBackgroundColor(Color.WHITE);

                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
                    params.addRule(RelativeLayout.BELOW,R.id.textViewFilteredList);
                    params.topMargin=20;


                    filterRecycler.setLayoutParams(params);

                    relLayout.addView(filterRecycler);


                }

                adapter.notifyDataSetChanged();






                return true;
            }
        });

    }
}
