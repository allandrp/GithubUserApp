package com.example.githubuserapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.example.githubuserapp.data.api.ApiService
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.data.response.ResponseUserDetail
import com.example.githubuserapp.data.room.FavouriteUserDao
import com.example.githubuserapp.utils.Result


class UserRepository(private val apiService: ApiService, private val favouritesDao: FavouriteUserDao) {

    /**
     * A group of *members*.
     *
     * Function for get list of user for the headline home
     *
     * @return livedata of headline user in ArrayList
     */
    fun getListUser(): LiveData<Result<ArrayList<ResponseListUserItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getListUser()
            when {
                response.isEmpty() -> {
                    emit(Result.Error("Data Not Found"))
                }

                else -> {
                    emit(Result.Success(response))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun searchUser(username: String): LiveData<Result<ArrayList<ResponseListUserItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.searchUser(username)
            val listSearchResult = response.items
            when {
                response.totalCount == 0 -> {
                    emit(Result.Error("Data Not Found"))
                }

                else -> {
                    emit(Result.Success(listSearchResult as ArrayList<ResponseListUserItem>))
                }
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun detailUser(username: String): LiveData<Result<ResponseUserDetail>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailUser(username)
            emit(Result.Success(response))

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFollowers(
        username: String,
        pageName: String
    ): LiveData<Result<ArrayList<ResponseFollowersItem>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFollowers(username, pageName)

            if (response.isEmpty()) {
                emit(Result.Error("$pageName Not Found"))
            } else {
                emit(Result.Success(response))
            }

        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavouritesUser(): LiveData<ArrayList<FavouriteUser>>{
        return favouritesDao.getAllData().map {
            ArrayList(it)
        }
    }

    suspend fun setFavouriteUser(user: FavouriteUser): Long{
        return favouritesDao.insert(user)
    }

    suspend fun deleteFavouriteUser(user: FavouriteUser): Int{
        return favouritesDao.delete(user)
    }

    fun isUserFavourite(username: String) = favouritesDao.isUserFavourite(username)



    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null

        fun initRepository(userApiService: ApiService, favouritesDao: FavouriteUserDao): UserRepository {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = UserRepository(userApiService, favouritesDao)
                }
            }

            return INSTANCE as UserRepository
        }
    }
}