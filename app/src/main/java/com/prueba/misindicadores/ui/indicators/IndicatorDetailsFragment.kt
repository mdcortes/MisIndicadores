package com.prueba.misindicadores.ui.indicators

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prueba.misindicadores.R

class IndicatorDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = IndicatorDetailsFragment()
    }

    private lateinit var viewModel: IndicatorDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_indicator_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IndicatorDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

}