package com.prueba.misindicadores.ui.login.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.R
import com.prueba.misindicadores.ui.login.LoggedInUserView
import javax.inject.Inject

class RegisterFragment : Fragment() {

    @Inject
    lateinit var registerViewModel: RegisterViewModel

    private val args: RegisterFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application!! as MisIndicadoresApplication).appComponent.registerComponent()
            .create().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val usernameEditText = view.findViewById<EditText>(R.id.username)
        val displayNameEditText = view.findViewById<EditText>(R.id.display_name)
        val passwordEditText = view.findViewById<EditText>(R.id.password)
        val registerButton = view.findViewById<Button>(R.id.register)

        val loadingProgressBar = view.findViewById<ProgressBar>(R.id.loading)

        usernameEditText.setText(args.email)
        passwordEditText.setText(args.password)

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormState ->
                if (registerFormState == null) {
                    return@Observer
                }

                registerButton.isEnabled = registerFormState.isDataValid
                registerFormState.usernameError?.let {
                    usernameEditText.error = getString(it)
                }
                registerFormState.displayNameError?.let {
                    displayNameEditText.error = getString(it)
                }
                registerFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
            })

        registerViewModel.registerResult.observe(viewLifecycleOwner,
            Observer { registerResult ->
                registerResult ?: return@Observer

                loadingProgressBar.visibility = View.GONE

                registerResult.error?.let {
                    showRegisterFailed(it)
                }

                registerResult.success?.let {
                    updateUiWithUser(it)
                }
            })
        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                registerViewModel.registerDataChanged(
                    usernameEditText.text.toString(),
                    displayNameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
        }
        usernameEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                    usernameEditText.text.toString(),
                    displayNameEditText.text.toString(),
                    passwordEditText.text.toString()
                )
            }
            false
        }

        registerButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            registerViewModel.register(
                usernameEditText.text.toString(),
                displayNameEditText.text.toString(),
                passwordEditText.text.toString()
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        findNavController().navigate(R.id.login_from_register)
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}