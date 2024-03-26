package com.example.githubuserapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.ResponseFollowers
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.ui.viewmodel.FollowersViewModel
import com.example.githubuserapp.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapterFollower: AdapterFollowers
    private val followersViewModel by viewModels<FollowersViewModel>()
    var page: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = requireParentFragment().arguments?.getString("username")

        if (page != null) {
            followersViewModel.getFollowers(username.toString(), page.toString())
            followersViewModel.listUser.observe(requireActivity()) { data ->
                setRecyclerview(data)
            }

            followersViewModel.errorMessage.observe(requireActivity()){ error ->
                Toast.makeText(requireActivity(), error.getContentIfNotHandled(), Toast.LENGTH_SHORT).show()
            }

            followersViewModel.isLoading.observe(requireActivity()){ isLoading ->
                Utils.isLoading(binding.loadingBarFollowers, isLoading)
            }

        }else{
            Toast.makeText(requireContext(), "GAGAL", Toast.LENGTH_SHORT).show()
        }
    }

    fun setRecyclerview(listData: ArrayList<ResponseFollowersItem>) {
        adapterFollower = AdapterFollowers(listData)

        val layoutManager = LinearLayoutManager(requireActivity())
        with(binding) {
            recyclerViewFollowers.layoutManager = layoutManager
            recyclerViewFollowers.adapter = adapterFollower
            recyclerViewFollowers.addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    layoutManager.orientation
                )
            )
            adapterFollower.notifyDataSetChanged()
        }
    }
}