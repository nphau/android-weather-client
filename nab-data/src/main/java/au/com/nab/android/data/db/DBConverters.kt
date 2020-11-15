package au.com.nab.android.data.db

import androidx.room.TypeConverter
import au.com.nab.android.data.entities.WeatherStatus
import au.com.nab.android.data.entities.WeatherTemperature
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object DBConverters {

    @TypeConverter
    fun fromWeatherStatus(weathers: List<WeatherStatus>?): String? {
        return Gson().toJson(weathers)
    }

    @TypeConverter
    fun toWeatherStatus(data: String?): List<WeatherStatus>? {
        return Gson().fromJson(data, object : TypeToken<List<WeatherStatus?>?>() {}.type)
    }


    @TypeConverter
    fun fromWeatherTemperature(temperature: WeatherTemperature?): String? {
        return Gson().toJson(temperature)
    }

    @TypeConverter
    fun toWeatherTemperature(data: String?): WeatherTemperature? {
        return Gson().fromJson(data, WeatherTemperature::class.java)
    }

}