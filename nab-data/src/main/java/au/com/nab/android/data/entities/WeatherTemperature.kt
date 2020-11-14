package au.com.nab.android.data.entities

data class WeatherTemperature(
    val min: Double = 0.0,
    val max: Double = 0.0
) {
    fun avg() = (min + max) / 2
}