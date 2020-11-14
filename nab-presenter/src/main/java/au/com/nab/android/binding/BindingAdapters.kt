package au.com.nab.android.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("temperature")
fun TextView.temperature(temperature: Double? = 0.0) {
    text = String.format("Average temperature: %d°C", temperature?.toInt())
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
fun TextView.description(description: String? ="") {
    text = String.format("Description: %s ", description)
}
