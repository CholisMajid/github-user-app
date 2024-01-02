package com.cholis.myapplicationgithubuser.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cholis.myapplicationgithubuser.databinding.UserItemBinding
import com.cholis.myapplicationgithubuser.room.UserGithubEntity

class AdapterFavorite(private val onItemClickCallback: OnItemClickCallback) :
    ListAdapter<UserGithubEntity, AdapterFavorite.FavoriteViewHolder>(UserEntityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    inner class FavoriteViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val user = getItem(position)
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
        fun bind(user: UserGithubEntity) {
            binding.apply {
                tvUsernameList.text = user.username
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUserList)
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserGithubEntity)
    }

    private class UserEntityDiffCallback : DiffUtil.ItemCallback<UserGithubEntity>() {
        override fun areItemsTheSame(oldItem: UserGithubEntity, newItem: UserGithubEntity): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: UserGithubEntity, newItem: UserGithubEntity): Boolean {
            return oldItem == newItem
        }
    }
}