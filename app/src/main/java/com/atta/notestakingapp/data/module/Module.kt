package com.atta.notestakingapp.data.module

import android.content.Context
import androidx.room.Room
import com.atta.notestakingapp.AppDataBase
import com.atta.notestakingapp.Notes.NotesDAO
import com.atta.notestakingapp.data.repository.NotesAppRepo
import com.atta.notestakingapp.data.repository.NotesAppRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideMustToolDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "notes_app_database").build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(database: AppDataBase): NotesDAO {
        return database.noteDao()
    }

    @Provides
    @Singleton
    fun getNotesAppRepo(mustToolDatabase: AppDataBase): NotesAppRepo = NotesAppRepoImpl(mustToolDatabase)

}

