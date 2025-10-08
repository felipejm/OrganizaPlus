package com.joffer.organizeplus.features.onboarding.domain.repositories

import com.joffer.organizeplus.features.onboarding.domain.entities.AuthCredentials
import com.joffer.organizeplus.features.onboarding.domain.entities.User

interface AuthRepository {
    suspend fun signUp(credentials: AuthCredentials): Result<User>
    suspend fun signIn(credentials: AuthCredentials): Result<User>
    suspend fun isUserSignedIn(): Boolean
    suspend fun signOut()
    suspend fun getCurrentUser(): User?
}
