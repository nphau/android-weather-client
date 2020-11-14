package au.com.nab.android.domain.entities

interface IWeatherModel {
    val date: String?
    val avgTemperature: Double?
    val pressure: Double?
    val humidity: Double?
    val description: String?
}