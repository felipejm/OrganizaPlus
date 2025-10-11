package com.joffer.organizeplus.features.onboarding.data.repositories

import com.joffer.organizeplus.features.onboarding.data.local.AuthLocalDataSource
import com.joffer.organizeplus.features.onboarding.data.remote.AuthRemoteDataSource
import com.joffer.organizeplus.features.onboarding.domain.entities.AuthCredentials
import com.joffer.organizeplus.features.onboarding.domain.entities.User
import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository

class AuthRepositoryImpl(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun signUp(credentials: AuthCredentials): Result<User> {
        val result = remoteDataSource.signUp(credentials.email, credentials.password)

        return result.map { tokens ->
            val user = User(
                id = tokens.userId,
                email = tokens.email
            )

            localDataSource.saveUserSession(
                userId = tokens.userId,
                email = tokens.email,
                accessToken = tokens.accessToken,
                refreshToken = tokens.refreshToken
            )

            user
        }
    }

    override suspend fun signIn(credentials: AuthCredentials): Result<User> {
        val result = remoteDataSource.signIn(credentials.email, credentials.password)

        return result.map { tokens ->
            val user = User(
                id = tokens.userId,
                email = tokens.email
            )

            localDataSource.saveUserSession(
                userId = tokens.userId,
                email = tokens.email,
                accessToken = tokens.accessToken,
                refreshToken = tokens.refreshToken
            )

            user
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        return localDataSource.isUserSignedIn()
    }

    override suspend fun signOut() {
        val accessToken = localDataSource.getAccessToken()
        localDataSource.clearUserSession()
        
        if (accessToken != null) {
            try {
                remoteDataSource.signOut(accessToken)
            } catch (e: Exception) {
                // Ignore remote sign out errors since we've already cleared the local session
            }
        }
    }

    override suspend fun getCurrentUser(): User? {
        val session = localDataSource.getUserSession()
        return session?.let {
            User(
                id = it.userId,
                email = it.email
            )
        }
    }
}
