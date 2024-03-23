package com.example.githubuserapp.data.response

import com.google.gson.annotations.SerializedName

data class ResponseSearchUser(

	@field:SerializedName("total_count")
	val totalCount: Int,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean,

	@field:SerializedName("items")
	val items: List<ResponseListUserItem>
)