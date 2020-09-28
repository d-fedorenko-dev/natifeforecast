package com.natife.forecast.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.natife.forecast.R
import kotlinx.android.synthetic.main.daily_list_item.view.*

class DailyListAdapter(private val forecasts: ArrayList<DailyForecast>) :
    RecyclerView.Adapter<DailyListAdapter.DailyHolder>() {

    class DailyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var forecast: DailyForecast? = null

        fun bindForecast(forecast: DailyForecast) {
            this.forecast = forecast

            view.day.text = forecast.day
            view.temp.text = forecast.temp
            view.icon.setImageResource(forecast.icon)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val inflatedView = parent.inflate(R.layout.daily_list_item, false)
        return DailyHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
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

class DailyForecast(val day: String, val temp: String, val icon: Int)