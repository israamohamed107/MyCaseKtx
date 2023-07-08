package com.example.mycasektx.ui.login

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.mycasektx.R
import com.example.mycasektx.data.UserPreferences
import com.example.mycasektx.ui.USER_EMAIL
import com.example.mycasektx.ui.USER_NAME
import com.example.mycasektx.ui.USER_PHONE
import javax.inject.Inject

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userPreferences = UserPreferences(requireContext())

        Handler(Looper.myLooper()!!).postDelayed({
            userPreferences.preferencesFlow.asLiveData().observe(this) {
                if (it == null)
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                else{
                    USER_NAME = it.name.toString()
                    USER_EMAIL = it.email
                    USER_PHONE = "01234567810"
                    findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                }

            }

        }, 5000)
    }

}