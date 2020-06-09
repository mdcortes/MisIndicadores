package com.prueba.misindicadores.ui.indicators

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.prueba.misindicadores.R
import com.prueba.misindicadores.data.model.Indicator


class IndicatorsAdapter (private val indicators: MutableList<Indicator>) : Adapter<IndicatorsAdapter.IndicatorsViewHolder>() {

    inner class IndicatorsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(indicator: Indicator) {
            itemView.findViewById<TextView>(R.id.indicator_name).text =
                itemView.context.getString(R.string.indicator_name, indicator.nombre)

            itemView.findViewById<TextView>(R.id.indicator_value).text =
                itemView.context.getString(R.string.indicator_value, indicator.valor)
        }
    }

    fun update(indicatorList: List<Indicator>) {
        if (indicators != indicatorList) {
            indicators.clear()
            indicators.addAll(indicatorList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IndicatorsViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.indicator_layout,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = indicators.size

    override fun onBindViewHolder(holder: IndicatorsViewHolder, position: Int) {
        holder.bind(indicators[position])
    }
}