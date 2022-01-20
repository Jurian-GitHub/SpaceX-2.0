package com.example.spacex20.feature_launches.presentation.launch_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex20.feature_launches.domain.model.Launch
import com.example.spacex20.feature_launches.domain.use_case.get_launch.GetLaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    private val getLaunchUseCase: GetLaunchUseCase,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(LaunchDetailState())
    val state: State<LaunchDetailState> = _state

    init {
        savedStateHandle.get<Int>("launchId")?.let { launchId ->
            if (launchId != -1) {
                viewModelScope.launch {
                    getLaunchUseCase(launchId)?.also { launch ->
                        _state.value = LaunchDetailState(launch)
                    }
                }
            }
        }
    }
 }