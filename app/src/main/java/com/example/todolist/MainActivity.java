package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView RecyclerViewNotes;
    private FloatingActionButton buttonAddNote;
    private MainViewModel viewModel;

    private NotesAdapter notesAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesAdapter = new NotesAdapter();
        initViews();
        RecyclerViewNotes.setAdapter(notesAdapter);
        viewModel=new ViewModelProvider(this).get(MainViewModel.class);//При перевороте экрана данные не удаляются
        viewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesAdapter.setNotes(notes);
            }
        });



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Note note = NotesAdapter.getNotes().get(position);
                viewModel.remove(note);
            }
        });
        itemTouchHelper.attachToRecyclerView(RecyclerViewNotes);


        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AddNote.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });

        }



    public void initViews(){
        RecyclerViewNotes=findViewById(R.id.RecyclerViewNotes);
        buttonAddNote=findViewById(R.id.floatingActionButtonAddNote);

    }

    }
