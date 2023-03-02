package com.example.retrofittest.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofittest.data.models.ResultData
import com.example.retrofittest.data.models.TaskData
import com.example.retrofittest.domain.MainRepository
import com.example.retrofittest.retrofit.RetrofitHelper
import com.example.retrofittest.retrofit.TodoApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TasksViewModel(application: Application) : AndroidViewModel(application) {

    val repo = MainRepository(RetrofitHelper.getInstance().create(TodoApi::class.java))

    val getAllTasksFlow = MutableSharedFlow<List<TaskData>>()
    val getMessageFlow = MutableSharedFlow<String>()
    val getErrorFlow = MutableSharedFlow<Throwable>()

    val getTaskFlow = MutableSharedFlow<String>()
    val getTaskMessageFlow = MutableSharedFlow<String>()
    val getTaskErrorFlow = MutableSharedFlow<Throwable>()

    suspend fun getAllTasks() {
        repo.getAllTasks().onEach {
            when (it) {
                is ResultData.Success -> {
                    getAllTasksFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    getMessageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getTaskErrorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun createTask(desc: String, task: String) {
        repo.createTask(desc, task).onEach {
            when (it) {
                is ResultData.Success -> {
                    getTaskFlow.emit(it.data)
                    Log.w("RRRR", it.data)
                }
                is ResultData.Message -> {
                    getTaskMessageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getErrorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

}