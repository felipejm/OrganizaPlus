package com.joffer.organizeplus.features.onboarding.domain.usecases

import com.joffer.organizeplus.features.onboarding.domain.entities.AuthCredentials
import com.joffer.organizeplus.features.onboarding.domain.entities.SignUpError
import com.joffer.organizeplus.features.onboarding.domain.entities.User
import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository

open class SignUpUseCase(
    private val authRepository: AuthRepository
) {
    open suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String
    ): Result<User> {
        when {
            email.isBlank() -> return Result.failure(SignUpException(SignUpError.EMAIL_EMPTY))
            !isValidEmail(email) -> return Result.failure(SignUpException(SignUpError.EMAIL_INVALID))
        }

        when {
            password.isBlank() -> return Result.failure(SignUpException(SignUpError.PASSWORD_EMPTY))
            password.length < 6 -> return Result.failure(SignUpException(SignUpError.PASSWORD_TOO_SHORT))
            password != confirmPassword -> return Result.failure(SignUpException(SignUpError.PASSWORDS_DO_NOT_MATCH))
        }

        val credentials = AuthCredentials(email, password)
        return authRepository.signUp(credentials)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
        return emailRegex.matches(email)
    }
}

class SignUpException(val error: SignUpError) : Exception()
