package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Create_note extends AppCompatActivity {
    EditText note_title;
    EditText note_description;
    Button btn_add;
    Button btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edited_notes);
        note_title=findViewById(R.id.note_title);
        note_description=findViewById(R.id.note_Description);
        btn_add=findViewById(R.id.btn_add);
        btn_cancel=findViewById(R.id.btn_cancel);

        Intent it = getIntent();
        if (it != null) {
            String text_title = it.getStringExtra("TEXT_TITLE");
            String text_description = it.getStringExtra("TEXT_DESCRIPTION");

            note_title.setText(text_title);
            note_description.setText(text_description);

            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (note_title.getText().toString().length() == 0) {
                        Toast.makeText(Create_note.this, "Write a note", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent it = new Intent();
                        it.putExtra("TEXT_TITLE", note_title.getText().toString());
                        it.putExtra("TEXT_DESCRIPTION", note_description.getText().toString());
                        it.putExtra("NOTE_ID", getIntent().getLongExtra("NOTE_ID", 1));
                        setResult(RESULT_OK, it);
                        finish();
                    }

                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_CANCELED, null);
                    finish();
                }
            });

        }
    }
}