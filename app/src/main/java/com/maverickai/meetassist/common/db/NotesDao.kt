package com.maverickai.meetassist.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maverickai.meetassist.feature_list.domain.model.Note

@Dao
interface NotesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note): Long

    @Query("SELECT * from notes")
    suspend fun getAllNotes(): List<Note>
}