package com.example.githubuserapp.ui.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.databinding.ItemGithubUserBinding

class AdapterFavourite(
    private val listFavourite: ArrayList<FavouriteUser>,
    private val onClick: (FavouriteUser) -> (Unit)
) :
    RecyclerView.Adapter<AdapterFavourite.FavouriteViewHolder>() {
    class FavouriteViewHolder(val binding: ItemGithubUserBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = ItemGithubUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFavourite.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favouriteItem = listFavourite[position]

        with(holder.binding) {
            usernameTextview.text = favouriteItem.username
            Glide.with(holder.itemView.context).load(favouriteItem.avatarUrl)
                .placeholder(R.drawable.avatar_placeholder).circleCrop().into(usernameProfilePic)

            root.setOnClickListener{
                onClick(favouriteItem)
            }
        }
    }

}