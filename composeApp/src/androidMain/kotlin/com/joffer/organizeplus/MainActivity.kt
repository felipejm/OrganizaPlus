package com.joffer.organizeplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.di.appModule
import com.joffer.organizeplus.di.platformDatabaseModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Enable edge-to-edge with custom status bar color
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = SemanticColors.Background.surface.toArgb(),
                darkScrim = SemanticColors.Background.surface.toArgb()
            )
        )
        super.onCreate(savedInstanceState)

        // Initialize Napier logging
        Napier.base(DebugAntilog())

        // Initialize Koin with Android context
        startKoin {
            androidContext(this@MainActivity)
            modules(appModule, platformDatabaseModule)
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
