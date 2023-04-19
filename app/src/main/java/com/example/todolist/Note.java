package com.example.todolist;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "notes"
)
public class Note {
    @PrimaryKey(autoGenerate = true)
    int id;
    String disc;
    int priority;

    public int getId() {
        return id;
    }


    public String getDisc() {
        return disc;
    }

    public int getPriority() {
        return priority;
    }

    public Note(int id, String disc, int priority) {
        this.id = id;
        this.disc = disc;
        this.priority = priority;
    }
    @Ignore
    public Note(String disc, int priority) {
        this(0,disc,priority);
    }
}
