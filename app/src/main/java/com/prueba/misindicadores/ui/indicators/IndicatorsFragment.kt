package com.prueba.misindicadores.ui.indicators

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.R
import com.prueba.misindicadores.databinding.FragmentIndicatorsBinding
import kotlinx.android.synthetic.main.fragment_indicators.*
import kotlinx.android.synthetic.main.fragment_indicators.view.*
import kotlinx.android.synthetic.main.fragment_indicators.view.indicators_recycler_view
import javax.inject.Inject

class IndicatorsFragment : Fragment() {

    companion object {
        fun newInstance() =
            IndicatorsFragment()
    }

    @Inject
    lateinit var indicatorsViewModel: IndicatorsViewModel
    lateinit var binding: FragmentIndicatorsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application!! as MisIndicadoresApplication).appComponent.indicatorsComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.indicatorsViewModel = indicatorsViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        indicators_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = IndicatorsAdapter(mutableListOf())
        }

        indicatorsViewModel.logoutResult.observe(viewLifecycleOwner,
            Observer {
                it?: return@Observer

                it.success?.let {
                    onLogoutComplete()
                }
            })

        indicatorsViewModel.indicatorsList.observe(viewLifecycleOwner,
            Observer {
                it?: return@Observer

                (indicators_recycler_view.adapter as IndicatorsAdapter).update(it)
            })
    }

    private fun onLogoutComplete() {
        findNavController().navigate(R.id.logout)
    }
}