package au.com.nab.android.data.entities

import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection

data class WeatherResponse(
    @SerializedName("cod")
    val code: String = "",
    @SerializedName("city")
    val city: CityEntity? = null,
    var message: String = "",
    @SerializedName("list")
    val items: List<WeatherEntity>? = listOf()
) {

    fun isSuccess(): Boolean {
        return code == HttpURLConnection.HTTP_OK.toString()
    }
}