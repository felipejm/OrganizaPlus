package com.joffer.organizeplus

import androidx.compose.ui.window.ComposeUIViewController
import com.joffer.organizeplus.di.appModule
import com.joffer.organizeplus.di.platformDatabaseModule
import org.koin.core.context.startKoin

// Initialize Koin once using lazy - guaranteed single execution
private val koinApplication = lazy {
    startKoin {
        modules(appModule, platformDatabaseModule)
    }
}

fun MainViewController() = ComposeUIViewController {
    // Initialize Koin before first composition
    koinApplication.value
    App()
}
