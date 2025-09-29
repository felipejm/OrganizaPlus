package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@Composable
fun TagUsageExample(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Spacing.borderRadius))
            .background(AppColorScheme.surfaceContainerLow)
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        // Example 1: Tags as filters
        Text(
            text = "Filters:",
            style = Typography.bodyMedium,
            color = AppColorScheme.onSurface
        )
        var selectedCategories by remember { mutableStateOf(setOf("Work")) }
        val allCategories = listOf("Work", "Personal", "Urgent", "Ideas", "Shopping")

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            verticalArrangement = Arrangement.spacedBy(Spacing.xs)
        ) {
            allCategories.forEach { category ->
                val isSelected = category in selectedCategories
                Box(
                    modifier = Modifier.clickable {
                        selectedCategories = if (isSelected) {
                            selectedCategories - category
                        } else {
                            selectedCategories + category
                        }
                    }
                ) {
                    OrganizeTag(
                        text = category,
                        variant = if (isSelected) TagVariant.FILLED else TagVariant.OUTLINED,
                        leadingIcon = if (isSelected) Icons.Default.Check else null
                    )
                }
            }
        }

        // Example 2: Closable tags for selected items
        Text(
            text = "Selected Users:",
            style = Typography.bodyMedium,
            color = AppColorScheme.onSurface
        )
        var selectedUsers by remember { mutableStateOf(listOf("Alice", "Bob", "Charlie")) }

        if (selectedUsers.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                verticalArrangement = Arrangement.spacedBy(Spacing.xs)
            ) {
                selectedUsers.forEach { user ->
                    Box(
                        modifier = Modifier.clickable {
                            selectedUsers = selectedUsers - user
                        }
                    ) {
                        OrganizeTag(
                            text = user,
                            variant = TagVariant.DEFAULT,
                            trailingIcon = Icons.Default.Close
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No users selected.",
                style = Typography.body,
                color = AppColorScheme.neutral500
            )
        }

        // Example 3: Status indicators
        Text(
            text = "Project Status:",
            style = Typography.bodyMedium,
            color = AppColorScheme.onSurface
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrganizeTag(
                text = "Approved",
                variant = TagVariant.SUCCESS,
                leadingIcon = Icons.Default.Check
            )
            OrganizeTag(
                text = "Under Review",
                variant = TagVariant.WARNING,
                leadingIcon = Icons.Default.Refresh
            )
        }

        // Example 4: Tags in a list item
        Text(
            text = "Task Details:",
            style = Typography.bodyMedium,
            color = AppColorScheme.onSurface
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = AppColorScheme.white),
            shape = RoundedCornerShape(Spacing.borderRadius)
        ) {
            Column(modifier = Modifier.padding(Spacing.md)) {
                Text(
                    text = "Implement new feature",
                    style = Typography.subtitle,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OrganizeTag(
                        text = "High Priority",
                        variant = TagVariant.ERROR,
                        size = TagSize.SMALL
                    )
                    OrganizeTag(
                        text = "Frontend",
                        variant = TagVariant.DEFAULT,
                        size = TagSize.SMALL
                    )
                }
            }
        }
    }
}