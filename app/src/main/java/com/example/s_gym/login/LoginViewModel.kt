package com.example.s_gym.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.User
import com.example.s_gym.database.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): ViewModel() {
    private var userRepository: UserRepository = UserRepository(application)

    fun insertUser(user: User) {
        viewModelScope.launch {
            userRepository.insertUser(user)
        }
    }

    class LoginViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}