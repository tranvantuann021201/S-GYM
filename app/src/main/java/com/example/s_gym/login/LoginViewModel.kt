package com.example.s_gym.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.s_gym.database.entity.Days
import com.example.s_gym.database.entity.FitnessBasic
import com.example.s_gym.database.entity.Setting
import com.example.s_gym.database.entity.User
import com.example.s_gym.database.repository.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class LoginViewModel(application: Application): ViewModel() {
    private var userRepository: UserRepository = UserRepository(application)
    private var settingRepository: SettingRepository = SettingRepository(application)
    private val fitnessBasicRepository = FitnessBasicRepository(application)
    private var daysRepository = DaysRepository(application)

    fun getListDay(userId: String): LiveData<List<Days>> {
        return daysRepository.getAllDays(userId)
    }

    fun insertListDay(currentUser: FirebaseUser, reference: DatabaseReference, dataSnapshot: DataSnapshot) {
        for(day in dataSnapshot.children) {
            val dayConverted = day.getValue(Days::class.java)
            if (dayConverted != null) {
                CoroutineScope(Dispatchers.IO).launch {
                    daysRepository.insertDay(dayConverted)
                }
                reference.child("Days").child(currentUser!!.uid).child(dayConverted.name).setValue(dayConverted)
            }
        }
    }

    fun insertDefaultDay(currentUser: FirebaseUser, reference: DatabaseReference) {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val dateString = dateFormat.format(currentDate)
        val defaultDay: Days
        if (currentUser != null) {
            defaultDay = Days(4, dateString, 0, 10, 3, 55.5, 170.0, 120.2, 19.1, currentUser!!.uid)
            CoroutineScope(Dispatchers.IO).launch {
                daysRepository.insertDay(defaultDay)
            }
            reference.child("Days").child(currentUser!!.uid).child(defaultDay.name).setValue(defaultDay)
        }
    }

    suspend fun allBasic(userId: String): LiveData<List<FitnessBasic>> {
        return fitnessBasicRepository.allFitnessBasics(userId)
    }

    var settingDefault = Setting(
        0, "", 0,
        drinkMind = true,
        fitnessMind = true,
        fitnessMindTime = ""
    )

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