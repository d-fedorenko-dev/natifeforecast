package com.natife.forecast.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.natife.forecast.R
import kotlinx.android.synthetic.main.hourly_list_item.view.*


class HourlyListAdapter(private val forecasts: ArrayList<HourlyForecast>) :
    RecyclerView.Adapter<HourlyListAdapter.HourlyHolder>() {

    class HourlyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var forecast: HourlyForecast? = null

        fun bindForecast(forecast: HourlyForecast) {
            this.forecast = forecast

            view.hour.text = forecast.hour
            view.temp.text = forecast.temp
            view.itemImage.setImageResource(forecast.icon)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyHolder {
        val inflatedView = parent.inflate(R.layout.hourly_list_item, false)
        return HourlyHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: HourlyHolder, position: Int) {
        val itemHourly = forecasts[position]
        holder.bindForecast(itemHourly)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }
}

private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

class HourlyForecast(val hour: String, val temp: String, val icon: Int)
