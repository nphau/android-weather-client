package au.com.nab.android.screens.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import au.com.nab.android.R
import au.com.nab.android.databinding.RowWeatherBinding
import au.com.nab.android.domain.entities.Weather
import au.com.nab.android.shared.libs.executors.AppExecutors
import au.com.nab.android.shared.screens.adapters.CoreAdapter
import au.com.nab.android.shared.screens.adapters.DataBindingViewHolder

class WeathersAdapters : CoreAdapter<Weather>(AppExecutors(), COMPARE) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserViewHolder).bind(get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UserViewHolder.create(parent)
    }

    class UserViewHolder(binding: RowWeatherBinding) :
        DataBindingViewHolder<Weather>(binding) {

        companion object {
            fun create(parent: ViewGroup): UserViewHolder {
                return UserViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.row_weather,
                        parent,
                        false
                    )
                )
            }
        }

        override fun bind(item: Weather?) {
            (binding as RowWeatherBinding).item = item
            super.bind(item)
        }
    }

    companion object {
        private val COMPARE = object : DiffUtil.ItemCallback<Weather>() {
            override fun areContentsTheSame(oldItem: Weather, newItem: Weather): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areItemsTheSame(oldItem: Weather, newItem: Weather): Boolean {
                return oldItem == newItem
            }
        }
    }

}