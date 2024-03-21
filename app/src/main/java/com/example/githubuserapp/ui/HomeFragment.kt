package com.example.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.databinding.FragmentHomeBinding
import com.example.githubuserapp.ui.viewmodel.HomeViewModel
import com.example.githubuserapp.utils.Utils
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapterListUser: AdapterListUser
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            mainSearchView.setupWithSearchBar(mainSearchBar)

            mainSearchView.editText.setOnEditorActionListener { textview, _, _ ->

                mainSearchBar.setText(textview.text)
                if(textview.text.isEmpty()){
                    homeViewModel.getListUser()
                }else{
                    homeViewModel.searchUser(textview.text.toString())
                }

                mainSearchView.hide()
                true
            }

            requireActivity().onBackPressedDispatcher.addCallback(
                viewLifecycleOwner,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (mainSearchView.isShowing) {
                            mainSearchView.hide()
                        } else {
                            requireActivity().onBackPressed()
                        }
                    }
                })

        }

        homeViewModel.errorMessage.observe(requireActivity()) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    binding.root,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }


        homeViewModel.listUser.observe(requireActivity()) { response ->
            setRecyclerview(response)
        }

        homeViewModel.isLoading.observe(requireActivity()){ loading ->
            val progressBar = binding.loadingBarMain
            Utils.isLoading(progressBar, loading)
        }

    }

    fun setRecyclerview(listData: ArrayList<ResponseListUserItem>) {
        adapterListUser = AdapterListUser(listData)
        val layoutManager = LinearLayoutManager(requireActivity())
        with(binding) {
            mainRecyclerView.layoutManager = layoutManager
            mainRecyclerView.adapter = adapterListUser
            mainRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    layoutManager.orientation
                )
            )
            adapterListUser.notifyDataSetChanged()
        }
    }
}