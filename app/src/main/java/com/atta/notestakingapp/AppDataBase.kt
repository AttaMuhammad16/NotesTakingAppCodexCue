package com.atta.notestakingapp

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atta.notestakingapp.Notes.Note
import com.atta.notestakingapp.Notes.NotesDAO

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDataBase:RoomDatabase() {
    abstract fun noteDao() : NotesDAO
}

