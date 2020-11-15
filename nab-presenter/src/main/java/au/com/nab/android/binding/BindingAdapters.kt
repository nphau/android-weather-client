package au.com.nab.android.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import au.com.nab.android.R

@BindingAdapter("date")
fun TextView.date(date: String? = "") {
    text = String.format("Date: %s", date)
}

@BindingAdapter("temperature")
fun TextView.temperature(temperature: Double? = 0.0) {
    text = String.format(context.getString(R.string.format_temperature), temperature)
}

@BindingAdapter("pressure")
fun TextView.pressure(pressure: Double? = 0.0) {
    text = String.format("Pressure: %d", pressure?.toInt())
}

@BindingAdapter("humidity")
fun TextView.humidity(humidity: Double? = 0.0) {
    text = String.format("Humidity: %.0f%% ", humidity)
}

@BindingAdapter("description")
fun TextView.description(description: String? = "") {
    text = String.format("Description: %s ", description)
}
