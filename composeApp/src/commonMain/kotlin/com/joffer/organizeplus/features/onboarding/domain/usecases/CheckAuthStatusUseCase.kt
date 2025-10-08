package com.joffer.organizeplus.features.onboarding.domain.usecases

import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository

class CheckAuthStatusUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        return authRepository.isUserSignedIn()
    }
}
