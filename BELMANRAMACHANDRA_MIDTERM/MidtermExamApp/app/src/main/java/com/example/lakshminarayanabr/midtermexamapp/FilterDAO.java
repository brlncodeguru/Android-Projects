package com.example.lakshminarayanabr.midtermexamapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class FilterDAO {
    private SQLiteDatabase db;
    public FilterDAO(SQLiteDatabase db)
    {
        this.db=db;

    }

    public long save(AppObject note)

    {
        ContentValues values=new ContentValues();
        values.put(FilteredAppTable.APP_NAME, note.getAppName());
        values.put(FilteredAppTable.PRICE,note.getPrice());//ADD HERE
        values.put(FilteredAppTable.PRICEUNIT,note.getPriceUNIT());
        values.put(FilteredAppTable.THUMBAILURL,note.getThumbnailImageURL());


        return db.insert(FilteredAppTable.TABLENAME,null,values);



    }
    public boolean update(AppObject note)
    {
        ContentValues values=new ContentValues();
        values.put(FilteredAppTable.APP_NAME, note.getAppName());
        values.put(FilteredAppTable.PRICE,note.getPrice());//ADD HERE
        values.put(FilteredAppTable.PRICEUNIT,note.getPriceUNIT());
        values.put(FilteredAppTable.THUMBAILURL,note.getThumbnailImageURL());

        return db.update(FilteredAppTable.TABLENAME,values,FilteredAppTable.APP_NAME+"=?",new String[]{note.getAppName()+""})>0;



    }

    public boolean delete(AppObject note)
    {
        return db.delete(FilteredAppTable.TABLENAME,FilteredAppTable.APP_NAME+"=?",new String[]{note.getAppName()+""})>0;


    }

    public AppObject get(String id)
    {

        AppObject note=null;
        Cursor c= db.query(true,FilteredAppTable.TABLENAME, new String []{
                        FilteredAppTable.COLUMN_ID,FilteredAppTable.APP_NAME, FilteredAppTable.PRICE,FilteredAppTable.PRICEUNIT,FilteredAppTable.THUMBAILURL},//ADD EHRER
                //NoteTable.COLUMN_ID,NoteTable.CITY_NAME, NoteTable.COUNTRY},//ADD EHRER
                FilteredAppTable.COLUMN_ID+"=?",new String[]{id+""},null,null,null,null,null);
        if (c !=null&&c.moveToFirst())
        {
            note=buildNoteFromCurser(c);
            if (!c.isClosed())
                c.close();
        }

        return note;
    }
    public List<AppObject> getAll()

    {
        List<AppObject> notes=new ArrayList<AppObject>();
        Cursor c= db.query(FilteredAppTable.TABLENAME,new String[]{
                FilteredAppTable.COLUMN_ID,FilteredAppTable.APP_NAME, FilteredAppTable.PRICE,FilteredAppTable.PRICEUNIT,FilteredAppTable.THUMBAILURL},null,null,null,null,null);//ADD HERE
        //NoteTable.COLUMN_ID,NoteTable.COUNTRY,NoteTable.CITY_NAME,},null,null,null,null,null);//ADD HERE
        if (c !=null&&c.moveToFirst())
        {
            do {
                AppObject note=buildNoteFromCurser(c);
                if (note!=null)
                {
                    notes.add(note);

                }


            }while (c.moveToNext());
            if (!c.isClosed())
                c.close();
        }


        return notes;

    }

    private AppObject buildNoteFromCurser(Cursor c)
    {
        AppObject note= null;
        if (c!=null)
        {
            note=new AppObject();
            note.set_id(c.getLong(0));
            note.setAppName(c.getString(1));
            note.setPrice(c.getString(2));
            note.setPriceUNIT(c.getString(3));
            note.setThumbnailImageURL(c.getString(4));
        }

        return note;
    }


}
