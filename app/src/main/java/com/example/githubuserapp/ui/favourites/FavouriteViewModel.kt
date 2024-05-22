package com.example.githubuserapp.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.data.repository.UserRepository

class FavouriteViewModel(private val userRepository: UserRepository): ViewModel()  {
    fun getFavouriteUser():  LiveData<ArrayList<FavouriteUser>> = userRepository.getFavouritesUser()
}