package com.hann.githubappmvvm.presentation.main

import com.hann.core.domain.model.User

data class UserListState (
    val isLoading : Boolean = false,
    val users: List<User> = emptyList(),
    val error: String = ""
)