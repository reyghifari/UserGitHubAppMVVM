package com.hann.githubappmvvm.presentation.follow

import com.hann.core.domain.model.User

data class FollowListState(
    val isLoading : Boolean = false,
    val users: List<User> = emptyList(),
    val error: String = ""
)