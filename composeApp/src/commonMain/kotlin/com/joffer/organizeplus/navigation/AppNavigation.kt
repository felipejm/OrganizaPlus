package com.joffer.organizeplus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.typography.ProvideSfProTypography
import com.joffer.organizeplus.features.dashboard.presentation.DashboardScreen
import com.joffer.organizeplus.features.dashboard.presentation.DashboardViewModel
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyScreen
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsScreen
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import com.joffer.organizeplus.features.duty.list.presentation.DutyListScreen
import com.joffer.organizeplus.features.duty.list.presentation.DutyListViewModel
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewScreen
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewViewModel
import com.joffer.organizeplus.features.settings.presentation.SettingsScreen
import com.joffer.organizeplus.features.settings.presentation.SettingsViewModel
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    val dashboardViewModel: DashboardViewModel = koinInject()

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

    ProvideSfProTypography {
        NavHost(
            navController = navController,
            startDestination = Dashboard
        ) {
            composable<Dashboard> {
                DashboardScreen(
                    viewModel = dashboardViewModel,
                    onNavigateToPersonalDuties = {
                        navController.navigate(Duties(CategoryConstants.PERSONAL))
                    },
                    onNavigateToCompanyDuties = {
                        navController.navigate(Duties(CategoryConstants.COMPANY))
                    },
                    onNavigateToDutyDetails = { dutyId ->
                        navController.navigate(DutyOccurrences(dutyId))
                    },
                    onNavigateToSettings = {
                        navController.navigate(Settings)
                    },
                    onNavigateToCreateDuty = {
                        navController.navigate(CreateDuty)
                    },
                )
            }

            composable<CreateDuty> {
                val createDutyViewModel: CreateDutyViewModel = koinInject {
                    parametersOf(null, null)
                }
                CreateDutyScreen(
                    viewModel = createDutyViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable<CreateDutyWithCategory> { backStackEntry ->
                val createDutyWithCategory = backStackEntry.toRoute<CreateDutyWithCategory>()
                val createDutyViewModel: CreateDutyViewModel =
                    koinInject { parametersOf(null, createDutyWithCategory.category) }
                CreateDutyScreen(
                    viewModel = createDutyViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable<Duties> { backStackEntry ->
                // Type-safe route extraction using toRoute()
                val duties = backStackEntry.toRoute<Duties>()
                val categoryFilter = when (duties.category) {
                    CategoryConstants.PERSONAL -> DutyCategoryFilter.Personal
                    else -> DutyCategoryFilter.Company
                }
                val dutyListViewModel: DutyListViewModel = koinInject { parametersOf(categoryFilter) }
                DutyListScreen(
                    viewModel = dutyListViewModel,
                    categoryFilter = categoryFilter,
                    onNavigateToCreateDuty = {
                        navController.navigate(CreateDutyWithCategory(duties.category))
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToOccurrences = { dutyId ->
                        navController.navigate(DutyOccurrences(dutyId))
                    },
                    onNavigateToReview = {
                        navController.navigate(DutyReview(duties.category))
                    }
                )
            }

            composable<EditDuty> { backStackEntry ->
                // Type-safe route extraction using toRoute()
                val editDuty = backStackEntry.toRoute<EditDuty>()
                val createDutyViewModel: CreateDutyViewModel =
                    koinInject { parametersOf(editDuty.dutyId, editDuty.categoryName) }
                CreateDutyScreen(
                    viewModel = createDutyViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                )
            }

            composable<DutyOccurrences> { backStackEntry ->
                val dutyOccurrences = backStackEntry.toRoute<DutyOccurrences>()
                val dutyDetailsListViewModel: DutyDetailsListViewModel =
                    koinInject { parametersOf(dutyOccurrences.dutyId) }
                DutyDetailsScreen(
                    viewModel = dutyDetailsListViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onEditDuty = { editDutyId, categoryName ->
                        navController.navigate(EditDuty(editDutyId, categoryName))
                    }
                )
            }

            composable<Settings> {
                val settingsViewModel: SettingsViewModel = koinInject()
                SettingsScreen(
                    viewModel = settingsViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToDesignSystem = {
                        navController.navigate(DesignSystemCatalog)
                    }
                )
            }

            composable<DutyReview> { backStackEntry ->
                val dutyReview = backStackEntry.toRoute<DutyReview>()
                val dutyReviewViewModel: DutyReviewViewModel = koinInject { parametersOf(dutyReview.category) }
                DutyReviewScreen(
                    viewModel = dutyReviewViewModel,
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable<DesignSystemCatalog> {
                com.joffer.organizeplus.designsystem.catalog.DesignSystemCatalogScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onNavigateToComponent = { route ->
                        navController.navigate(route)
                    }
                )
            }

            // Design System Component Showcases
            composable("buttons") {
                com.joffer.organizeplus.designsystem.catalog.ButtonShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("cards") {
                com.joffer.organizeplus.designsystem.catalog.CardShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("inputs") {
                com.joffer.organizeplus.designsystem.catalog.InputShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("chips") {
                com.joffer.organizeplus.designsystem.catalog.ChipShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("badges") {
                com.joffer.organizeplus.designsystem.catalog.BadgeShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("simple_tags") {
                com.joffer.organizeplus.designsystem.catalog.SimpleTagShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("banners") {
                com.joffer.organizeplus.designsystem.catalog.BannerShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("layouts") {
                com.joffer.organizeplus.designsystem.catalog.LayoutShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("colors") {
                com.joffer.organizeplus.designsystem.catalog.ColorShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("typography") {
                com.joffer.organizeplus.designsystem.catalog.TypographyShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("progress") {
                com.joffer.organizeplus.designsystem.catalog.ProgressShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("message") {
                com.joffer.organizeplus.designsystem.catalog.MessageShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("select") {
                com.joffer.organizeplus.designsystem.catalog.SelectShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("radio") {
                com.joffer.organizeplus.designsystem.catalog.RadioShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("form") {
                com.joffer.organizeplus.designsystem.catalog.FormShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable("result") {
                com.joffer.organizeplus.designsystem.catalog.ResultShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable("charts") {
                com.joffer.organizeplus.designsystem.catalog.ChartShowcaseScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
