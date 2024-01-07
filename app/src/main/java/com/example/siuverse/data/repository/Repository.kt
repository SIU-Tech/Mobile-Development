package com.example.siuverse.data.repository

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.siuverse.data.pref.UserPreference
import com.example.siuverse.data.response.ResponseLogin

import com.example.siuverse.data.response.ResponseRegister
import com.example.siuverse.data.retrofit.ApiService
import com.example.siuverse.ui.model.UserModel
import kotlinx.coroutines.flow.Flow

class Repository (
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() {
        userPreference.logout()
    }

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

    fun login(email: String, password: String): LiveData<Result<ResponseLogin>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, password)
            if (response.user.token.isNotBlank()) {
                val userModel = UserModel(email, response.user.token, true)
                saveSession(userModel)
                emit(Result.Success(response))
            } else {
                emit(Result.Error("Token is empty"))
            }
        } catch (e: Exception) {
            Log.d("Login", e.message.toString())
            emit(Result.Error(e.message.toString()))
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