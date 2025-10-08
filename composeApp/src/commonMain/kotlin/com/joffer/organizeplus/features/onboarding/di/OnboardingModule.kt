package com.joffer.organizeplus.features.onboarding.di

import com.joffer.organizeplus.features.onboarding.data.local.AuthLocalDataSource
import com.joffer.organizeplus.features.onboarding.data.local.AuthLocalDataSourceImpl
import com.joffer.organizeplus.features.onboarding.data.remote.AuthRemoteDataSource
import com.joffer.organizeplus.features.onboarding.data.remote.AuthRemoteDataSourceImpl
import com.joffer.organizeplus.features.onboarding.data.repositories.AuthRepositoryImpl
import com.joffer.organizeplus.features.onboarding.domain.repositories.AuthRepository
import com.joffer.organizeplus.features.onboarding.domain.usecases.CheckAuthStatusUseCase
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignInUseCase
import com.joffer.organizeplus.features.onboarding.domain.usecases.SignUpUseCase
import com.joffer.organizeplus.features.onboarding.signin.presentation.SignInViewModel
import com.joffer.organizeplus.features.onboarding.signup.presentation.SignUpViewModel
import org.koin.dsl.module

val onboardingModule = module {
    // Data Sources
    single<AuthLocalDataSource> { AuthLocalDataSourceImpl() }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(httpClient = get()) }

    // Repository
    single<AuthRepository> {
        AuthRepositoryImpl(
            get<AuthRemoteDataSource>(),
            get<AuthLocalDataSource>()
        )
    }

    // Use Cases
    single { SignUpUseCase(authRepository = get()) }
    single { SignInUseCase(authRepository = get()) }
    single { CheckAuthStatusUseCase(authRepository = get()) }

    // ViewModels
    single { SignUpViewModel(signUpUseCase = get()) }
    single { SignInViewModel(signInUseCase = get()) }
}
