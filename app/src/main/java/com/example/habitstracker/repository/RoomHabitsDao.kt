package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.models.HabitType

@Dao
interface RoomHabitsDao : IHabitsRepository {
    @Query("SELECT * FROM HabitInfo") //TODO несколько запросов
    override fun getHabits(): LiveData<List<HabitInfo>>

    @Query("SELECT * FROM HabitInfo WHERE HabitInfo.type = \"${HabitType.Positive}\"")
    override fun getPositiveHabits(): LiveData<List<HabitInfo>>

    @Query("SELECT * FROM HabitInfo WHERE HabitInfo.type = \"${HabitType.Negative}\"")
    override fun getNegativeHabits(): LiveData<List<HabitInfo>>

    //TODO insert, update; transactions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun insertOrUpdate(habitInfo: HabitInfo)
}