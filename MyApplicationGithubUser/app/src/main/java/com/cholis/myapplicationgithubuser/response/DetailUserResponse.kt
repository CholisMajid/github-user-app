package com.cholis.myapplicationgithubuser.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(
    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("followers")
    val followers: Int,

    @field:SerializedName("following")
    val following: Int,

    @field:SerializedName("login")
    val login: String,

    @field:SerializedName("name")
    val name: String,
)