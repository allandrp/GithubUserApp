package com.example.githubuserapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.api.ApiConfig
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.data.response.ResponseSearchUser
import com.example.githubuserapp.utils.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<ResponseListUserItem>>()
    val listUser: LiveData<ArrayList<ResponseListUserItem>> = _listUser

    private val _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getListUser()
    }

    fun getListUser() {
        _isLoading.value = true

        val client = ApiConfig.initApiService().getListUser()

        client.enqueue(object : Callback<ArrayList<ResponseListUserItem>> {
            override fun onResponse(
                call: Call<ArrayList<ResponseListUserItem>>,
                response: Response<ArrayList<ResponseListUserItem>>
            ) {
                if (response.isSuccessful) {
                    _listUser.postValue(response.body())
                    _isLoading.postValue(false)
                }
            }

            override fun onFailure(p0: Call<ArrayList<ResponseListUserItem>>, p1: Throwable) {
                _errorMessage.postValue(Event("Failed"))
                _isLoading.postValue(false)
            }

        })
    }

    fun searchUser(name: String){
        _isLoading.value = true
        val client = ApiConfig.initApiService().searchUser(name)

        client.enqueue(object: Callback<ResponseSearchUser>{
            override fun onResponse(
                call: Call<ResponseSearchUser>,
                response: Response<ResponseSearchUser>
            ) {
                if(response.isSuccessful){
                    if(response.body()?.totalCount == 0){
                        _errorMessage.postValue(Event("No Data Found"))
                    }else{
                        _listUser.postValue(response.body()?.items as ArrayList<ResponseListUserItem>?)
                    }
                    _isLoading.postValue(false)

                }
            }

            override fun onFailure(call: Call<ResponseSearchUser>, error: Throwable) {
                _isLoading.postValue(false)
            }

        })
    }
}