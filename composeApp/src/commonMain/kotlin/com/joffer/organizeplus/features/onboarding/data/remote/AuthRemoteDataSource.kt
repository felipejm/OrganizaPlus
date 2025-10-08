package com.joffer.organizeplus.features.onboarding.data.remote

import com.joffer.organizeplus.features.onboarding.domain.entities.User

interface AuthRemoteDataSource {
    suspend fun signUp(email: String, password: String): Result<User>
    suspend fun signIn(email: String, password: String): Result<AuthTokens>
    suspend fun refreshToken(refreshToken: String): Result<AuthTokens>
    suspend fun signOut(accessToken: String): Result<Unit>
}

data class AuthTokens(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    val expiresIn: Int
)
