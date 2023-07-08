package com.example.mycasektx.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mycasektx.databinding.FragmentLoginBinding
import com.example.mycasektx.data.network.Resource
import com.example.mycasektx.data.responses.LoginResponse
import com.example.mycasektx.ui.enable
import com.example.mycasektx.ui.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onClick()


    }

    fun onClick() {
        binding.progressBar.visible(false)
        binding.btnLogin.enable(false)


        binding.btnLogin.setOnClickListener {
            binding.progressBar.visible(true)
            lifecycleScope.launch {
                // make input validation
                binding.progressBar.visible(true)
                login()
            }
        }

        binding.userPass.addTextChangedListener {
            val email = binding.userName.text.toString().trim()
            binding.btnLogin.enable(email.isNotEmpty() && it.toString().isNotEmpty())
        }

        loginViewModel.loginData.observe(viewLifecycleOwner) { response ->
            binding.progressBar.visible(false)

            when (response) {

                is Resource.Success -> {

                    saveUserInfo(response)
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                }

                is Resource.Failure -> Toast.makeText(
                    requireContext(),
                    "login failed",
                    Toast.LENGTH_SHORT
                ).show()

            }


        }
    }


    private fun saveUserInfo(response: Resource.Success<LoginResponse>) {
        lifecycleScope.launch {
            loginViewModel.userPreferences.setUserName(response.value.user.name)
            loginViewModel.userPreferences.setUserEmail(response.value.user.email)
            loginViewModel.userPreferences.setUserPhone(response.value.user.phone)
            loginViewModel.userPreferences.setUserToken(response.value.token)
        }
    }


    private fun login() {
        val userName = binding.userName.text.toString().trim()
        val password = binding.userPass.text.toString().trim()

        loginViewModel.login(userName, password)
    }
}
