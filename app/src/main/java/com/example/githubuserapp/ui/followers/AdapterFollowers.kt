package com.example.githubuserapp.ui.followers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.databinding.ItemGithubUserBinding

class AdapterFollowers(
    private val listFollower: ArrayList<ResponseFollowersItem>,
    private val onClick: (ResponseFollowersItem) -> (Unit)
) :
    RecyclerView.Adapter<AdapterFollowers.FollowerViewHolder>() {
    class FollowerViewHolder(val binding: ItemGithubUserBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowerViewHolder {
        val binding =
            ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowerViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }

    override fun onBindViewHolder(holder: FollowerViewHolder, position: Int) {
        val user = listFollower[position]

        with(holder) {
            binding.usernameTextview.text = user.login
            Glide.with(binding.root.context).load(user.avatarUrl).circleCrop()
                .placeholder(R.drawable.avatar_placeholder).into(binding.usernameProfilePic)

            itemView.setOnClickListener{
                onClick(user)
            }
        }
    }
}