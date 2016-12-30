package com.example.lakshminarayanabr.midtermexamapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by lakshminarayanabr on 10/24/16.
 */
public class DBDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private FilterDAO noteDAO;

    public DBDataManager(Context mContext)
    {
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
        noteDAO=new FilterDAO(db);


    }

    public void close()
    {
        if (db!=null)
        {
            db.close();
        }
    }
    public FilterDAO getNoteDAO()
    {
        return this.noteDAO;
    }

    public long saveNote(AppObject note)
    {
        return this.noteDAO.save(note);

    }

    public boolean updateNote(AppObject note)
    {
        return this.noteDAO.update(note);

    }

    public boolean deletenote(AppObject note)
    {
        return this.noteDAO.delete(note);

    }

    public AppObject getNote (String id)
    {
        return this.noteDAO.get(id);
    }

    public List<AppObject> getAllNotes()
    {
        return this.noteDAO.getAll();
    }


}
