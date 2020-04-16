package com.example.habitstracker

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity
@TypeConverters(UUIDConverter::class)
class HabitInfo(
    @PrimaryKey(autoGenerate = false) val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val numberOfRepeats: Int = 0,
    val numberOfDays: Int = 0,
    val color: Int = Color.WHITE,
    val priority: String = ""
) : Parcelable

class UUIDConverter{
    @TypeConverter
    fun fromUUID(uuid: UUID?) : String?{
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(string: String?): UUID?{
        return UUID.fromString(string)
    }
}

enum class HabitType(val type: String){
    Positive("Позитивная"),
    Negative("Негативная")
}