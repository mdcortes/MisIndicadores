package com.prueba.misindicadores.ui.indicators

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.prueba.misindicadores.data.model.Indicator
import com.prueba.misindicadores.databinding.IndicatorLayoutBinding


class IndicatorsAdapter (private val indicators: MutableList<Indicator>) : Adapter<IndicatorsAdapter.IndicatorsViewHolder>() {

    inner class IndicatorsViewHolder(val indicatorsLayoutBinding: IndicatorLayoutBinding)
        : RecyclerView.ViewHolder(indicatorsLayoutBinding.root)

    fun update(indicatorList: List<Indicator>) {
        if (indicators != indicatorList) {
            indicators.clear()
            indicators.addAll(indicatorList)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IndicatorsViewHolder(
        IndicatorLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = indicators.size

    override fun onBindViewHolder(holder: IndicatorsViewHolder, position: Int) {
        holder.indicatorsLayoutBinding.indicator = indicators[position]
    }
}