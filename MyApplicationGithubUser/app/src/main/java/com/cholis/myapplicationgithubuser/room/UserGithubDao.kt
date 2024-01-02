package com.cholis.myapplicationgithubuser.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserGithubDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: UserGithubEntity)

    @Query("DELETE from fav_user where fav_user.id = :id")
    fun delete(id: Int): Int

    @Query("SELECT count(*) from fav_user where fav_user.id = :id")
    fun check(id: Int): Int

    @Query("SELECT * from fav_user where isFavorite = 1")
    fun getAllFavorite(): LiveData<List<UserGithubEntity>>

    @Query("SELECT EXISTS(SELECT * FROM fav_user WHERE username = :username)")
    fun isFavorite(username: String): Boolean
}