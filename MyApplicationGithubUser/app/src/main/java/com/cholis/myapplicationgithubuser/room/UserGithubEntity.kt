package com.cholis.myapplicationgithubuser.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "fav_user")
@Parcelize
data class UserGithubEntity(

    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "username")
    val username: String?,

    @ColumnInfo(name = "avatar_url")
    val avatar_url: String?,

    @field:ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean,

    ) : Parcelable

