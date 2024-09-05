package com.example.e_commercekotlin.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.e_commercekotlin.R
import com.example.e_commercekotlin.Util.hide
import com.example.e_commercekotlin.Util.navToLogin
import com.example.e_commercekotlin.Util.setBottomNavVisibility
import com.example.e_commercekotlin.Util.show
import com.example.e_commercekotlin.Util.showToast
import com.example.e_commercekotlin.data.Resource
import com.example.e_commercekotlin.databinding.FragmentLoginBinding
import com.example.e_commercekotlin.domain.Repository
import com.example.e_commercekotlin.presentation.viewmodels.LoginViewModel


class LoginFragment : Fragment(R.layout.fragment_login), OnClickListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = LoginViewModel(Repository())
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setBottomNavVisibility(visible = false)
        handleUIClicks()
        loginObserver()
    }

    private fun handleUIClicks() {
        binding.loginButton.setOnClickListener(this)
    }

    private fun loginObserver() {
        loginViewModel.loginState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    binding.progressBar.progressBar.hide()
                    navToLogin(this)
                }

                is Resource.Error -> {
                    binding.progressBar.progressBar.hide()
                    showToast(message = resource.message.orEmpty())
                }

                is Resource.Loading -> {
                    binding.progressBar.progressBar.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View?) {
        when(view){
            binding.loginButton -> {
                val username = binding.usernameEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                loginViewModel.login(username, password)
            }
        }
    }
}
