package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class Edit_Note extends AppCompatActivity {

    EditText tv_title;

    EditText tv_description;
    List_BD listBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_to_do_list_notes);
        tv_title=findViewById(R.id.tv_title);
        tv_description=findViewById(R.id.tv_description);
        listBD=new List_BD(this);

        Intent it = getIntent();
        if (it != null) {

            String note_title = it.getStringExtra("TEXT_TITLE");
            String note_description = it.getStringExtra("TEXT_DESCRIPTION");
            tv_title.setText(note_title);
            tv_description.setText(note_description);
        }



    }

    @Override
    public void onBackPressed() {
        String newTitle = tv_title.getText().toString();
        String newDescription = tv_description.getText().toString();
        long noteId = getIntent().getLongExtra("NOTE_ID", 1);

        if (!newTitle.equals(getIntent().getStringExtra("TEXT_TITLE")) || !newDescription.equals(getIntent().getStringExtra("TEXT_DESCRIPTION"))) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("TEXT_TITLE", newTitle);
            resultIntent.putExtra("TEXT_DESCRIPTION", newDescription);
            resultIntent.putExtra("NOTE_ID", noteId);
            setResult(RESULT_OK, resultIntent);
        }
        super.onBackPressed();
    }
}