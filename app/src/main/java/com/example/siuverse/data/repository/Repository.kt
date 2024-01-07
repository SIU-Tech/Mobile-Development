package com.example.siuverse.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.siuverse.data.pref.UserPreference

import com.example.siuverse.data.response.ResponseRegister
import com.example.siuverse.data.retrofit.ApiService

class Repository (
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    @SuppressLint("SuspiciousIndentation")
    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<Result<ResponseRegister>> {
        return liveData {
            try {
                val response = apiService.register(name, email, password)
                emit(Result.Success(response))

            } catch (e: Exception) {
                Log.d("Register", e.message.toString())
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(userPreference, apiService)
            }.also { instance = it }
    }

}