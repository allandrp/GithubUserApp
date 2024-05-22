package com.example.githubuserapp.ui.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.repository.UserRepository
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel(private val userRepository: UserRepository): ViewModel() {

    fun getFollowers(username: String, pageName: String) = userRepository.getFollowers(username, pageName)

}