package com.cholis.myapplicationgithubuser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.cholis.myapplicationgithubuser.databinding.UserItemBinding
import com.cholis.myapplicationgithubuser.response.Item

class Adapter : RecyclerView.Adapter<Adapter.UserGithubViewHolder>() {
    private var userList = mutableListOf<Item>()
    private var onItemClickCallback: OnItemClickCallback? = null

    // Membuat tampilan item dalam RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGithubViewHolder {
        val view = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserGithubViewHolder(view)
    }

    // Menghubungkan data dengan tampilan item.
    override fun onBindViewHolder(holder: UserGithubViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    // Mengembalikan jumlah item dalam daftar.
    override fun getItemCount(): Int = userList.size

    inner class UserGithubViewHolder(private val binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {

        // Mengikat data pengguna ke tampilan item.
        fun bind(user: Item) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUserList)
                tvUsernameList.text = user.login
            }
        }
    }

    // Mengatur callback ketika item diklik.
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    // Mengatur daftar pengguna yang akan ditampilkan dalam RecyclerView.
    @SuppressLint("NotifyDataSetChanged")
    fun setUserList(users: List<Item>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    // Interface untuk menangani klik item.
    interface OnItemClickCallback {
        fun onItemClicked(data: Item)
    }
}
