package com.example.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.disposables.Disposable;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private static List <Note> notes = new ArrayList<>();
    private onNoteClickListener onNoteClickListener;


    public void setOnNoteClickListener(NotesAdapter.onNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewNote = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,
                        parent,
                        false);
        return new NotesViewHolder(viewNote);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note= notes.get(position);
        holder.textViewNotes.setText(note.getDisc());
        int colorResId;
        if (note.getPriority()==0){
            colorResId= android.R.color.holo_green_light;
        }
        else if (note.getPriority()==1) {
            colorResId= android.R.color.holo_orange_light;
        }
        else {
            colorResId= android.R.color.holo_red_light;
        }
//
        int color= ContextCompat.getColor(holder.itemView.getContext(),colorResId);
        holder.textViewNotes.setBackgroundColor(color);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onNoteClickListener.onNoteClick(note);
//
//            }
//        });

    }




    @Override
    public int getItemCount() {
        return notes.size();
    }
    public class NotesViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNotes;
         public NotesViewHolder(@NonNull View itemView) {

            super(itemView);
             textViewNotes=itemView.findViewById(R.id.textViewNote);
        }
    }
    interface onNoteClickListener{
        void onNoteClick(Note note);
    }

    public static List<Note> getNotes() {
        return new ArrayList<>(notes);
    }
}
