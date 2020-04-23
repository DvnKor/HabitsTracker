package com.example.habitstracker.repository

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.habitstracker.models.HabitInfo
import com.example.habitstracker.models.HabitType
import com.example.habitstracker.models.UUIDConverter
import java.util.*

@Dao
@TypeConverters(UUIDConverter::class)
interface RoomHabitsDao : IHabitsRepository {
    @Query("SELECT * FROM HabitInfo") //TODO несколько запросов *СДЕЛАНО*
    override fun getHabits(): LiveData<List<HabitInfo>>

    @Query("SELECT * FROM HabitInfo WHERE HabitInfo.type = \"${HabitType.Positive}\"")
    override fun getPositiveHabits(): LiveData<List<HabitInfo>>

    @Query("SELECT * FROM HabitInfo WHERE HabitInfo.type = \"${HabitType.Negative}\"")
    override fun getNegativeHabits(): LiveData<List<HabitInfo>>

    @Query("SELECT * FROM HabitInfo WHERE HabitInfo.id = :uuid")
    override fun getHabitById(uuid: UUID): HabitInfo?

    //TODO insert, update; transactions *СДЕЛАНО*
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    override fun insertOrUpdate(habitInfo: HabitInfo)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    override fun insert(habitInfo: HabitInfo)

    @Update
    override fun update(habitInfo: HabitInfo)

    @Transaction
    override fun insertOrUpdate(habitInfo: HabitInfo) {
        val existingHabit = getHabitById(habitInfo.id)
        if (existingHabit == null)
            insert(habitInfo)
        else
            update(habitInfo)
    }
}