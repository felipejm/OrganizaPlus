package com.joffer.organizeplus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.joffer.organizeplus.designsystem.typography.ProvideTypography
import com.joffer.organizeplus.features.onboarding.domain.usecases.CheckAuthStatusUseCase
import com.joffer.organizeplus.navigation.config.MainNavigationProvider
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.koin.core.qualifier.named

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val checkAuthStatusUseCase: CheckAuthStatusUseCase = koinInject()
    val coroutineScope = rememberCoroutineScope()
    val mainNavigationProviders: List<MainNavigationProvider> = koinInject(named("mainProviders"))

    var startDestination by remember { mutableStateOf<Any?>(null) }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
        coroutineScope.launch {
            val isSignedIn = checkAuthStatusUseCase()
            startDestination = if (isSignedIn) Dashboard else SignIn
        }
    }

    if (startDestination == null) {
        // Loading while checking auth status
        return
    }

    ProvideTypography {
        NavHost(
            navController = navController,
            startDestination = startDestination!!
        ) {
            registerAllFeatureScreens(
                providers = mainNavigationProviders,
                navController = navController
            )
        }
    }
}

private fun androidx.navigation.NavGraphBuilder.registerAllFeatureScreens(
    providers: List<MainNavigationProvider>,
    navController: NavHostController
) {
    providers.forEach { provider ->
        with(provider) {
            registerMainScreens(navController)
        }
    }
}
