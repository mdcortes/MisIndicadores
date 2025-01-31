package com.prueba.misindicadores.ui.indicators

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.R
import com.prueba.misindicadores.data.model.Indicator
import com.prueba.misindicadores.databinding.FragmentIndicatorsBinding
import kotlinx.android.synthetic.main.fragment_indicators.*
import javax.inject.Inject

class IndicatorsFragment : Fragment() {

    @Inject
    lateinit var indicatorsViewModel: IndicatorsViewModel
    private lateinit var binding: FragmentIndicatorsBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application!! as MisIndicadoresApplication).appComponent.indicatorsComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        binding = FragmentIndicatorsBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.indicatorsViewModel = indicatorsViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        indicators_loading_progress_bar.visibility = View.VISIBLE

        indicators_recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = IndicatorsAdapter(mutableListOf(), onItemClick)
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
                indicators_loading_progress_bar.visibility = View.GONE
                it?: return@Observer

                (indicators_recycler_view.adapter as IndicatorsAdapter).update(it)
            })
    }

    private val onItemClick = { indicator: Indicator ->
        findNavController().navigate(
            IndicatorsFragmentDirections.details(indicator.codigo)
        )
    }

    private fun onLogoutComplete() {
        findNavController().navigate(R.id.logout)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.code_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            setOnQueryTextListener(onQuery)
        }
    }

    private val onQuery: SearchView.OnQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            indicatorsViewModel.filterByCode(query)
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            indicatorsViewModel.filterByCode(newText)
            return true
        }
    }
}