package com.joffer.organizeplus

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
// import com.joffer.organizeplus.core.di.coreModule
import com.joffer.organizeplus.designsystem.theme.AppTheme
import com.joffer.organizeplus.di.appModule
import com.joffer.organizeplus.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    KoinApplication(application = {
        modules(appModule)
    }) {
        AppTheme {
            AppNavigation(onNavHostReady = onNavHostReady)
        }
    }
}