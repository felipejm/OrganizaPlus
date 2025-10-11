package com.joffer.organizeplus.features.duty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import com.joffer.organizeplus.features.duty.list.domain.DutyCategoryFilter
import com.joffer.organizeplus.features.duty.list.presentation.DutyListScreen
import com.joffer.organizeplus.features.duty.list.presentation.DutyListViewModel
import com.joffer.organizeplus.navigation.CreateDutyWithCategory
import com.joffer.organizeplus.navigation.Duties
import com.joffer.organizeplus.navigation.DutyOccurrences
import com.joffer.organizeplus.navigation.config.BottomNavConfiguration
import com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.nav_company_review
import organizeplus.composeapp.generated.resources.nav_personal_review

/**
 * Navigation provider for personal duties feature
 */
class PersonalDutyNavigationProvider : FeatureNavigationProvider {

    @Composable
    override fun getBottomNavConfiguration(): BottomNavConfiguration {
        return BottomNavConfiguration(
            route = Duties(CategoryConstants.PERSONAL),
            labelRes = Res.string.nav_personal_review,
            icon = OrganizeIcons.Navigation.User,
            selectedIcon = OrganizeIcons.Navigation.UserFilled,
            order = 1
        )
    }

    override fun NavGraphBuilder.registerScreens(
        mainNavController: NavHostController,
        tabNavController: NavHostController
    ) {
        composable<Duties> { backStackEntry ->
            val duties = backStackEntry.toRoute<Duties>()
            val categoryFilter = mapCategoryToCategoryFilter(duties.category)
            val viewModel: DutyListViewModel = koinInject { parametersOf(categoryFilter) }

            DutyListScreen(
                viewModel = viewModel,
                categoryFilter = categoryFilter,
                onNavigateToCreateDuty = {
                    mainNavController.navigate(CreateDutyWithCategory(duties.category))
                },
                onNavigateToOccurrences = { dutyId ->
                    mainNavController.navigate(DutyOccurrences(dutyId))
                }
            )
        }
    }

    private fun mapCategoryToCategoryFilter(category: String): DutyCategoryFilter {
        return when (category) {
            CategoryConstants.PERSONAL -> DutyCategoryFilter.Personal
            CategoryConstants.COMPANY -> DutyCategoryFilter.Company
            else -> DutyCategoryFilter.Personal
        }
    }
}

/**
 * Navigation provider for company duties feature
 */
class CompanyDutyNavigationProvider : FeatureNavigationProvider {

    @Composable
    override fun getBottomNavConfiguration(): BottomNavConfiguration {
        return BottomNavConfiguration(
            route = Duties(CategoryConstants.COMPANY),
            labelRes = Res.string.nav_company_review,
            icon = OrganizeIcons.Navigation.Building,
            selectedIcon = OrganizeIcons.Navigation.BuildingFilled,
            order = 2
        )
    }

    override fun NavGraphBuilder.registerScreens(
        mainNavController: NavHostController,
        tabNavController: NavHostController
    ) {
        // Screens are already registered by PersonalDutyNavigationProvider
        // This provider only contributes to bottom navigation
    }
}
