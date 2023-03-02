package com.maverickai.meetassist.common.di

import android.app.Application
import com.maverickai.meetassist.common.db.NotesRoomDatabase
import com.maverickai.meetassist.feature_list.data.NotesListRepositoryImpl
import com.maverickai.meetassist.feature_list.domain.NotesListRepository
import com.maverickai.meetassist.feature_create_note.data.datasource.LocalNotesDataSourceImpl
import com.maverickai.meetassist.feature_create_note.data.datasource.RemoteGPTDataSourceImpl
import com.maverickai.meetassist.feature_create_note.data.repository.CreateNoteRepositoryImpl
import com.maverickai.meetassist.feature_create_note.domain.CreateNoteRepository
import com.maverickai.meetassist.feature_create_note.domain.GPTDataSource
import com.maverickai.meetassist.feature_create_note.domain.NotesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun getNoteDataSource(notesDataSourceImpl: LocalNotesDataSourceImpl): NotesDataSource =
        notesDataSourceImpl

    @Provides
    fun getGPTDataSource(gptDataSourceImpl: RemoteGPTDataSourceImpl): GPTDataSource =
        gptDataSourceImpl

    @Provides
    fun getNotesListRepository(notesListRepositoryImpl: NotesListRepositoryImpl): NotesListRepository =
        notesListRepositoryImpl

    @Provides
    fun getCreateNotesRepository(createNoteRepositoryImpl: CreateNoteRepositoryImpl): CreateNoteRepository =
        createNoteRepositoryImpl

    @Singleton
    @Provides
    fun getFBMDatabase(context: Application): NotesRoomDatabase {
        return NotesRoomDatabase.getAppDatabase(context)
    }
}