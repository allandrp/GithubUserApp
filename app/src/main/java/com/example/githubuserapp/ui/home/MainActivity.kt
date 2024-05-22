package com.example.githubuserapp.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.response.ResponseListUserItem
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.ViewModelFactory
import com.example.githubuserapp.ui.detail.DetailActivity
import com.example.githubuserapp.ui.favourites.FavouritesActivity
import com.example.githubuserapp.utils.Result
import com.example.githubuserapp.utils.Utils

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterListUser: AdapterListUser
    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val progressBar = binding.loadingBarMain

        with(binding) {
            mainSearchView.setupWithSearchBar(mainSearchBar)

            mainSearchView.editText.setOnEditorActionListener { textview, _, _ ->

                mainSearchBar.setText(textview.text)
                homeViewModel.searchUser(textview.text.toString())
                    .observe(this@MainActivity) { result ->
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
                                Toast.makeText(
                                    this@MainActivity,
                                    result.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            else -> {
                                Utils.isLoading(progressBar, false)
                            }
                        }
                    }
                mainSearchView.hide()
                true
            }

            this@MainActivity.onBackPressedDispatcher.addCallback(
                this@MainActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (mainSearchView.isShowing) {
                            mainSearchView.hide()
                        } else {
                            if (isEnabled) {
                                isEnabled = false
                                this@MainActivity.onBackPressed()
                            }
                        }
                    }
                })
        }

        homeViewModel.getUserHeadline().observe(this@MainActivity) { result ->
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
                    Toast.makeText(this@MainActivity, result.message, Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Utils.isLoading(progressBar, false)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)

        when(item.itemId){
            R.id.favourite_option -> {
                val intent = Intent(this, FavouritesActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setRecyclerview(listData: ArrayList<ResponseListUserItem>) {
        adapterListUser = AdapterListUser(listData) { item ->
            val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
            intentToDetail.putExtra("username", item.login)
            intentToDetail.putExtra("avatarUrl", item.avatarUrl)
            startActivity(intentToDetail)
        }

        val layoutManager = LinearLayoutManager(this@MainActivity)
        with(binding) {
            mainRecyclerView.layoutManager = layoutManager
            mainRecyclerView.adapter = adapterListUser
            mainRecyclerView.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    layoutManager.orientation
                )
            )
            adapterListUser.notifyDataSetChanged()
        }
    }
}