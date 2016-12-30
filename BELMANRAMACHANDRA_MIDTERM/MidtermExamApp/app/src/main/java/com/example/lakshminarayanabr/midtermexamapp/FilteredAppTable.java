package com.example.lakshminarayanabr.midtermexamapp;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class FilteredAppTable {
    static final String TABLENAME="filterapp";
    static final String COLUMN_ID="_id";
    static final String APP_NAME="APPNAME";
    static final String PRICE="PRICE";
    static final String PRICEUNIT="PRICEUNIT";
    static final String THUMBAILURL="IMAGEURL";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+TABLENAME+" (");
        sb.append(COLUMN_ID+" integer primary key autoincrement, ");
        sb.append(APP_NAME+" text not null, ");
        sb.append(PRICEUNIT+" text not null, ");
        sb.append(THUMBAILURL+" text not null, ");
        sb.append(PRICE+" text not null ); ");


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
        FilteredAppTable.onCreate(db);


    }

}
