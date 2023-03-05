package com.example.retrofittest.retrofit

import com.example.retrofittest.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {


    @GET("/api/tasks")
    suspend fun getAllTasks(@Header("Authorization") token: String): Response<GenericData<List<TaskData>>>

    @POST("/api/register")
    suspend fun registerApi(@Body body: RegisterRequestBodyData): Response<GenericData<RegisterResponceData>>

    @POST("/api/login")
    suspend fun loginApi(@Body body: LoginRequestBodyData): Response<GenericData<RegisterResponceData>>

    @POST("/api/tasks")
    suspend fun createTask(
        @Body body: CreateTaskBodyData,
        @Header("Authorization") token: String
    ): Response<GenericData<TaskData>>

    @DELETE("/api/tasks/{id}")
    suspend fun deleteTask(@Header("Authorization") token: String, @Path("id") id: Int)

    @PUT("/api/tasks/{id}")
    suspend fun updateIsDone(
        @Path("id") id: Int,
        @Header("Authorization") token: String,
        @Body body: DoneRequestBodyData
    )

    @PATCH("/api/tasks/{id}")
    suspend fun updateTask(
        @Body body: CreateTaskBodyData,
        @Header("Authorization") token: String,
        @Path("id") id: Int
    )

}