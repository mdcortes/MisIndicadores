package com.prueba.misindicadores.ui.register

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.navArgs
import com.prueba.misindicadores.R

class RegisterFragment : Fragment() {

    companion object {
        fun newInstance() =
            RegisterFragment()
    }

    private lateinit var viewModel: RegisterViewModel

    private val args: RegisterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var usernameEditText = view.findViewById<EditText>(R.id.username)
        var passwordEditText = view.findViewById<EditText>(R.id.password)
        var registerButton = view.findViewById<Button>(R.id.register)

        usernameEditText.setText(args.email)
        passwordEditText.setText(args.password)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
        // TODO: Use the ViewModel
    }

}