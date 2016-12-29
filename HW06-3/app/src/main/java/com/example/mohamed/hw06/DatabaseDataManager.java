package com.example.mohamed.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by lakshminarayanabr on 10/20/16.
 */
public class DatabaseDataManager {
    private Context mContext;
    private DatabaseOpenHelper dbOpenHelper;
    private SQLiteDatabase db;
    private NoteDAO noteDAO;

    public DatabaseDataManager(Context mContext)
    {
        this.mContext=mContext;
        dbOpenHelper=new DatabaseOpenHelper(this.mContext);
        db=dbOpenHelper.getWritableDatabase();
        noteDAO=new NoteDAO(db);


    }

    public void close()
    {
        if (db!=null)
        {
            db.close();
        }
    }
    public NoteDAO getNoteDAO()
    {
        return this.noteDAO;
    }

    public long saveNote(Note note)
    {
        return this.noteDAO.save(note);

    }

    public boolean updateNote(Note note)
    {
        return this.noteDAO.update(note);

    }

    public boolean deletenote(Note note)
    {
        return this.noteDAO.delete(note);

    }

    public Note getNote (String id)
    {
        return this.noteDAO.get(id);
    }

    public List<Note> getAllNotes()
    {
        return this.noteDAO.getAll();
    }

}
