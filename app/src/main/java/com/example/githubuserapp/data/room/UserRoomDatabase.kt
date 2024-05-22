package com.example.githubuserapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubuserapp.data.model.FavouriteUser

@Database([FavouriteUser::class], version = 1)
abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouriteUserDao


    companion object {

        @Volatile
        private var instance: UserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UserRoomDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserRoomDatabase::class.java,
                        "user_github"
                    ).build()
                }
            }

            return instance as UserRoomDatabase
        }
    }
}