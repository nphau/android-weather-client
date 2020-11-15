package au.com.nab.android.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherTemperature")
data class WeatherTemperature(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val min: Double = 0.0,
    val max: Double = 0.0
) {
    fun avg() = (min + max) / 2
}