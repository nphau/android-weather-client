package au.com.nab.android.data.entities

import androidx.room.Entity

@Entity(primaryKeys = ["cityName"], tableName = "weathersResponse")
data class WeatherResponseEntity(
    var cityName: String = "",
    val city: CityEntity? = null,
    val items: List<WeatherEntity>? = listOf()
)