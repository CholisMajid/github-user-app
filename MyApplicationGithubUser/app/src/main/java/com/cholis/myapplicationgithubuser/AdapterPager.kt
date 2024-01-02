package com.cholis.myapplicationgithubuser

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.cholis.myapplicationgithubuser.follow.FollowersFollowingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AdapterPager(
    private val mCtx: Context,
    fragmentActivity: FragmentActivity,
    private val tabLayout: TabLayout,
    private val viewPager: ViewPager2,
    private vararg val fragments: Bundle
) : FragmentStateAdapter(fragmentActivity) {

    @StringRes
    val title = intArrayOf(R.string.followersUser, R.string.followingUser)

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        val followersFollowingFragment = FollowersFollowingFragment()
        followersFollowingFragment.arguments = fragments[position]
        return followersFollowingFragment
    }

    fun setTabs() {
        viewPager.adapter = this
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = mCtx.resources.getString(title[position])
        }.attach()
    }
}

