package com.hann.core.data.source.remote.response

data class SearchResponse(
    val incomplete_results: Boolean,
    val items: List<UserResponse>,
    val total_count: Int
)