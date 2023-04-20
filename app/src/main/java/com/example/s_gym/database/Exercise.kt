package com.example.s_gym.database

import com.google.gson.annotations.SerializedName

data class Exercise(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("language")
    val language: Int,
    @SerializedName("muscles")
    val muscles: List<Int>,
    @SerializedName("category")
    val category: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("uuid")
    val uuid: String

)
