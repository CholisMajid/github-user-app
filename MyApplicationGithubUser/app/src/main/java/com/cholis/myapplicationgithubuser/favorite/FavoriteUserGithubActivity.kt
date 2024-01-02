package com.cholis.myapplicationgithubuser.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.cholis.myapplicationgithubuser.databinding.ActivityFavoriteUserGithubBinding
import com.cholis.myapplicationgithubuser.detail.DetailActivity
import com.cholis.myapplicationgithubuser.room.UserGithubEntity

class FavoriteUserGithubActivity : AppCompatActivity() {
    private var _binding: ActivityFavoriteUserGithubBinding? = null
    private val binding get() = _binding
    private val favoriteViewModel: FavoriteVM by viewModels()
    private lateinit var adapter: AdapterFavorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteUserGithubBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Favorite"

        adapter = AdapterFavorite(object : AdapterFavorite.OnItemClickCallback {
            override fun onItemClicked(user: UserGithubEntity) {
                val intent = Intent(this@FavoriteUserGithubActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                intent.putExtra(DetailActivity.EXTRA_ID, user.id)
                intent.putExtra(DetailActivity.EXTRA_AVATAR, user.avatar_url)
                startActivity(intent)
            }
        })

        binding?.rvUser?.layoutManager = LinearLayoutManager(this)
        binding?.rvUser?.adapter = adapter
        favoriteViewModel.getFavoriteUsers().observe(this) { users ->
            if (!users.isNullOrEmpty()) {
                binding?.rvUser?.visibility = View.VISIBLE
                adapter.submitList(users)
            } else {
                binding?.rvUser?.visibility = View.INVISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showDataFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showDataFavorite() {
        val rvUser = binding?.rvUser
        val tvNoData = binding?.tvNoData
        favoriteViewModel.getFavoriteUsers().observe(this) { users ->
            if (users.isNullOrEmpty()) {
                tvNoData?.visibility = View.VISIBLE
                rvUser?.visibility = View.INVISIBLE
            } else {
                rvUser?.visibility = View.VISIBLE
                adapter.submitList(users)
                tvNoData?.visibility = View.GONE
            }
        }
    }
}
