package com.cholis.myapplicationgithubuser.response

data class UsersGithubResponse(
    val incomplete_results: Boolean,
    val items: ArrayList<Item>,
    val total_count: Int
)