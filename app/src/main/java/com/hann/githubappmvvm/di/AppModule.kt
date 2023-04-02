package com.hann.githubappmvvm.di

import com.hann.core.domain.usecase.GithubInteractor
import com.hann.core.domain.usecase.GithubUseCase
import com.hann.githubappmvvm.presentation.detail.DetailUserViewModel
import com.hann.githubappmvvm.presentation.follow.FollowViewModel
import com.hann.githubappmvvm.presentation.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GithubUseCase> {
        GithubInteractor(get())
    }
}

val viewModelModule = module {
    viewModel {
        MainViewModel(get())
    }
    viewModel {
        DetailUserViewModel(get(), get())
    }
    viewModel {
        FollowViewModel(get())
    }
}
