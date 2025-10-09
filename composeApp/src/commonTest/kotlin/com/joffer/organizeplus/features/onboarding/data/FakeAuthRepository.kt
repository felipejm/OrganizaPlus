package com.joffer.organizeplus.features.onboarding.data

import com.joffer.organizeplus.features.onboarding.domain.entities.AuthCredentials
import com.joffer.organizeplus.features.onboarding.domain.entities.User
import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository

class FakeAuthRepository : AuthRepository {

    var signUpCallCount = 0
    var signInCallCount = 0
    var signOutCallCount = 0
    var isUserSignedInCallCount = 0

    private var isSignedIn = false
    private var currentUser: User? = null

    var signUpResult: Result<User> = Result.success(User("user123", "test@example.com"))
    var signInResult: Result<User> = Result.success(User("user123", "test@example.com"))

    override suspend fun signUp(credentials: AuthCredentials): Result<User> {
        signUpCallCount++
        return signUpResult.onSuccess {
            currentUser = it
            isSignedIn = true
        }
    }

    override suspend fun signIn(credentials: AuthCredentials): Result<User> {
        signInCallCount++
        return signInResult.onSuccess {
            currentUser = it
            isSignedIn = true
        }
    }

    override suspend fun isUserSignedIn(): Boolean {
        isUserSignedInCallCount++
        return isSignedIn
    }

    override suspend fun signOut() {
        signOutCallCount++
        isSignedIn = false
        currentUser = null
    }

    override suspend fun getCurrentUser(): User? {
        return currentUser
    }

    fun setSignedIn(signedIn: Boolean) {
        isSignedIn = signedIn
    }

    fun setCurrentUser(user: User?) {
        currentUser = user
    }

    fun reset() {
        signUpCallCount = 0
        signInCallCount = 0
        signOutCallCount = 0
        isUserSignedInCallCount = 0
        isSignedIn = false
        currentUser = null
        signUpResult = Result.success(User("user123", "test@example.com"))
        signInResult = Result.success(User("user123", "test@example.com"))
    }
}

