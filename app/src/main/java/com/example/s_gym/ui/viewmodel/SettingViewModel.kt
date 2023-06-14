package com.example.s_gym.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.s_gym.database.entity.User
import com.example.s_gym.database.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(application: Application): ViewModel() {
    private var userRepository: UserRepository = UserRepository(application)
    private val _selectedUser = MutableLiveData<User>()
    val selectedUser: LiveData<User> = _selectedUser

    fun getUserById(userId: String) {
        viewModelScope.launch {
            val user = userRepository.getUserById(userId)
            _selectedUser.value = user
        }
    }

    class SettingViewModelFactory(private val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(SettingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingViewModel(application) as T
            }
            throw IllegalAccessException("Unable construct viewModel")
        }
    }
}