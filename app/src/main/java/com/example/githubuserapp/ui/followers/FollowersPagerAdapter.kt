package com.example.githubuserapp.ui.followers

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FollowersPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> {
                fragment = FollowersFragment()
                fragment.page = "followers"
            }

            1 -> {
                fragment = FollowersFragment()
                fragment.page = "following"
            }
        }

        return fragment as Fragment
    }
}