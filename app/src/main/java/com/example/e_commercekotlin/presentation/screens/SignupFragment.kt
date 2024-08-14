package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.presentation.viewmodels.SignupViewModel
import kotlinx.coroutines.launch

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var signupViewModel: SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = SignupViewModel(Repository())

        view.findViewById<Button>(R.id.signupButton).setOnClickListener {
            val firstName = view.findViewById<EditText>(R.id.firstNameEditText).text.toString()
            val lastName = view.findViewById<EditText>(R.id.lastNameEditText).text.toString()
            val email = view.findViewById<EditText>(R.id.emailEditText).text.toString()
            val username = view.findViewById<EditText>(R.id.usernameEditText).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()

            signupViewModel.signup(signupRequest = SignupRequest(
                firstName= firstName ,
                lastName= lastName ,
                email= email ,
                username= username ,
                password= password
            ))
        }

        signUpObserver()
    }

    private fun signUpObserver() {
        lifecycleScope.launch {
            signupViewModel.signupState.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Resource.Error -> Toast.makeText(
                        requireContext(),
                        resource.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    is Resource.Loading -> {}
                }
            }
        }
    }
}

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.SignupRequest
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.presentation.viewmodels.SignupViewModel
import kotlinx.coroutines.launch

class SignupFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var signupViewModel: SignupViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signupViewModel = SignupViewModel(Repository())

        view.findViewById<Button>(R.id.signupButton).setOnClickListener {
            val firstName = view.findViewById<EditText>(R.id.firstNameEditText).text.toString()
            val lastName = view.findViewById<EditText>(R.id.lastNameEditText).text.toString()
            val email = view.findViewById<EditText>(R.id.emailEditText).text.toString()
            val username = view.findViewById<EditText>(R.id.usernameEditText).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()

            signupViewModel.signup(signupRequest = SignupRequest(
                     firstName= firstName ,
                     lastName= lastName ,
                 email= email ,
                 username= username ,
                 password= password
            ))
        }

        signUpObserver()
    }

    private fun signUpObserver() {
        lifecycleScope.launch {
            signupViewModel.signupState.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Toast.makeText(requireContext(), "Sign up successful", Toast.LENGTH_SHORT)
                            .show()
                    }

                    is Resource.Error -> Toast.makeText(
                        requireContext(),
                        resource.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    is Resource.Loading -> {}
                }
            }
        }
    }
}
