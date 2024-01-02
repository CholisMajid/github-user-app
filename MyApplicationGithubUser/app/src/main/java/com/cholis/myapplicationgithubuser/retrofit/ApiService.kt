package com.cholis.myapplicationgithubuser.retrofit

import com.cholis.myapplicationgithubuser.response.DetailUserResponse
import com.cholis.myapplicationgithubuser.response.Item
import com.cholis.myapplicationgithubuser.response.UsersGithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String
    ): Call<UsersGithubResponse>

    @GET("users/{username}")
    fun getDetailuser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<List<Item>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<List<Item>>
}