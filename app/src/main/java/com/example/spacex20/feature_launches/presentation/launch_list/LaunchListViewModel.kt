package com.example.spacex20.feature_launches.presentation.launch_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex20.feature_launches.core.util.Resource
import com.example.spacex20.feature_launches.domain.use_case.get_launches.GetLaunchesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LaunchListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private val _state = mutableStateOf(LaunchListState())
    val state: State<LaunchListState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getLaunches()
    }

    private fun getLaunches() {
        getLaunchesUseCase().onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _state.value = LaunchListState(
                        launches = result.data ?: emptyList(),
                        isLoading = true
                    )
                    _eventFlow.emit(UIEvent.ShowSnackBar(
                        result.message ?: "Something went wrong"
                    ))
                }
                is Resource.Success -> {
                    _state.value = LaunchListState(launches = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = LaunchListState(launches = result.data ?: emptyList())
                    _eventFlow.emit(UIEvent.ShowSnackBar(
                        result.message ?: "Something went wrong"
                    ))
                }
            }
        }.launchIn(viewModelScope)
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}