package com.joffer.organizeplus.features.onboarding.data.remote

interface AuthRemoteDataSource {
    suspend fun signUp(email: String, password: String): Result<AuthTokens>
    suspend fun signIn(email: String, password: String): Result<AuthTokens>
    suspend fun refreshToken(refreshToken: String): Result<AuthTokens>
    suspend fun signOut(accessToken: String): Result<Unit>
}

data class AuthTokens(
    val userId: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    val expiresIn: Int
)
