package com.example.habitstracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habitstracker.models.HabitInfo

@Database(entities = [HabitInfo::class], version = 1)
abstract class HabitsDatabase : RoomDatabase() {
    companion object {
        private var instance: HabitsDatabase? = null
        fun getInstance(context: Context): HabitsDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    HabitsDatabase::class.java, "HabitsDatabase"
                ).build()
            }
            return instance!!
        }
    }

    abstract fun habitsDao(): RoomHabitsDao
}
