package com.example.siuverse.data.di

import android.content.Context
import com.example.siuverse.data.pref.UserPreference
import com.example.siuverse.data.pref.dataStore
import com.example.siuverse.data.repository.Repository
import com.example.siuverse.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(pref, apiService)
    }
}