package com.example.habitstracker

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class HabitInfo(
    val id: UUID = UUID.randomUUID(),
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val numberOfRepeats: Int = 0,
    val numberOfDays: Int = 0,
    val color: Int = Color.WHITE,
    val priority: String = ""
) : Parcelable

enum class HabitType(val type: String){
    Positive("Позитивная"),
    Negative("Негативная")

}