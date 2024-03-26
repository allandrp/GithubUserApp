package com.example.githubuserapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.response.ResponseUserDetail
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailUser = MutableLiveData<ResponseUserDetail>()
    val detailUser: LiveData<ResponseUserDetail> = _detailUser

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.initApiService().getDetailUser(username)

        client.enqueue(object : Callback<ResponseUserDetail> {
            override fun onResponse(
                call: Call<ResponseUserDetail>,
                response: Response<ResponseUserDetail>
            ) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    _detailUser.postValue(response.body())
                }else{
                    _errorMessage.postValue(Event(response.errorBody().toString()))
                }
            }

            override fun onFailure(call: Call<ResponseUserDetail>, error: Throwable) {
                _isLoading.postValue(false)
                _errorMessage.postValue(Event(error.message.toString()))
            }
        })
    }

}