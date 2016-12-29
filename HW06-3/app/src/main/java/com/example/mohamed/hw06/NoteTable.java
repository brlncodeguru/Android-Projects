package com.example.mohamed.hw06;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class NoteTable {
    static final String TABLENAME="notes1";
    static final String COLUMN_ID="_id";
    static final String CITY_NAME="CITYNAME";
    static final String COUNTRY="COUNTY";
    static final String TEMPERATURE="TEMPERATURE";
    static final String FAVORITE="FAVORITE";
    static final String DATE="DATE";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+" (");
        sb.append(COLUMN_ID+" integer primary key autoincrement, ");
        sb.append(CITY_NAME+" text no null, ");
        sb.append(COUNTRY+" text not null, ");
        sb.append(TEMPERATURE+" text not null, ");
        sb.append(FAVORITE+" text not null, ");
        sb.append(DATE+" text not null ); ");


        try {
            db.execSQL(sb.toString());


        }
        catch (SQLException ex)
        {
            ex.printStackTrace();

        }


    }

    static public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)

    {

        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        NoteTable.onCreate(db);


    }

}
