package com.example.mycasektx.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mycasektx.R
import com.example.mycasektx.databinding.FragmentLoginBinding
import com.example.mycasektx.data.network.Resource
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding : FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)
        binding.btnLogin.setOnClickListener{
            lifecycleScope.launch {
                loginViewModel.login(binding.userName.text.toString().trim() ,binding.userPass.text.toString().trim())
            }


            loginViewModel.loginData.observe(viewLifecycleOwner){
                when(it){
                    is Resource.Success -> Toast.makeText(requireContext(),"login successful", Toast.LENGTH_SHORT).show()
                    is Resource.Failure -> Toast.makeText(requireContext(),"login failed", Toast.LENGTH_SHORT).show()

                }
            }
        }

    }



}