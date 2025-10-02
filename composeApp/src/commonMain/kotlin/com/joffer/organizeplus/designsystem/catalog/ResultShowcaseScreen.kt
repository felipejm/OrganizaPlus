package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeResult
import com.joffer.organizeplus.designsystem.components.ResultType
import com.joffer.organizeplus.designsystem.spacing.Spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Result Components",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Success Result
            OrganizeResult(
                type = ResultType.SUCCESS,
                title = "Success!",
                description = "Your operation completed successfully."
            )

            // Error Result
            OrganizeResult(
                type = ResultType.ERROR,
                title = "Error",
                description = "Something went wrong. Please try again."
            )

            // Warning Result
            OrganizeResult(
                type = ResultType.WARNING,
                title = "Warning",
                description = "Please review your input before proceeding."
            )

            // Info Result
            OrganizeResult(
                type = ResultType.INFO,
                title = "Information",
                description = "Here's some helpful information for you."
            )
        }
    }
}
