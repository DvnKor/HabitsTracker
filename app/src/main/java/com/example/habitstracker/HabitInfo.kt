package com.example.habitstracker

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class HabitInfo(
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val numberOfRepeats: Int = 0,
    val numberOfDays: Int = 0,
    val color: Int = Color.WHITE,
    val priority: String = ""
) : Parcelable