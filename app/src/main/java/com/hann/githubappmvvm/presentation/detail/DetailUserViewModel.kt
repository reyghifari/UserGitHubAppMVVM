package com.hann.githubappmvvm.presentation.detail

import androidx.lifecycle.*
import com.hann.core.data.Resource
import com.hann.core.domain.model.User
import com.hann.core.domain.usecase.GithubUseCase
import com.hann.core.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DetailUserViewModel(
    private val githubUseCase: GithubUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableLiveData<DetailUserState>()
    val state : LiveData<DetailUserState> = _state

    private val _favorite = MutableLiveData<User>()
    val favorite : LiveData<User> = _favorite

    fun setFavoriteUser(user: User, newStatus:Boolean) =
        githubUseCase.setFavoriteUser(user, newStatus)

    init {
        savedStateHandle.get<String>(Constants.PARAM_USERNAME)?.let {
            getDetailUser(it)
        }

    }

     fun userFavorite(id :String){
       viewModelScope.launch(Dispatchers.IO) {
           _favorite.postValue(githubUseCase.getFavoriteDetail(id))
       }
    }

    private fun getDetailUser(username: String){
        githubUseCase.getDetailUser(username).onEach {
                result ->
            when(result){
                is Resource.Loading -> {
                    _state.value = DetailUserState(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = DetailUserState(error = result.message ?: "An unexpected Error occurred")
                }
                is Resource.Success -> {
                    _state.value = DetailUserState(user = result.data)
                }

            }
        }.launchIn(viewModelScope)
    }

}