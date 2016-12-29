package com.example.mohamed.hw06;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by lakshminarayanabr on 10/16/16.
 */
public class MyPrefsActivity extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();


    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, final Preference preference) {
Log.d("Demo",preference.getKey());
            if(preference.getKey().equals("Temperature"))
            {
                final android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
                builder.setTitle("Choose a Keyword");

                builder.setCancelable(false);
                builder.setItems(R.array.keyword, new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String [] str=getResources().getStringArray(R.array.keyword);
                        Log.d("Demo",str[which]);
                        getActivity().finish();
                        String unicodeF="\u2109";
                        Log.d("Demow",unicodeF);
                        if(str[which].equals(unicodeF) )
                        {
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(getResources().getString(R.string.Temperature),"F");
                            editor.commit();

                        }
                        else
                        {
                            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
                            SharedPreferences.Editor editor=preferences.edit();
                            editor.putString(getResources().getString(R.string.Temperature),"C");
                            editor.commit();
                        }

                    }
                });
                builder.create().show();


                Log.d("demo","Shared Pref value");

            }
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);


        }
    }
}
