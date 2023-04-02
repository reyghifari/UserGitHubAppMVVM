package com.hann.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hann.core.domain.usecase.GithubUseCase

class FavoriteViewModel(githubUseCase: GithubUseCase) : ViewModel()  {

    val favoriteUser = githubUseCase.getFavoriteUser().asLiveData()

}