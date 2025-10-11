package com.joffer.organizeplus.features.duty.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyScreen
import com.joffer.organizeplus.features.duty.create.presentation.CreateDutyViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsListViewModel
import com.joffer.organizeplus.features.duty.detail.presentation.DutyDetailsScreen
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewScreen
import com.joffer.organizeplus.features.duty.review.presentation.DutyReviewViewModel
import com.joffer.organizeplus.navigation.AddDutyOccurrence
import com.joffer.organizeplus.navigation.CreateDuty
import com.joffer.organizeplus.navigation.CreateDutyWithCategory
import com.joffer.organizeplus.navigation.DutyOccurrences
import com.joffer.organizeplus.navigation.DutyReview
import com.joffer.organizeplus.navigation.EditDuty
import com.joffer.organizeplus.navigation.config.MainNavigationProvider
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

class DutyMainNavigationProvider : MainNavigationProvider {

    override fun NavGraphBuilder.registerMainScreens(navController: NavHostController) {
        composable<CreateDuty> {
            val viewModel: CreateDutyViewModel = koinInject {
                parametersOf(null, null)
            }
            CreateDutyScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<CreateDutyWithCategory> { backStackEntry ->
            val createDutyWithCategory = backStackEntry.toRoute<CreateDutyWithCategory>()
            val viewModel: CreateDutyViewModel = koinInject {
                parametersOf(null, createDutyWithCategory.category)
            }
            CreateDutyScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<EditDuty> { backStackEntry ->
            val editDuty = backStackEntry.toRoute<EditDuty>()
            val viewModel: CreateDutyViewModel = koinInject {
                parametersOf(editDuty.dutyId, editDuty.categoryName)
            }
            CreateDutyScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<DutyOccurrences> { backStackEntry ->
            val dutyOccurrences = backStackEntry.toRoute<DutyOccurrences>()
            val viewModel: DutyDetailsListViewModel = koinInject {
                parametersOf(dutyOccurrences.dutyId)
            }
            DutyDetailsScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEditDuty = { dutyId, categoryName ->
                    navController.navigate(EditDuty(dutyId, categoryName))
                }
            )
        }

        composable<AddDutyOccurrence> { backStackEntry ->
            val addDutyOccurrence = backStackEntry.toRoute<AddDutyOccurrence>()
            val viewModel: DutyReviewViewModel = koinInject {
                parametersOf(addDutyOccurrence.dutyId)
            }
            DutyReviewScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<DutyReview> { backStackEntry ->
            val dutyReview = backStackEntry.toRoute<DutyReview>()
            val viewModel: DutyReviewViewModel = koinInject {
                parametersOf(dutyReview.category)
            }
            DutyReviewScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDuty = { dutyId ->
                    navController.navigate(DutyOccurrences(dutyId))
                }
            )
        }
    }
}
