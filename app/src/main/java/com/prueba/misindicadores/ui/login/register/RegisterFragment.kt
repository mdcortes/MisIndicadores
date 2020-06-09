package com.prueba.misindicadores.ui.login.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prueba.misindicadores.MisIndicadoresApplication
import com.prueba.misindicadores.R
import com.prueba.misindicadores.databinding.FragmentRegisterBinding
import com.prueba.misindicadores.ui.login.LoggedInUserView
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : Fragment() {

    @Inject
    lateinit var registerViewModel: RegisterViewModel
    private lateinit var binding: FragmentRegisterBinding

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
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username.setText(args.email)
        password.setText(args.password)

        registerViewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormState ->
                if (registerFormState == null) {
                    return@Observer
                }

                register.isEnabled = registerFormState.isDataValid
                registerFormState.usernameError?.let {
                    username.error = getString(it)
                }
                registerFormState.displayNameError?.let {
                    display_name.error = getString(it)
                }
                registerFormState.passwordError?.let {
                    password.error = getString(it)
                }
            })

        registerViewModel.registerResult.observe(viewLifecycleOwner,
            Observer { registerResult ->
                registerResult ?: return@Observer

                loading.visibility = View.GONE

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
                    username.text.toString(),
                    display_name.text.toString(),
                    password.text.toString()
                )
            }
        }
        username.addTextChangedListener(afterTextChangedListener)
        password.addTextChangedListener(afterTextChangedListener)
        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerViewModel.register(
                    username.text.toString(),
                    display_name.text.toString(),
                    password.text.toString()
                )
            }
            false
        }

        register.setOnClickListener {
            loading.visibility = View.VISIBLE
            registerViewModel.register(
                username.text.toString(),
                display_name.text.toString(),
                password.text.toString()
            )
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        findNavController().navigate(RegisterFragmentDirections.loginFromRegister())
    }

    private fun showRegisterFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }
}