package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Layouts",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                Text(
                    text = "Layout Components",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
            }

            items(LayoutShowcaseItem.values()) { item ->
                LayoutShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun LayoutShowcaseItem(
    item: LayoutShowcaseItem,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = item.title,
                style = Typography.title,
                color = AppColorScheme.onSurface
            )

            Text(
                text = item.description,
                style = Typography.body,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            EmptyStateExamples()
        }
    }
}

@Composable
private fun EmptyStateExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Empty States",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )

        // No data empty state
        EmptyState(
            title = "No Data Available",
            message = "There are no items to display at the moment. Try refreshing or adding new content.",
            actionText = "Refresh",
            onAction = { /* Handle refresh */ }
        )

        // Error empty state
        EmptyState(
            title = "Something Went Wrong",
            message = "We couldn't load the data. Please check your connection and try again.",
            actionText = "Retry",
            onAction = { /* Handle retry */ }
        )

        // Success empty state
        EmptyState(
            title = "All Caught Up!",
            message = "You've completed all your tasks. Great job!",
            actionText = "Add New Task",
            onAction = { /* Handle add new */ }
        )
    }
}

@Composable
private fun EmptyState(
    title: String,
    message: String,
    actionText: String,
    onAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                tint = AppColorScheme.onSurfaceVariant,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            Text(
                text = title,
                style = Typography.h3,
                color = AppColorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(Spacing.sm))

            Text(
                text = message,
                style = Typography.body,
                color = AppColorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            OrganizePrimaryButton(
                text = actionText,
                onClick = onAction
            )
        }
    }
}

enum class LayoutShowcaseItem(
    val title: String,
    val description: String
) {
    EMPTY_STATE(
        title = "Empty States",
        description = "Components for when there's no content to display"
    ),
}
