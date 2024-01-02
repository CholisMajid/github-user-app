package com.cholis.myapplicationgithubuser.response

import androidx.lifecycle.LiveData
import com.cholis.myapplicationgithubuser.room.UserGithubDao
import com.cholis.myapplicationgithubuser.room.UserGithubEntity

class UserRepo(
    private val userDao: UserGithubDao,
) {
    fun getFavoriteUser(): LiveData<List<UserGithubEntity>>{
        return userDao.getAllFavorite()
    }
    fun setFavorite(username: String, avatar : String, id: Int, favoritesState: Boolean){
        val user = UserGithubEntity(id, username, avatar, favoritesState)
        userDao.insert(user)
    }
    fun deleteFavorite(id : Int){
        userDao.delete(id)
    }
    fun check(id: Int): Int {
        return userDao.check(id)
    }
    companion object {
        var username = "KEY_DATA"
    }
}