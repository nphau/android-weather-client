package au.com.nab.android.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Weather(
    override val date: String?,
    override val avgTemperature: Double?,
    override val pressure: Double?,
    override val humidity: Double?,
    override val description: String?
) : Parcelable, IWeatherModel