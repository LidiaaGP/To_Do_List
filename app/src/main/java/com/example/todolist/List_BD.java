package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class List_BD extends SQLiteOpenHelper {

    final static String BD="notes_list.sqlite";
    final static int VERSION=1;

    SQLiteDatabase db=null;
    Context contexto;

    public List_BD(@Nullable Context context) {
        super(context, BD, null, VERSION);
        contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;
        sql ="CREATE TABLE NOTES_LIST ( " +
                " NOTE_ID INTEGER PRIMARY KEY ASC AUTOINCREMENT," +
                " NOTE_TITLE TEXT NOT NULL," +
                " NOTE_DESCRIPTION TEXT)";

        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long insertNote(String note_title,String note_description) {
        openBD();
        if (note_title==null || note_title.length()>0) {
            ContentValues values = new ContentValues();
            values.put("NOTE_TITLE", note_title);
            values.put("NOTE_DESCRIPTION", note_description);
            return db.insert("NOTES_LIST", null, values);
        }
        else {
            return 1;
        }

    }

    public int updateNote(int noteId,String note_title,String note_description) {
        openBD();
        if (note_title==null || note_title.length()>0) {
            ContentValues values = new ContentValues();
            values.put("NOTE_TITLE", note_title);
            values.put("NOTE_DESCRIPTION", note_description);

            int updated_rows= db.update("NOTES_LIST", values, "NOTE_ID="+noteId, null);
            return updated_rows;
        }
        else {
            return 1;
        }

    }
    public void openBD() {
        if (db==null) {
            db = this.getReadableDatabase();
        }
    }

    public Cursor getNoteList(){
        openBD();
        Cursor c = db.rawQuery("SELECT rowid as _id, NOTE_ID, NOTE_TITLE, NOTE_DESCRIPTION FROM NOTES_LIST order by NOTE_ID DESC",null);
        return c;

    }
    public int deleteTask(long rowid) {
        openBD();
        return db.delete("NOTES_LIST", "NOTE_ID=" + rowid, null);
    }
}
