package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Chips",
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
                    text = "Chip Components",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
            }

            items(ChipShowcaseItem.values()) { item ->
                ChipShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun ChipShowcaseItem(
    item: ChipShowcaseItem,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = item.title,
                style = typography.titleMedium,
                color = AppColorScheme.onSurface
            )

            Text(
                text = item.description,
                style = typography.bodyMedium,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (item) {
                ChipShowcaseItem.PRIORITY -> {
                    PriorityChipExamples()
                }

                ChipShowcaseItem.CATEGORY -> {
                    CategoryChipExamples()
                }

                ChipShowcaseItem.STATUS -> {
                    StatusChipExamples()
                }
            }
        }
    }
}

@Composable
private fun PriorityChipExamples() {
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Priority Chips",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            PriorityChip(text = "LOW", priority = ObligationPriority.LOW)
            PriorityChip(text = "MEDIUM", priority = ObligationPriority.MEDIUM)
            PriorityChip(text = "HIGH", priority = ObligationPriority.HIGH)
            PriorityChip(text = "URGENT", priority = ObligationPriority.URGENT)
        }
    }
}

@Composable
private fun CategoryChipExamples() {
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Category Chips",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            CategoryChip(
                category = "Work",
                color = AppColorScheme.primary
            )
            CategoryChip(
                category = "Personal",
                color = AppColorScheme.secondary
            )
            CategoryChip(
                category = "Health",
                color = AppColorScheme.success500
            )
            CategoryChip(
                category = "Finance",
                color = AppColorScheme.warning500
            )
        }
    }
}

@Composable
private fun StatusChipExamples() {
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Status Chips",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            StatusChip(
                status = ObligationStatus.PENDING,
                text = "Pending"
            )
            StatusChip(
                status = ObligationStatus.PAID,
                text = "Paid"
            )
            StatusChip(
                status = ObligationStatus.OVERDUE,
                text = "Overdue"
            )
        }
    }
}

@Composable
private fun StatusChip(
    status: ObligationStatus,
    text: String,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    val (containerColor, contentColor) = when (status) {
        ObligationStatus.PENDING -> AppColorScheme.surfaceVariant to AppColorScheme.onSurfaceVariant
        ObligationStatus.PAID -> AppColorScheme.success100 to AppColorScheme.success700
        ObligationStatus.OVERDUE -> AppColorScheme.danger100 to AppColorScheme.danger700
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = text,
                style = typography.labelMedium,
                color = contentColor
            )
        },
        modifier = modifier,
        colors = AssistChipDefaults.assistChipColors(
            containerColor = containerColor,
            labelColor = contentColor
        )
    )
}

enum class ChipShowcaseItem(
    val title: String,
    val description: String
) {
    PRIORITY(
        title = "Priority Chips",
        description = "Chips indicating task priority levels"
    ),
    CATEGORY(
        title = "Category Chips",
        description = "Chips for categorizing content"
    ),
    STATUS(
        title = "Status Chips",
        description = "Chips showing current status"
    )
}
