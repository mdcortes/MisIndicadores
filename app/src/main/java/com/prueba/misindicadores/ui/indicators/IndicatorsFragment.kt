package com.prueba.misindicadores.ui.indicators

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.R
import javax.inject.Inject

class IndicatorsFragment : Fragment() {

    companion object {
        fun newInstance() =
            IndicatorsFragment()
    }

    @Inject
    lateinit var indicatorsViewModel: IndicatorsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application!! as MisIndicadoresApplication).appComponent.indicatorsComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_indicators, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userGreetingTextView = view.findViewById<TextView>(R.id.user_greeting)
        var logoutButton = view.findViewById<Button>(R.id.logout)

        indicatorsViewModel.currentUser.observe(viewLifecycleOwner,
            Observer {
                it?: return@Observer

                userGreetingTextView.text = getString(R.string.user_greeting, it.displayName)
            })

        indicatorsViewModel.logoutResult.observe(viewLifecycleOwner,
            Observer {
                it?: return@Observer

                it.success?.let {
                    onLogoutComplete()
                }
            })

        logoutButton.setOnClickListener { indicatorsViewModel.logout() }
    }

    private fun onLogoutComplete() {
        findNavController().navigate(R.id.logout)
    }
}