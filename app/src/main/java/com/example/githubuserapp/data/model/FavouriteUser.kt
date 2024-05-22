package com.example.githubuserapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class FavouriteUser(

    @PrimaryKey
    @ColumnInfo
    var username: String,

    @ColumnInfo
    var avatarUrl: String

): Parcelable
