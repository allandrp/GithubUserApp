package com.example.githubuserapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.databinding.ItemGithubUserBinding

class AdapterListUser(private val listUser: ArrayList<ResponseListUserItem>) :
    RecyclerView.Adapter<AdapterListUser.ListUserVH>() {

    class ListUserVH(val binding: ItemGithubUserBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserVH {
        val binding =
            ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUserVH(binding)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    override fun onBindViewHolder(holder: ListUserVH, position: Int) {

        val user = listUser[position]

        with(holder) {
            binding.usernameTextview.text = user.login
            Glide.with(itemView.context).load(user.avatarUrl)
                .placeholder(R.drawable.avatar_placeholder).circleCrop()
                .into(binding.usernameProfilePic)
        }
    }
}