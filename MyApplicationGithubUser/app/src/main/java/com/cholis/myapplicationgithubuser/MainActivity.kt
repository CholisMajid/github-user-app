package com.cholis.myapplicationgithubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cholis.myapplicationgithubuser.databinding.ActivityMainBinding
import com.cholis.myapplicationgithubuser.detail.DetailActivity
import com.cholis.myapplicationgithubuser.favorite.FavoriteUserGithubActivity
import com.cholis.myapplicationgithubuser.response.Item
import com.cholis.myapplicationgithubuser.setting.ThemeSettingActivity


class MainActivity : AppCompatActivity() {
    private var bind: ActivityMainBinding? = null
    private val binding get() = bind!!

    private lateinit var viewModel: UserViewModel

    private lateinit var connector: Adapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Github User App"
        applyTheme()

        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        connector = Adapter()

        connector.setOnItemClickCallback(object : Adapter.OnItemClickCallback {
            override fun onItemClicked(data: Item) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatar_url)
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    startActivity(it)
                }
            }
        })

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = this@MainActivity.connector
        }

        // Implementasi SearchView
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Handle pencarian berdasarkan perubahan teks
                if (!newText.isNullOrEmpty()) {
                    showLoading(true)
                    viewModel.fetchUserData(newText)
                } else {
                    // Jika teks kosong, tampilkan semua pengguna
                    showLoading(true)
                    viewModel.fetchUserData("cholis")
                }
                return true
            }
        })

        // Setting the adapter's setList and disabling the progress bar.
        viewModel.searchUser().observe(this) {
            if (it != null) {
                connector.setUserList(it)
                showLoading(false)
            }
        }
        showLoading(true)

        // Setting the search user with the result named "cholis," which is my personal name.
        viewModel.fetchUserData("cholis")

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        preferences.registerOnSharedPreferenceChangeListener { _, key ->
            if (key == "theme_switch") {
                applyTheme()
            }
        }
    }

    private fun applyTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkThemeEnabled = preferences.getBoolean("theme_switch", false)

        if (isDarkThemeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    override fun onDestroy() {
        bind = null
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // Setting event when the menu is clicked and defining actions to navigate to selected activities.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                val intent = Intent(this, FavoriteUserGithubActivity::class.java)
                startActivity(intent)
                true
            }
              R.id.action_settings -> {
                val intent = Intent(this, ThemeSettingActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoading(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}
