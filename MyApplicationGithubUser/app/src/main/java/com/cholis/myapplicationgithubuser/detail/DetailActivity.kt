package com.cholis.myapplicationgithubuser.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cholis.myapplicationgithubuser.AdapterPager
import com.cholis.myapplicationgithubuser.R
import com.cholis.myapplicationgithubuser.databinding.ActivityDetailBinding
import com.cholis.myapplicationgithubuser.favorite.FavoriteVM
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val favoriteVM: FavoriteVM by viewModels()

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var username: String
    private lateinit var avatar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur judul ActionBar
        supportActionBar?.title = "Detail"

        // Mendapatkan data dari intent
        username = intent.getStringExtra(EXTRA_USERNAME) ?: ""
        avatar = intent.getStringExtra(EXTRA_AVATAR) ?: ""
        val id = intent.getIntExtra(EXTRA_ID, 0)

        // Menginisialisasi TabLayout dan ViewPager2
        tabLayout = binding.tabs
        viewPager = binding.viewPager

        // Membuat bundle untuk fragmen followers dan following
        val bundleFollowers = Bundle()
        bundleFollowers.putString(EXTRA_USERNAME, username)
        bundleFollowers.putBoolean(EXTRA_FOLLOWERS, true)

        val bundleFollowing = Bundle()
        bundleFollowing.putString(EXTRA_USERNAME, username)
        bundleFollowing.putBoolean(EXTRA_FOLLOWERS, false)

        // Menginisialisasi AdapterPager dengan TabLayout dan ViewPager2
        val adapterPager = AdapterPager(
            this,
            this,
            tabLayout,
            viewPager,
            bundleFollowers,
            bundleFollowing
        )

        // Menambahkan tab pada TabLayout
        adapterPager.setTabs()

        // Menghandle klik pada tombol favorit
        binding.btnFav.setOnClickListener {
            checkAndToggleFavorite(id)
        }

        // Menampilkan detail pengguna dan memeriksa status favorit
        setView()
        setFavIcon(id)
    }

    // Fungsi untuk menampilkan detail pengguna
    private fun setView() {
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        // Menampilkan loading spinner
        showLoading(true)

        if (username.isNotEmpty()) {
            // Memanggil fungsi untuk mengambil detail pengguna
            viewModel.fetchDetailUser(username)
            viewModel.getDetailUser().observe(this) {
                if (it != null) {
                    // Memuat gambar avatar menggunakan Glide
                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(binding.ivDetailUser)
                    showLoading(false)

                    // Mengatur teks pada tampilan
                    binding.tvDetailName.text = it.name
                    binding.tvDetailUsername.text = it.login
                    binding.tvJmlFollowers.text = it.followers.toString()
                    binding.tvJmlFollowing.text = it.following.toString()
                }
            }
        } else {
            // Menampilkan pesan jika username tidak ditemukan dan menutup aktivitas
            Toast.makeText(this, "Username tidak ditemukan.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // Fungsi untuk mengatur ikon favorit berdasarkan status favorit pengguna
    private fun setFavIcon(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteVM.check(id)
            withContext(Dispatchers.Main) {
                favoriteIcon(count > 0)
            }
        }
    }

    // Fungsi untuk memeriksa dan mengganti status favorit pengguna
    private fun checkAndToggleFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val count = favoriteVM.check(id)
            withContext(Dispatchers.Main) {
                if (count > 0) {
                    favoriteIcon(false)
                    favoriteVM.deleteFavorite(id)
                } else {
                    favoriteIcon(true)
                    favoriteVM.saveFavorite(username, avatar, id)
                }
            }
        }
    }

    // Fungsi untuk mengubah ikon favorit
    private fun favoriteIcon(isFavorite: Boolean) {
        val drawableResId = if (isFavorite) {
            R.drawable.ic_favorite
        } else {
            R.drawable.ic_favorite2
        }

        // Mengatur latar belakang ToggleButton dengan gambar sesuai status favorit
        binding.btnFav.setBackgroundResource(drawableResId)
    }

    // Fungsi untuk menampilkan atau menyembunyikan loading spinner
    fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    // Konstanta untuk mengirimkan data antar aktivitas
    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_FOLLOWERS = "extra_followers"
    }
}
