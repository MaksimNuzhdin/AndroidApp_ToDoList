package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddNote extends AppCompatActivity {
    private EditText EditTextEnterNote;
    private RadioButton RadioButton_low;
    private RadioButton RadioButtomMedium;
    private RadioButton RadioButtonHigh;
    private Button CreateNoteBtn;
    private AddNoteViewModel addNoteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        initViews();

        addNoteViewModel=new ViewModelProvider(this).get(AddNoteViewModel.class);
        addNoteViewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose){
                    finish();
                }

            }
        });
        CreateNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EditTextEnterNote.getText().toString().trim().isEmpty()){
                    fieldCheck();
                } else {
                    saveNewNote();
                }


            }
        });
    }
    public void initViews(){
        EditTextEnterNote=findViewById(R.id.EditTextEnterNote);
        RadioButton_low=findViewById(R.id.RadioButton_low);
        RadioButtomMedium=findViewById(R.id.RadioButtomMedium);
        RadioButtonHigh=findViewById(R.id.RadioButtonHigh);
        CreateNoteBtn=findViewById(R.id.CreateNoteBtn);
    }
    public void saveNewNote(){
        String text=EditTextEnterNote.getText().toString().trim();
        int priority=getPriority();
//        int id = database.getNotes().size();
        Note note=new Note(text,priority);
        addNoteViewModel.saveNote(note);





    }
    public void fieldCheck(){

            Toast.makeText(AddNote.this,
                    getString(R.string.error_field_empty),
                    Toast.LENGTH_SHORT).show();

    }
    public int getPriority(){
        int priority;
        if (RadioButton_low.isChecked()){
            priority=0;
        } else if (RadioButtomMedium.isChecked()) {
            priority=1;

        }else {priority=2;}
        return priority;
    }
    public static Intent newIntent(Context context) {
        return new Intent(context, AddNote.class);
    }

}