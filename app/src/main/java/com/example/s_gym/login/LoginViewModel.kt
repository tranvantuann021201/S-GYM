package com.example.s_gym.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.entity.User
import com.example.s_gym.database.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): ViewModel() {
    private var userRepository: UserRepository = UserRepository(application)
    private var settingRepository: SettingRepository = SettingRepository(application)
    private val fitnessBasicRepository = FitnessBasicRepository(application)

    suspend fun allBasic(userId: String): LiveData<List<FitnessBasic>> {
        return fitnessBasicRepository.allFitnessBasics(userId)
    }

    var settingDefault = Setting(
        0, "", 0,
        drinkMind = true,
        fitnessMind = true,
        fitnessMindTime = ""
    )

    private var daysRepository = DaysRepository(application)
//    var dayDefault = Days(0,)
    private var fitnessBasicDefault = FitnessBasicRepository(application)

    private var fitnessAdvanceDefault = FitnessAdvanceRepository(application)

    override fun onCleared() {
        super.onCleared()
        Log.e("MyViewModel", "onCleared")
    }

    fun insertFB(fitnessBasic: FitnessBasic) {
        CoroutineScope(Dispatchers.IO).launch {
            fitnessBasicRepository.insert(fitnessBasic)
        }
    }

    fun insertSetting(setting: Setting) {
        viewModelScope.launch {
            settingRepository.insert(setting)
        }
    }

    fun getSetting(userId: String): LiveData<Setting> {
        return settingRepository.getSettingsByUserId(userId)
    }

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