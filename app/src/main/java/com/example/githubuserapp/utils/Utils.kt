package com.example.githubuserapp.utils

import android.view.View
import android.widget.ProgressBar

object Utils {
    fun isLoading(progressbar: ProgressBar, loading: Boolean){
        if(loading){
            progressbar.visibility = View.VISIBLE
        }else{
            progressbar.visibility = View.GONE
        }
    }

}