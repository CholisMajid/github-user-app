package com.cholis.myapplicationgithubuser.follow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cholis.myapplicationgithubuser.response.Item
import com.cholis.myapplicationgithubuser.retrofit.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel: ViewModel() {
    private val _listFollowers = MutableLiveData<List<Item>>()
    val listFollowers: LiveData<List<Item>> = _listFollowers

    private val _listFollowing = MutableLiveData<List<Item>>()
    val listFollowing: LiveData<List<Item>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String) {
        _isLoading.value = true
        val client = Api.create().getUserFollowers(username)
        client.enqueue(object : Callback<List<Item>> {
            override fun onResponse(
                call: Call<List<Item>>,
                response: Response<List<Item>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowers.value = response.body()
                } else {
                    Log.d(TAG, "error : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "on Failure : ${t.message}")
            }

        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true
        val client = Api.create().getUserFollowing(username)
        client.enqueue(object : Callback<List<Item>> {
            override fun onResponse(
                call: Call<List<Item>>,
                response: Response<List<Item>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FollowViewModel"
    }
}