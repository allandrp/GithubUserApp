package com.example.githubuserapp.utils.injection

import android.content.Context
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.repository.UserRepository
import com.example.githubuserapp.data.room.UserRoomDatabase

object Injection {

    fun provideUserRepository(context: Context) : UserRepository{
        val apiService = ApiConfig.initApiService()
        val db = UserRoomDatabase.getDatabase(context)
        return UserRepository.initRepository(apiService, db.favouritesDao())
    }
}