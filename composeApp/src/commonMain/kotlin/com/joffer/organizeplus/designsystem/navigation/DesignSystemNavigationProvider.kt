package com.joffer.organizeplus.designsystem.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.joffer.organizeplus.designsystem.catalog.BannerShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ButtonShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.CardShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ChartShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ChipShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ColorShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.DesignSystemCatalogScreen
import com.joffer.organizeplus.designsystem.catalog.LayoutShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ProgressShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.ResultShowcaseScreen
import com.joffer.organizeplus.designsystem.catalog.TypographyShowcaseScreen
import com.joffer.organizeplus.navigation.DesignSystemCatalog
import com.joffer.organizeplus.navigation.config.MainNavigationProvider

class DesignSystemNavigationProvider : MainNavigationProvider {

    override fun NavGraphBuilder.registerMainScreens(navController: NavHostController) {
        composable<DesignSystemCatalog> {
            DesignSystemCatalogScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToComponent = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable("buttons") {
            ButtonShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("cards") {
            CardShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("chips") {
            ChipShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("banners") {
            BannerShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("layouts") {
            LayoutShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("colors") {
            ColorShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("typography") {
            TypographyShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("progress") {
            ProgressShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("result") {
            ResultShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("charts") {
            ChartShowcaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
