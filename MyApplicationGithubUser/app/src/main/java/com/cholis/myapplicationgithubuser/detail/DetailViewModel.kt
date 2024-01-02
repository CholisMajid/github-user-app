package com.cholis.myapplicationgithubuser.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log
import com.cholis.myapplicationgithubuser.response.DetailUserResponse
import com.cholis.myapplicationgithubuser.retrofit.Api

class DetailViewModel : ViewModel() {
    private val _client = MutableLiveData<DetailUserResponse>()
    val client: LiveData<DetailUserResponse> get() = _client

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchDetailUser(username: String) {
        _isLoading.value = true
        val q = Api.create().getDetailuser(username)
        q.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(call: Call<DetailUserResponse>, response: Response<DetailUserResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _client.value = response.body()
                } else {
                    _errorMessage.value = "Gagal mengambil data pengguna."
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                _errorMessage.value = t.message ?: "Terjadi kesalahan yang tidak diketahui."
                Log.d("Failure", _errorMessage.value ?: "Terjadi kesalahan yang tidak diketahui.")
            }
        })
    }

    fun getDetailUser(): LiveData<DetailUserResponse> {
        return client
    }

}
