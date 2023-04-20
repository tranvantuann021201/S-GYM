package com.example.s_gym.api

import com.example.s_gym.database.Exercise
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

public interface WorkoutApiService {

    companion object{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://wger.de")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(WorkoutApiService::class.java)

    }

    @GET("/api/v2/exercise/")
    fun getExercises(): Call<List<Exercise>>
}