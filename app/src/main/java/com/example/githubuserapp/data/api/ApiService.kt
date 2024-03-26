package com.example.githubuserapp.data.api

import com.example.githubuserapp.data.response.ResponseFollowers
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.data.response.ResponseListUser
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.data.response.ResponseSearchUser
import com.example.githubuserapp.data.response.ResponseUserDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    fun getListUser():Call<ArrayList<ResponseListUserItem>>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<ResponseUserDetail>

    @GET("search/users")
    fun searchUser(
        @Query("q") username: String
    ): Call <ResponseSearchUser>

    @GET("users/{username}/{pageName}")
    fun getFollowers(
        @Path("username") username: String,
        @Path("pageName") pageName: String,
    ): Call <ArrayList<ResponseFollowersItem>>

}