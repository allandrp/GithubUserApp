package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.databinding.FragmentDetailBinding
import com.example.githubuserapp.ui.viewmodel.DetailViewModel
import com.example.githubuserapp.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val usernamePass = DetailFragmentArgs.fromBundle(arguments as Bundle).username

        val viewPager = binding.viewPagerFollower
        val tabLayout = binding.tabLayoutFollowers
        val pagerAdapter = FollowersPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(TAB_TITLES[position])
        }.attach()

        with(detailViewModel) {
            getDetailUser(usernamePass)

            detailUser.observe(requireActivity()) { data ->
                binding.holderUsername.text = data.login
                Glide.with(requireActivity()).load(data.avatarUrl)
                    .placeholder(R.drawable.avatar_placeholder).circleCrop()
                    .into(binding.holderProfilePicture)

                binding.holderFollowers.text = data.followers.toString()
                binding.holderFollowing.text = data.following.toString()
                binding.holderRepos.text = data.publicRepos.toString()
            }

            isLoading.observe(requireActivity()) { isLoading ->
                Utils.isLoading(binding.loadingBarDetail, isLoading)
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