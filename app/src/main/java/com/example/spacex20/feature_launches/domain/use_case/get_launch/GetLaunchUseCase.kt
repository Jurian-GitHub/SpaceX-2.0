package com.example.spacex20.feature_launches.domain.use_case.get_launch

import com.example.spacex20.feature_launches.domain.model.Launch
import com.example.spacex20.feature_launches.domain.repository.LaunchRepository

class GetLaunchUseCase(
    private val repository: LaunchRepository
) {

    suspend operator fun invoke(id: Int): Launch? {
        return repository.getLaunch(id)
    }
}