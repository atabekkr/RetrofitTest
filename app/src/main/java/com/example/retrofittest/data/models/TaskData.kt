package com.example.retrofittest.data.models

import com.google.gson.annotations.SerializedName


data class TaskData(
    val id: Int,
    val task: String,
    val description: String,
    @SerializedName("is_done")
    val isDone: Boolean
)
