package com.example.githubuserapp.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.model.FavouriteUser
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.followers.FollowersPagerAdapter
import com.example.githubuserapp.utils.Result
import com.example.githubuserapp.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var isFavouriteUser = false

    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(this@DetailActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usernamePass = intent.getStringExtra("username").toString()
        val avatarUrlPass = intent.getStringExtra("avatarUrl").toString()

        supportActionBar?.title = usernamePass

        val favouriteFab = binding.favouriteButton
        val viewPager = binding.viewPagerFollower
        val tabLayout = binding.tabLayoutFollowers
        val pagerAdapter = FollowersPagerAdapter(this)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        with(detailViewModel) {
            getDetailUser(usernamePass)

            getDetailUser(usernamePass).observe(this@DetailActivity) { result ->
                when (result) {
                    is Result.Loading -> {
                        Utils.isLoading(binding.loadingBarDetail, true)
                    }

                    is Result.Success -> {
                        val detaiUser = result.data
                        Utils.isLoading(binding.loadingBarDetail, false)
                        binding.holderUsername.text = detaiUser.login
                        Glide.with(this@DetailActivity).load(detaiUser.avatarUrl)
                            .placeholder(R.drawable.avatar_placeholder).circleCrop()
                            .into(binding.holderProfilePicture)

                        binding.holderFollowers.text = detaiUser.followers.toString()
                        binding.holderFollowing.text = detaiUser.following.toString()
                        binding.holderRepos.text = detaiUser.publicRepos.toString()
                    }

                    is Result.Error -> {
                        Utils.isLoading(binding.loadingBarDetail, false)
                    }
                }


            }

            isUserFavourite(usernamePass).observe(this@DetailActivity) { favourite ->
                if (favourite) {
                    favouriteFab.setImageResource(R.drawable.baseline_favorite_75)
                    isFavouriteUser = true
                } else {
                    favouriteFab.setImageResource(R.drawable.baseline_favorite_border_75)
                    isFavouriteUser = false
                }
            }

        }

        favouriteFab.setOnClickListener {
            lifecycleScope.launch {
                if (isFavouriteUser) {
                    val result = detailViewModel.deleteUserFavourite(
                        FavouriteUser(
                            username = usernamePass,
                            avatarUrl = avatarUrlPass
                        )
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailActivity,
                            "Deleted From Favourite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DetailActivity,
                            "Cannot Remove User From Favourite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    val result = detailViewModel.setUserFavourite(
                        FavouriteUser(
                            username = usernamePass,
                            avatarUrl = avatarUrlPass
                        )
                    )
                    if (result > 0) {
                        Toast.makeText(
                            this@DetailActivity,
                            "User Added To Favourite",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DetailActivity,
                            "Error Adding To Favourite",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

}