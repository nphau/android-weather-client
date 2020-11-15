package au.com.nab.android.data.entities

import androidx.room.Entity
import au.com.nab.android.domain.entities.IWeatherModel
import au.com.nab.android.shared.utils.TimeUtils
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["timestamp"], tableName = "weathers")
data class WeatherEntity(
    @field:SerializedName("dt")
    val timestamp: Long,
    @field:SerializedName("pressure")
    override val pressure: Double?,
    @field:SerializedName("humidity")
    override val humidity: Double?,
    @field:SerializedName("weather")
    val weathers: List<WeatherStatus>? = listOf(),
    @field:SerializedName("temp")
    val temperature: WeatherTemperature? = null
) : IWeatherModel {

    override val date: String?
        get() = TimeUtils.toDate(timestamp * 1000, TimeUtils.PATTERN_E_MMM_dd_yyyy)

    override val avgTemperature: Double?
        get() = temperature?.avg() ?: 0.0

    override val description: String?
        get() = weathers?.firstOrNull()?.description
}