package com.cholis.myapplicationgithubuser.setting

import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.cholis.myapplicationgithubuser.R

class ThemeSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_setting)

        supportActionBar?.title = "Setting"

        // Inisialisasi switch dan simpanan tema
        val switchDarkTheme = findViewById<Switch>(R.id.switch_dark_theme)
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Set status switch berdasarkan tema yang ada
        val isDarkThemeEnabled = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        switchDarkTheme.isChecked = isDarkThemeEnabled

        // Tambahkan listener untuk switch
        switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            // Simpan preferensi tema
            preferences.edit().putBoolean("theme_switch", isChecked).apply()
        }
    }
}
