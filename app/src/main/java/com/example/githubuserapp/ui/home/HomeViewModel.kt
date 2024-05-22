package com.example.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.repository.UserRepository
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.data.response.ResponseSearchUser
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUserHeadline() = userRepository.getListUser()
    fun searchUser(name: String) = userRepository.searchUser(name)
}