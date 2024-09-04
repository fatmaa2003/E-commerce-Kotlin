package com.example.e_commercekotlin.presentation.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.data.model.UserRole
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.presentation.viewmodels.LoginViewModel
import com.example.e_commercekotlin.presentation.screens.HomeActivity // Assuming HomeActivity is in the same package

class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var  loginViewModel: LoginViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel = LoginViewModel(Repository())

        loginObserver()

        view.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val username = view.findViewById<EditText>(R.id.usernameEditText).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordEditText).text.toString()
            loginViewModel.login(username, password)
        }


    }
    fun navToLogin(){
        val btn = view?.findViewById<Button>(R.id.loginButton)
        btn!!.setOnClickListener{
            findNavController().navigate(R.id.action_sign_in_to_Feed_fragment)
        }
    }

    private fun loginObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    navToLogin()
                    val data = resource.data
                    val userRole = UserRole.entries.find { UserRole -> UserRole.role.equals(other = data?.userDetails?.role, ignoreCase = true) }
                    navigateToHome(userRole ?: UserRole.USER)
                }
                is Resource.Error -> Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                is Resource.Loading -> {
                }
            }
        }
    }
    private fun navigateToHome(userRole: UserRole) {
        val intent = Intent(activity, MainActivity::class.java).apply {
            putExtra("USER_ROLE", userRole.name)
        }
        startActivity(intent)
        requireActivity().finish()
    }
}
