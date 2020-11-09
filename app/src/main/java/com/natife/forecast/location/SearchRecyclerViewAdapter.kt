package com.natife.forecast.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.natife.forecast.R
import com.natife.forecast.location.data.SearchPresentation
import kotlinx.android.synthetic.main.fragment_location_item.view.*


class SearchRecyclerViewAdapter(
    private val values: ArrayList<SearchPresentation>,
    private val itemClickedListener: (Double, Double) -> Unit
) :
    RecyclerView.Adapter<SearchRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_location_item, parent, false)

        return ViewHolder(view).listen { pos, type ->
            val item = values.get(pos)
            val lat = item.lat.toDouble()
            val lon = item.lon.toDouble()
            itemClickedListener.invoke(lat, lon)

        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.cityName.text = item.name
    }

    override fun getItemCount(): Int = values.size

    fun getItem(id: Int): SearchPresentation? {
        return values[id]
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.cityName)

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }


    }

    fun <T : RecyclerView.ViewHolder> T.listen(event: (position: Int, type: Int) -> Unit): T {
        itemView.setOnClickListener {
            event.invoke(adapterPosition, itemViewType)
        }
        return this
    }
}