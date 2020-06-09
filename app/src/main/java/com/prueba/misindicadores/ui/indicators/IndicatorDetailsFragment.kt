package com.prueba.misindicadores.ui.indicators

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.databinding.FragmentIndicatorDetailBinding
import javax.inject.Inject

class IndicatorDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: IndicatorDetailsViewModel
    private lateinit var binding: FragmentIndicatorDetailBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application!! as MisIndicadoresApplication).appComponent
            .indicatorDetailsComponent().create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIndicatorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}