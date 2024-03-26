package com.example.githubuserapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.response.ResponseFollowers
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listUser = MutableLiveData<ArrayList<ResponseFollowersItem>>()
    val listUser: LiveData<ArrayList<ResponseFollowersItem>> = _listUser

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getFollowers(username: String, pageName: String){
        _isLoading.value = true

        val client = ApiConfig.initApiService().getFollowers(username, pageName)

        client.enqueue(object: Callback<ArrayList<ResponseFollowersItem>> {
            override fun onResponse(call: Call<ArrayList<ResponseFollowersItem>>, response: Response<ArrayList<ResponseFollowersItem>>) {
                _isLoading.postValue(false)
                if(response.isSuccessful){
                    _listUser.postValue(response.body())
                }else{
                    _errorMessage.postValue(Event(response.message()))
                }
            }

            override fun onFailure(call: Call<ArrayList<ResponseFollowersItem>>, error: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue(Event(error.message.toString()))
            }

        })
    }

}