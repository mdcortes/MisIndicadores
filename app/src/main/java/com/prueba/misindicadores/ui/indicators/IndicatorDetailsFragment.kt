package com.prueba.misindicadores.ui.indicators

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.databinding.FragmentIndicatorDetailBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class IndicatorDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: IndicatorDetailsViewModel
    private lateinit var binding: FragmentIndicatorDetailBinding

    private val args: IndicatorDetailsFragmentArgs by navArgs()

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
        binding.dateFormat = SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.getDefault())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getIndicator(args.indicatorCode)

        viewModel.indicator.observe(viewLifecycleOwner, Observer {
            it?: return@Observer

            binding.indicator = it
        })
    }
}