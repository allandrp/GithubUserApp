package com.example.githubuserapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuserapp.data.repository.UserRepository
import com.example.githubuserapp.ui.detail.DetailViewModel
import com.example.githubuserapp.ui.favourites.FavouriteViewModel
import com.example.githubuserapp.ui.followers.FollowersViewModel
import com.example.githubuserapp.ui.home.HomeViewModel
import com.example.githubuserapp.utils.injection.Injection

class ViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(FollowersViewModel::class.java) -> {
                FollowersViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(FavouriteViewModel::class.java) -> {
                FavouriteViewModel(userRepository) as T
            }

            else -> {
                throw IllegalArgumentException("Unknown ViewModel Class: "+ modelClass.name)
            }
        }
    }

    companion object{
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory{
            return instance ?: synchronized(this){
                instance ?: ViewModelFactory(Injection.provideUserRepository(context))
            }.also {
                instance = it
            }
        }
    }


}