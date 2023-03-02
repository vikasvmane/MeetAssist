package com.maverickai.meetassist.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maverickai.meetassist.feature_list.domain.model.Note

@Database(entities = [Note::class], version = 2)
abstract class NotesRoomDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao

    companion object {
        private var dbInstance: NotesRoomDatabase? = null
        fun getAppDatabase(context: Context): NotesRoomDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesRoomDatabase::class.java,
                    NotesRoomDatabase.toString()
                ).build()
            }
            return dbInstance!!
        }
    }
}