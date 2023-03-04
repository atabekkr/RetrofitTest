package com.example.retrofittest.domain

import android.util.Log
import com.example.retrofittest.data.local.LocalStorage
import com.example.retrofittest.data.models.*
import com.example.retrofittest.retrofit.TodoApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import packagename.telegramclone.utils.toast

class MainRepository(private val api: TodoApi) {

    suspend fun getAllTasks() = flow {

        val token = LocalStorage().token

        val responce = api.getAllTasks("Bearer $token")

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    }.catch {
        emit(ResultData.Error(it))
    }

    suspend fun registerApi(name: String, password: String, phone: String) = flow {
        val responce =
            api.registerApi(RegisterRequestBodyData(name, password, phone))

        if (responce.isSuccessful) {
            LocalStorage().token = responce.body()!!.payload.token
            emit(ResultData.Success(responce.body()!!.message))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    }

    suspend fun loginApi(phone: String, password: String) = flow {
        val responce =
            api.loginApi(LoginRequestBodyData(password = password, phone = phone))

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload.token))
        } else {
            Log.d("usinday", "ne boldi")
            emit(ResultData.Message(responce.message()))
        }
    }

    suspend fun createTask(description: String, task: String) = flow {
        val responce =
            api.createTask(
                CreateTaskBodyData(description = description, task = task),
                "Bearer ${LocalStorage().token}"
            )

        if (responce.isSuccessful) {
            emit(ResultData.Success(responce.body()!!.payload.description))
        } else {
            emit(ResultData.Message(responce.message()))
        }
    }

    suspend fun deleteTask(id: Int) {
        api.deleteTask("Bearer ${LocalStorage().token}", id)
    }

    suspend fun updateIsDone(isDone: Boolean, id: Int) {
        api.updateIsDone(DoneRequestBodyData(isDone), "Bearer ${LocalStorage().token}", id)
    }

    suspend fun updateTask(desc: String, task: String, id: Int) {
        api.updateTask(
            CreateTaskBodyData(description = desc, task = task),
            token = "Bearer ${LocalStorage().token}",
            id = id
        )
    }
}