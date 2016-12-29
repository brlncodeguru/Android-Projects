package com.example.mohamed.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    static final String DB_NAME="favourites.db";
    static final int DB_VERSION=3;

    public DatabaseOpenHelper(Context context) {
        super(context, DB_NAME, null,DB_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        NoteTable.onCreate(db);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NoteTable.onUpgrade(db,oldVersion,newVersion);

    }
}
