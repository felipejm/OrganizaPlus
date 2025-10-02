package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Buttons",
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
                    text = "Button Variants",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
            }

            items(ButtonShowcaseItem.values()) { item ->
                ButtonShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun ButtonShowcaseItem(
    item: ButtonShowcaseItem,
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                when (item) {
                    ButtonShowcaseItem.PRIMARY -> {
                        OrganizePrimaryButton(
                            text = "Primary Button",
                            onClick = { }
                        )
                        OrganizePrimaryButton(
                            text = "With Icon",
                            onClick = { },
                            icon = Icons.Default.Add
                        )
                        OrganizePrimaryButton(
                            text = "Disabled",
                            onClick = { },
                            enabled = false
                        )
                    }
                    ButtonShowcaseItem.SECONDARY -> {
                        OrganizeSecondaryButton(
                            text = "Secondary Button",
                            onClick = { }
                        )
                        OrganizeSecondaryButton(
                            text = "With Icon",
                            onClick = { },
                            icon = Icons.Default.Settings
                        )
                        OrganizeSecondaryButton(
                            text = "Disabled",
                            onClick = { },
                            enabled = false
                        )
                    }
                    ButtonShowcaseItem.TEXT -> {
                        OrganizeTextButton(
                            text = "Text Button",
                            onClick = { }
                        )
                        OrganizeTextButton(
                            text = "Disabled",
                            onClick = { },
                            enabled = false
                        )
                    }
                    ButtonShowcaseItem.ICON -> {
                        OrganizeIconButton(
                            onClick = { }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "Settings"
                            )
                        }
                        OrganizeIconButton(
                            onClick = { },
                            enabled = false
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add"
                            )
                        }
                    }
                    ButtonShowcaseItem.FAB -> {
                        // FAB is typically floating, but we'll show it in a container for demo
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    color = AppColorScheme.primary,
                                    shape = androidx.compose.foundation.shape.CircleShape
                                ),
                            contentAlignment = androidx.compose.ui.Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                tint = AppColorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}

enum class ButtonShowcaseItem(
    val title: String,
    val description: String
) {
    PRIMARY(
        title = "Primary Button",
        description = "Main action buttons with solid background"
    ),
    SECONDARY(
        title = "Secondary Button",
        description = "Secondary actions with outlined style"
    ),
    TEXT(
        title = "Text Button",
        description = "Minimal text-only buttons for subtle actions"
    ),
    ICON(
        title = "Icon Button",
        description = "Icon-only buttons for compact actions"
    ),
    FAB(
        title = "Floating Action Button",
        description = "Floating circular button for primary actions"
    )
}
