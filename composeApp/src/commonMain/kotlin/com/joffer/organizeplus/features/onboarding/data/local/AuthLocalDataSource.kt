package com.joffer.organizeplus.features.onboarding.data.local

interface AuthLocalDataSource {
    suspend fun saveUserSession(userId: String, email: String, accessToken: String, refreshToken: String)
    suspend fun getUserSession(): UserSession?
    suspend fun clearUserSession()
    suspend fun isUserSignedIn(): Boolean
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
}

data class UserSession(
    val userId: String,
    val email: String,
    val accessToken: String,
    val refreshToken: String
)

class AuthLocalDataSourceImpl : AuthLocalDataSource {

    private var currentSession: UserSession? = null

    override suspend fun saveUserSession(userId: String, email: String, accessToken: String, refreshToken: String) {
        currentSession = UserSession(userId, email, accessToken, refreshToken)
    }

    override suspend fun getUserSession(): UserSession? {
        return currentSession
    }

    override suspend fun clearUserSession() {
        currentSession = null
    }

    override suspend fun isUserSignedIn(): Boolean {
        return currentSession != null
    }

    override suspend fun getAccessToken(): String? {
        return currentSession?.accessToken
    }

    override suspend fun getRefreshToken(): String? {
        return currentSession?.refreshToken
    }
}
