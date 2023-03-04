package com.example.retrofittest.data.models

import com.google.gson.annotations.SerializedName

data class DoneRequestBodyData(
    @SerializedName("is_done")
    val isDone: Boolean
)
