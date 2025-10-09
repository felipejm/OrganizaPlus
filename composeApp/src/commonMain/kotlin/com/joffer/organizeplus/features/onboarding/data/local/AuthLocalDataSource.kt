package com.joffer.organizeplus.features.onboarding.data.local

import com.joffer.organizeplus.core.storage.SecureKeyValueStorage

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

class AuthLocalDataSourceImpl(
    private val secureStorage: SecureKeyValueStorage
) : AuthLocalDataSource {

    override suspend fun saveUserSession(
        userId: String,
        email: String,
        accessToken: String,
        refreshToken: String
    ) {
        secureStorage.putString(KEY_USER_ID, userId)
        secureStorage.putString(KEY_USER_EMAIL, email)
        secureStorage.putString(KEY_ACCESS_TOKEN, accessToken)
        secureStorage.putString(KEY_REFRESH_TOKEN, refreshToken)
    }

    override suspend fun getUserSession(): UserSession? {
        val userId = secureStorage.getString(KEY_USER_ID) ?: return null
        val email = secureStorage.getString(KEY_USER_EMAIL) ?: return null
        val accessToken = secureStorage.getString(KEY_ACCESS_TOKEN) ?: return null
        val refreshToken = secureStorage.getString(KEY_REFRESH_TOKEN) ?: return null

        return UserSession(
            userId = userId,
            email = email,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    override suspend fun clearUserSession() {
        secureStorage.remove(KEY_USER_ID)
        secureStorage.remove(KEY_USER_EMAIL)
        secureStorage.remove(KEY_ACCESS_TOKEN)
        secureStorage.remove(KEY_REFRESH_TOKEN)
    }

    override suspend fun isUserSignedIn(): Boolean {
        return secureStorage.contains(KEY_ACCESS_TOKEN) &&
            secureStorage.contains(KEY_USER_ID)
    }

    override suspend fun getAccessToken(): String? {
        return secureStorage.getString(KEY_ACCESS_TOKEN)
    }

    override suspend fun getRefreshToken(): String? {
        return secureStorage.getString(KEY_REFRESH_TOKEN)
    }

    companion object {
        private const val KEY_USER_ID = "auth_user_id"
        private const val KEY_USER_EMAIL = "auth_user_email"
        private const val KEY_ACCESS_TOKEN = "auth_access_token"
        private const val KEY_REFRESH_TOKEN = "auth_refresh_token"
    }
}
