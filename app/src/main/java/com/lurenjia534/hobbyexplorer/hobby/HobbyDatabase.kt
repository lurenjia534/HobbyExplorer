package com.lurenjia534.hobbyexplorer.hobby

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hobby::class], version = 1, exportSchema = false)
abstract class HobbyDatabase : RoomDatabase() {
    abstract fun hobbyDao(): HobbyDao

    companion object {
        @Volatile
        private var INSTANCE: HobbyDatabase? = null

        fun getDatabase(context: Context): HobbyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HobbyDatabase::class.java,
                    "hobby_database"
                )
                    .createFromAsset("new_hobbies.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
