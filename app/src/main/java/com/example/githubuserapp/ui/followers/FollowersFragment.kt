package com.example.githubuserapp.ui.followers

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.ResponseFollowersItem
import com.example.githubuserapp.databinding.FragmentFollowersBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.detail.DetailActivity
import com.example.githubuserapp.utils.Result
import com.example.githubuserapp.utils.Utils

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var adapterFollower: AdapterFollowers
    private val followersViewModel by viewModels<FollowersViewModel>{
        ViewModelFactory.getInstance(requireActivity())
    }
    var page: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = activity?.intent?.getStringExtra("username")

        if (page != null) {
            val progressBar = binding.loadingBarFollowers
            followersViewModel.getFollowers(username.toString(), page.toString())
                .observe(requireActivity()) { result ->

                    when (result) {
                        is Result.Loading -> {
                            Utils.isLoading(progressBar, true)
                        }

                        is Result.Success -> {
                            Utils.isLoading(progressBar, false)
                            setRecyclerview(result.data)
                        }

                        is Result.Error -> {
                            Utils.isLoading(progressBar, false)
                            Toast.makeText(requireActivity(), result.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                            Utils.isLoading(progressBar, false)
                        }

                    }

                }
        }else{
            Toast.makeText(requireContext(), "Failed To Load", Toast.LENGTH_SHORT).show()
        }
    }

    fun setRecyclerview(listData: ArrayList<ResponseFollowersItem>) {
        adapterFollower = AdapterFollowers(listData){ item ->
            val intentToDetail = Intent(requireContext(), DetailActivity::class.java)
            intentToDetail.putExtra("username", item.login)
            intentToDetail.putExtra("avatarUrl", item.avatarUrl)
            startActivity(intentToDetail)
        }

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