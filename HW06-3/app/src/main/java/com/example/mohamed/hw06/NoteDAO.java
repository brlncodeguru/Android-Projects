package com.example.mohamed.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class NoteDAO {
    private SQLiteDatabase db;
    public NoteDAO(SQLiteDatabase db)
    {
        this.db=db;

    }

    public long save(Note note)

    {
        ContentValues values=new ContentValues();
        values.put(NoteTable.CITY_NAME, note.getCityName());
        values.put(NoteTable.COUNTRY,note.getCountryName());//ADD HERE
        values.put(NoteTable.TEMPERATURE,note.getTemperature());
        values.put(NoteTable.FAVORITE,note.getFavorite());
        values.put(NoteTable.DATE,note.getUpdatedDate());


        return db.insert(NoteTable.TABLENAME,null,values);



    }
    public boolean update(Note note)
    {
        ContentValues values=new ContentValues();
        values.put(NoteTable.CITY_NAME, note.getCityName());
        values.put(NoteTable.COUNTRY,note.getCountryName());// ADD HERE
        values.put(NoteTable.TEMPERATURE,note.getTemperature());
        values.put(NoteTable.FAVORITE,note.getFavorite());
        values.put(NoteTable.DATE,note.getUpdatedDate());
        return db.update(NoteTable.TABLENAME,values,NoteTable.CITY_NAME+"=?",new String[]{note.getCityName()+""})>0;



    }

    public boolean delete(Note note)
    {
        return db.delete(NoteTable.TABLENAME,NoteTable.CITY_NAME+"=?",new String[]{note.getCityName()+""})>0;


    }

    public Note get(String id)
    {

        Note note=null;
        Cursor c= db.query(true,NoteTable.TABLENAME, new String []{
                        NoteTable.COLUMN_ID,NoteTable.CITY_NAME, NoteTable.COUNTRY,NoteTable.TEMPERATURE,NoteTable.FAVORITE,NoteTable.DATE},//ADD EHRER
                //NoteTable.COLUMN_ID,NoteTable.CITY_NAME, NoteTable.COUNTRY},//ADD EHRER
                NoteTable.CITY_NAME+"=?",new String[]{id+""},null,null,null,null,null);
        if (c !=null&&c.moveToFirst())
        {
            note=buildNoteFromCurser(c);
            if (!c.isClosed())
                c.close();
        }

        return note;
    }
    public List<Note> getAll()

    {
        List<Note> notes=new ArrayList<Note>();
        Cursor c= db.query(NoteTable.TABLENAME,new String[]{
                NoteTable.COLUMN_ID,NoteTable.CITY_NAME,NoteTable.COUNTRY,NoteTable.TEMPERATURE,NoteTable.FAVORITE,NoteTable.DATE},null,null,null,null,null);//ADD HERE
        //NoteTable.COLUMN_ID,NoteTable.COUNTRY,NoteTable.CITY_NAME,},null,null,null,null,null);//ADD HERE
        if (c !=null&&c.moveToFirst())
        {
            do {
                Note note=buildNoteFromCurser(c);
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

    private Note buildNoteFromCurser(Cursor c)
    {
        Note note= null;
        if (c!=null)
        {
            note=new Note();
            note.set_id(c.getLong(0));
            note.setCityName(c.getString(1));
            note.setCountryName(c.getString(2));
            note.setTemperature(c.getString(3));
            note.setFavorite(c.getString(4));
            note.setUpdatedDate(c.getString(5));
        }

        return note;
    }

}
