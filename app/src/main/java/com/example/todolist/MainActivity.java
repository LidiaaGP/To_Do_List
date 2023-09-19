package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton btn_add_note;
    List_BD listBD;

    ListView listview_notes;

    final int EDIT=1;
    final int INSERT=2;

    SimpleCursorAdapter sc_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview_notes=findViewById(R.id.tv_listview_notes);
        btn_add_note=findViewById(R.id.btn_add_note);


        registerForContextMenu(listview_notes);
        listBD=new List_BD(MainActivity.this);
        listBD.openBD();

        String col[]={"NOTE_TITLE"};
        int controls[]={R.id.title};

        Cursor cursor = listBD.getNoteList();
        sc_adapter=new SimpleCursorAdapter(this,R.layout.list_notes,cursor,col,controls,0);
        listview_notes.setAdapter(sc_adapter);



        listview_notes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                int row_number = i;
                Intent it = new Intent(MainActivity.this, Edit_Note.class);

                it.setAction(Intent.ACTION_EDIT);

                Cursor cursor =sc_adapter.getCursor();
                cursor.moveToPosition(row_number);
                int index = cursor.getColumnIndex("NOTE_TITLE");
                int description= cursor.getColumnIndex("NOTE_DESCRIPTION");
                int noteIdIndex = cursor.getColumnIndex("NOTE_ID");


                it.putExtra("TEXT_TITLE", cursor.getString(index));
                it.putExtra("TEXT_DESCRIPTION", cursor.getString(description));
                it.putExtra("NOTE_ID", cursor.getLong(noteIdIndex));

                startActivityForResult(it, EDIT);

            }
        });
        btn_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(MainActivity.this, Create_note.class);
                it.setAction(Intent.ACTION_INSERT);
                startActivityForResult(it, INSERT);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

          if (requestCode==EDIT && resultCode==RESULT_OK){
            int note_id = (int) data.getLongExtra("NOTE_ID", 1);
            String text_title=data.getStringExtra("TEXT_TITLE");
            String text_description=data.getStringExtra("TEXT_DESCRIPTION");

            listBD.updateNote(note_id,text_title,text_description);
            Cursor cursor = listBD.getNoteList();
            sc_adapter.changeCursor(cursor);

        }

         if (requestCode==INSERT && resultCode==RESULT_OK) {
            listBD.insertNote(data.getStringExtra("TEXT_TITLE"), data.getStringExtra("TEXT_DESCRIPTION"));
            Cursor c = listBD.getNoteList();
            sc_adapter.changeCursor(c);
        }

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.tv_listview_notes) {
            getMenuInflater().inflate(R.menu.contextual_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        long rowid = info.id;
        if (item.getItemId()== R.id.contextual_menu_delete) {
            listBD.deleteTask(rowid);
            Cursor cursor = listBD.getNoteList();
            sc_adapter.changeCursor(cursor);

        }

        return super.onContextItemSelected(item);
    }


}