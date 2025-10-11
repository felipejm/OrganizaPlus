package com.joffer.organizeplus.features.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joffer.organizeplus.features.onboarding.signin.presentation.SignInScreen
import com.joffer.organizeplus.features.onboarding.signin.presentation.SignInViewModel
import com.joffer.organizeplus.features.onboarding.signup.presentation.SignUpScreen
import com.joffer.organizeplus.features.onboarding.signup.presentation.SignUpViewModel
import com.joffer.organizeplus.navigation.Dashboard
import com.joffer.organizeplus.navigation.SignIn
import com.joffer.organizeplus.navigation.SignUp
import com.joffer.organizeplus.navigation.config.MainNavigationProvider
import org.koin.compose.koinInject

class OnboardingNavigationProvider : MainNavigationProvider {
    
    override fun NavGraphBuilder.registerMainScreens(navController: NavHostController) {
        composable<SignIn> {
            val viewModel: SignInViewModel = koinInject()
            SignInScreen(
                viewModel = viewModel,
                onNavigateToSignUp = {
                    navController.navigate(SignUp) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Dashboard) {
                        popUpTo(SignIn) { inclusive = true }
                    }
                }
            )
        }

        composable<SignUp> {
            val viewModel: SignUpViewModel = koinInject()
            SignUpScreen(
                viewModel = viewModel,
                onNavigateToSignIn = {
                    navController.navigate(SignIn) {
                        popUpTo(SignUp) { inclusive = true }
                    }
                },
                onNavigateToDashboard = {
                    navController.navigate(Dashboard) {
                        popUpTo(SignUp) { inclusive = true }
                    }
                }
            )
        }
    }
}

