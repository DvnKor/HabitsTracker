package com.example.habitstracker.models

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.*
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type
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


class UUIDConverter {
    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }

    @TypeConverter
    fun toUUID(string: String?): UUID? {
        return UUID.fromString(string)
    }
}


class HabitJsonSerializer : JsonSerializer<HabitInfo> {
    override fun serialize(
        src: HabitInfo,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonObject().apply {
        addProperty("color", src.color)
        addProperty("count", src.numberOfRepeats)
        addProperty("date", 0) // TODO: передавать дату
        addProperty("description", src.description)
        addProperty("frequency", src.numberOfDays)
        val priority = when (src.priority) {
            HabitPriority.High -> 0
            HabitPriority.Average -> 1
            HabitPriority.Low -> 2
            else -> -1
        }
        addProperty("priority", priority)
        addProperty("title", src.name)
        val type = when (src.type) {
            HabitType.Positive -> 0
            HabitType.Negative -> 1
            else -> -1
        }
        addProperty("type", type)
        addProperty("uid", src.id.toString())
    }

}

class HabitJsonDeserializer : JsonDeserializer<HabitInfo> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): HabitInfo = HabitInfo(
        UUID.fromString(json.asJsonObject.get("uid").asString),
        json.asJsonObject.get("title").asString,
        json.asJsonObject.get("description").asString,
        when (json.asJsonObject.get("type").asInt) {
            0 -> HabitType.Positive
            1 -> HabitType.Negative
            else -> HabitType.Positive
        },
        json.asJsonObject.get("count").asInt,
        json.asJsonObject.get("frequency").asInt,
        json.asJsonObject.get("color").asInt,
        when (json.asJsonObject.get("priority").asInt) {
            0 -> HabitPriority.High
            1 -> HabitPriority.Average
            2 -> HabitPriority.Low
            else -> HabitPriority.High
        }
    )
}

