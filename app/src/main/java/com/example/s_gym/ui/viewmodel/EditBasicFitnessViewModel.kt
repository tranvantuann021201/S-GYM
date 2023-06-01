package com.example.s_gym.ui.viewmodel

import com.example.s_gym.api.Exercise
import com.example.s_gym.database.entity.Exercises

class EditBasicFitnessViewModel() {
    var exerciseList = mutableListOf<Exercises>()
    var sortedExercises = mutableListOf<Exercises>()
}