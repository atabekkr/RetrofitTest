package com.example.retrofittest.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofittest.data.models.ResultData
import com.example.retrofittest.domain.MainRepository
import com.example.retrofittest.retrofit.RetrofitHelper
import com.example.retrofittest.retrofit.TodoApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val repo = MainRepository(RetrofitHelper.getInstance().create(TodoApi::class.java))

    val getSuccessFlow = MutableSharedFlow<String>()
    val getMessageFlow = MutableSharedFlow<String>()
    val getErrorFlow = MutableSharedFlow<Throwable>()

    val getLoginFlow = MutableSharedFlow<String>()
    val getMessageLoginFlow = MutableSharedFlow<String>()
    val getErrorLoginFlow = MutableSharedFlow<Throwable>()

    suspend fun isSuccess(name: String, password: String, phone: String) {
        repo.registerApi(name, password, phone).onEach {
            when (it) {
                is ResultData.Success -> {
                    getSuccessFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    getMessageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getErrorFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    suspend fun isLogin(phone: String, password: String) {
        repo.loginApi(phone, password).onEach {
            when (it) {
                is ResultData.Success -> {
                    getLoginFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    getMessageLoginFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    getErrorLoginFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}