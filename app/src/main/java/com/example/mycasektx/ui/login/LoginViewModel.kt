package com.example.mycasektx.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycasektx.data.UserPreferences
import com.example.mycasektx.data.network.Resource
import com.example.mycasektx.data.repo.AuthRepository
import com.example.mycasektx.data.responses.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val userPreferences: UserPreferences
) : ViewModel() {


    private var _loginData: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val loginData: LiveData<Resource<LoginResponse>> = _loginData

    val preferencesFlow = userPreferences.preferencesFlow
    fun login(
        userName: String,
        password: String
    ) = viewModelScope.launch {
        _loginData.postValue(authRepository.login(userName, password))
    }
}