package com.example.githubuserapp.ui.favourites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.databinding.ActivityFavouritesBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.detail.DetailActivity

class FavouritesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavouritesBinding
    private lateinit var adapterFavourite: AdapterFavourite
    private val favouriteViewModel by viewModels <FavouriteViewModel>{
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavouritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Favourite"

        favouriteViewModel.getFavouriteUser().observe(this){ result ->
            if(result.isNotEmpty()){
                binding.favourite404.visibility = View.GONE
            }else{
                binding.favourite404.visibility = View.VISIBLE
            }
            setRecyclerview(result)
        }

    }

    fun setRecyclerview(listData: ArrayList<FavouriteUser>) {
        adapterFavourite = AdapterFavourite(listData){ item ->
            val intentToDetail = Intent(this, DetailActivity::class.java)
            intentToDetail.putExtra("username", item.username)
            intentToDetail.putExtra("avatarUrl", item.avatarUrl)
            startActivity(intentToDetail)
        }

        val layoutManager = LinearLayoutManager(this)
        with(binding) {
            favouriteRv.layoutManager = layoutManager
            favouriteRv.adapter = adapterFavourite
            favouriteRv.addItemDecoration(
                DividerItemDecoration(
                    this@FavouritesActivity,
                    layoutManager.orientation
                )
            )
            adapterFavourite.notifyDataSetChanged()
        }
    }
}