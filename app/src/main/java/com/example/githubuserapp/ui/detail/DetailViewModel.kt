package com.example.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.data.repository.UserRepository
import com.example.githubuserapp.data.response.ResponseUserDetail
import com.example.githubuserapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DetailViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDetailUser(username: String):  LiveData<Result<ResponseUserDetail>> = userRepository.detailUser(username)
    fun isUserFavourite(username: String): LiveData<Boolean> = userRepository.isUserFavourite(username)
    suspend fun setUserFavourite(user: FavouriteUser): Long{
        return withContext(Dispatchers.IO) {
            userRepository.setFavouriteUser(user)
        }
    }

    suspend fun deleteUserFavourite(user: FavouriteUser): Int{
        return withContext(Dispatchers.IO) {
            userRepository.deleteFavouriteUser(user)
        }
    }

}