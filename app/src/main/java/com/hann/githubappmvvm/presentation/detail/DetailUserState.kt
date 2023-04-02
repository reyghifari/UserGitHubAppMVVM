package com.hann.githubappmvvm.presentation.detail

import com.hann.core.domain.model.DetailUser

data class DetailUserState(
    val isLoading : Boolean = false,
    val user: DetailUser? = null,
    val error: String = ""
)