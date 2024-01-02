package com.cholis.myapplicationgithubuser.response

import com.google.gson.annotations.SerializedName

data class Item(
    @field:SerializedName("avatar_url")
    val avatar_url: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("login")
    val login: String,
)