package au.com.nab.android.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["id"], tableName = "weatherStatus")
data class WeatherStatus(
    val id: Double,
    val description: String?,
)