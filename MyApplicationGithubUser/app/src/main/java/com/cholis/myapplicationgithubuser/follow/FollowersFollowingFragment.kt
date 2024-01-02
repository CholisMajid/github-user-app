package com.cholis.myapplicationgithubuser.follow

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cholis.myapplicationgithubuser.Adapter
import com.cholis.myapplicationgithubuser.databinding.FragmentFollowersFollowingBinding
import com.cholis.myapplicationgithubuser.response.Item

class FollowersFollowingFragment : Fragment() {
    private var username: String? = null
    private var isFollowers: Boolean = false
    private lateinit var followersAdapter: Adapter
    private lateinit var followingAdapter: Adapter
    private var _binding: FragmentFollowersFollowingBinding? = null
    private val binding get() = _binding

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersFollowingBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(EXTRA_USERNAME)
        isFollowers = arguments?.getBoolean(EXTRA_FOLLOWERS, false) ?: false


        // Inisialisasi adapter untuk followers
        followersAdapter = Adapter()
        // Inisialisasi adapter untuk following
        followingAdapter = Adapter()

        binding?.rvUserFollow?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = followersAdapter
        }

        val followViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowViewModel::class.java]

        followViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (isFollowers) {
            followViewModel.getFollowers(username.toString())
            followViewModel.listFollowers.observe(viewLifecycleOwner) { result ->
                setFollowerUser(result)
            }
        } else {
            followViewModel.getFollowing(username.toString())
            followViewModel.listFollowing.observe(viewLifecycleOwner) { result ->
                setFollowingUser(result)
            }
        }

    }

    private fun setFollowingUser(result: List<Item>?) {
        if (result != null) {
            followingAdapter.setUserList(result)
            binding?.rvUserFollow?.adapter = followingAdapter
        }
    }

    private fun setFollowerUser(result: List<Item>?) {
        if (result != null) {
            followersAdapter.setUserList(result)
            binding?.rvUserFollow?.adapter = followersAdapter
        }
    }

    fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_FOLLOWERS = "extra_followers"
    }
}



