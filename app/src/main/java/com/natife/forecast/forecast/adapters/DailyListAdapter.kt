package com.natife.forecast.forecast.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.natife.forecast.R
import com.natife.forecast.forecast.data.ForecastPresentation
import kotlinx.android.synthetic.main.daily_list_item.view.*

class DailyListAdapter(
    private val forecasts: ArrayList<ForecastPresentation>,
    private val itemClickedListener: (Int) -> Unit
) :
    RecyclerView.Adapter<DailyListAdapter.DailyHolder>() {

    private var checkedPosition = 0

    class DailyHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var view: View = v
        private var forecast: ForecastPresentation? = null

        fun bindForecast(forecast: ForecastPresentation, isSelected: Boolean) {
            this.forecast = forecast

            view.day.text = this.forecast!!.weekDay
            view.temp.text = this.forecast!!.temp
            view.icon.setImageResource(this.forecast!!.weatherIconBlack)

            if (isSelected) {
                view.background = AppCompatResources.getDrawable(view.context, R.color.colorGray)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyHolder {
        val inflatedView = parent.inflate(R.layout.daily_list_item, false)
        return DailyHolder(inflatedView).listen { pos, type ->
            checkedPosition = pos;
            itemClickedListener.invoke(checkedPosition)
        }


    }

    override fun onBindViewHolder(holder: DailyHolder, position: Int) {
        val itemHourly = forecasts[position]
        holder.bindForecast(itemHourly, position == checkedPosition)
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }
}

private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}