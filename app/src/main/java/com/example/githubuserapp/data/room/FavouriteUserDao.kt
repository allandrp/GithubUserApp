package com.example.githubuserapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.githubuserapp.data.model.FavouriteUser


@Dao
interface FavouriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: FavouriteUser): Long

    @Update
    suspend fun update(user: FavouriteUser)

    @Delete
    suspend fun delete(user: FavouriteUser): Int

    @Query("SELECT * FROM FavouriteUser")
    fun getAllData(): LiveData<List<FavouriteUser>>

    @Query("SELECT EXISTS(SELECT * FROM FavouriteUser WHERE username = :username)")
    fun isUserFavourite(username: String): LiveData<Boolean>

}