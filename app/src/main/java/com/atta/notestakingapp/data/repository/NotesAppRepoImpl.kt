package com.atta.notestakingapp.data.repository

import com.atta.notestakingapp.AppDataBase
import com.atta.notestakingapp.Notes.Note
import com.atta.notestakingapp.data.repository.NotesAppRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotesAppRepoImpl @Inject constructor(private val mustToolDatabase: AppDataBase): NotesAppRepo {

    override suspend fun getNotes(): Flow<List<Note>> = flow{
        mustToolDatabase.noteDao().get().collect { notes ->
             emit(notes)
        }
    }

    override suspend fun putNotes(note: Note) {
        mustToolDatabase.noteDao().insert(note)
    }

    override suspend fun updateNotes(note: Note) {
        mustToolDatabase.noteDao().update(note)
    }

    override suspend fun deleteNotes(note: Note) {
       mustToolDatabase.noteDao().delete(note)
    }

    override suspend fun getNoteById(id: Long): Note {
        var note=mustToolDatabase.noteDao().getById(id)
        return note
    }

}