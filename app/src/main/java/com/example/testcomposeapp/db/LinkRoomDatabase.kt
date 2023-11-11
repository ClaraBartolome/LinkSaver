package com.example.testcomposeapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testcomposeapp.common.DB_NAME
import com.example.testcomposeapp.db.Model.LinkModel


// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [LinkModel::class], version = 1, exportSchema = false)
public abstract class LinkRoomDatabase : RoomDatabase() {

    abstract fun linkSaverDao(): LinkSaverDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: LinkRoomDatabase? = null

        fun getDatabase(context: Context): LinkRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinkRoomDatabase::class.java,
                    DB_NAME
                ).createFromAsset(DB_NAME)
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}