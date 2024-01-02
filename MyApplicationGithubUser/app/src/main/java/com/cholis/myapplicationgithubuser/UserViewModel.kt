package com.cholis.myapplicationgithubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cholis.myapplicationgithubuser.response.Item
import com.cholis.myapplicationgithubuser.response.UsersGithubResponse
import com.cholis.myapplicationgithubuser.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val userList = MutableLiveData<ArrayList<Item>>()

    fun fetchUserData(query: String) {
        val q = Api.create().searchUsers(query)

        q.enqueue(object : Callback<UsersGithubResponse> {
            override fun onResponse(call: Call<UsersGithubResponse>, response: Response<UsersGithubResponse>) {
                if (response.isSuccessful) {
                    userList.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UsersGithubResponse>, t: Throwable) {
                Log.d("UserViewModel", t.message ?: "Unknown error occurred")
            }
        })
    }

    fun searchUser(): LiveData<ArrayList<Item>> {
        return userList
    }
}
