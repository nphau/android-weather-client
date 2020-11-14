package au.com.nab.android.data.transform

import au.com.nab.android.data.entities.WeatherEntities
import au.com.nab.android.domain.entities.Weather

object EntitiesTransformer {

    fun fromWeatherEntitiesToWeather(weather: WeatherEntities): Weather {
        return Weather(
            date = weather.date,
            avgTemperature = weather.avgTemperature,
            pressure = weather.pressure,
            humidity = weather.humidity,
            description = weather.description
        )
    }
}