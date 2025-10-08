package com.joffer.organizeplus.features.onboarding.domain.usecases

import com.joffer.organizeplus.features.onboarding.domain.entities.AuthCredentials
import com.joffer.organizeplus.features.onboarding.domain.entities.SignInError
import com.joffer.organizeplus.features.onboarding.domain.entities.User
import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository

open class SignInUseCase(
    private val authRepository: AuthRepository
) {
    open suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank() || password.isBlank()) {
            return Result.failure(SignInException(SignInError.EMAIL_OR_PASSWORD_EMPTY))
        }

        val credentials = AuthCredentials(email, password)
        return authRepository.signIn(credentials)
    }
}

class SignInException(val error: SignInError) : Exception()
