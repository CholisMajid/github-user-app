package com.cholis.myapplicationgithubuser.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.cholis.myapplicationgithubuser.response.UserRepo
import com.cholis.myapplicationgithubuser.room.UserGithubEntity
import com.cholis.myapplicationgithubuser.room.UserGithubRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteVM(application: Application) : AndroidViewModel(application) {
    private val userRepo: UserRepo
    init {
        val userDb = UserGithubRoomDB.getDatabase(application)
        userRepo = UserRepo(userDb.userGithubDao())
    }
    suspend fun check(id: Int): Int {
        return withContext(Dispatchers.IO) {
            userRepo.check(id)
        }
    }
    suspend fun saveFavorite(username: String, avatar: String, id: Int) {
        withContext(Dispatchers.IO) {
            userRepo.setFavorite(username, avatar, id, true)
        }
    }
    suspend fun deleteFavorite(id: Int) {
        withContext(Dispatchers.IO) {
            userRepo.deleteFavorite(id)
        }
    }
    fun getFavoriteUsers(): LiveData<List<UserGithubEntity>> {
        return userRepo.getFavoriteUser()
    }
}