package com.goat.weatherapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goat.weatherapp.R
import com.goat.weatherapp.databinding.RowLayoutBinding
import com.goat.weatherapp.model.HourlyData
import com.goat.weatherapp.utils.TimeUtil
import javax.inject.Inject

class MainAdapter
@Inject constructor(
    private var hourlyDatum: List<HourlyData>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {

    class DataViewHolder(
            private val binding: RowLayoutBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(hourlyData: HourlyData) {
            binding.apply {
                time.text = TimeUtil.convertDate(hourlyData.time)
                icon.text = hourlyData.icon
                val context = root.context
                temp.text = context.getString(R.string.temp, hourlyData.temperature.toString())
                weatherIcon.setIconResource(
                    context.getString(
                        context.resources.getIdentifier(
                            "wi_forecast_io_" + hourlyData.icon.replace("-", "_"), "string",
                            context.packageName
                        )
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = hourlyDatum.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(hourlyDatum[position])
    }

    fun setData(hourlyDatum: List<HourlyData>) {
        this.hourlyDatum = hourlyDatum
        notifyDataSetChanged()
    }
}