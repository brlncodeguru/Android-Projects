package com.example.lakshminarayanabr.midtermexamapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    static final String DB_NAME="myfilter.db";
    static final int DB_VERSION=1;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        FilteredAppTable.onCreate(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FilteredAppTable.onUpgrade(db,oldVersion,newVersion);

    }
}
