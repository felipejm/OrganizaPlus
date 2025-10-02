package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTagShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Tag Component",
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
                    text = "Tag Component",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
                Text(
                    text = "A generic, presentational tag component for labeling and categorization.",
                    style = typography.bodyMedium,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.md))
            }

            item {
                Text(
                    text = "Sizes",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                ) {
                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Large",
                            style = typography.caption,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        OrganizeTag(
                            text = "Large Tag",
                            size = TagSize.LARGE
                        )
                    }

                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Medium",
                            style = typography.caption,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        OrganizeTag(
                            text = "Medium Tag",
                            size = TagSize.MEDIUM
                        )
                    }

                    Column(
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Small",
                            style = typography.caption,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        OrganizeTag(
                            text = "Small",
                            size = TagSize.SMALL
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Variants",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    OrganizeTag(text = "Default", variant = TagVariant.DEFAULT)
                    OrganizeTag(text = "Border", variant = TagVariant.BORDER)
                    OrganizeTag(text = "Filled", variant = TagVariant.FILLED)
                    OrganizeTag(text = "Outlined", variant = TagVariant.OUTLINED)
                    OrganizeTag(text = "Success", variant = TagVariant.SUCCESS)
                    OrganizeTag(text = "Warning", variant = TagVariant.WARNING)
                    OrganizeTag(text = "Error", variant = TagVariant.ERROR)
                    OrganizeTag(text = "Info", variant = TagVariant.INFO)
                    OrganizeTag(text = "Disabled", enabled = false)
                }
            }

            item {
                Text(
                    text = "With Icons",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    OrganizeTag(
                        text = "Approved",
                        variant = TagVariant.SUCCESS,
                        leadingIcon = Icons.Default.Check
                    )
                    OrganizeTag(
                        text = "Rejected",
                        variant = TagVariant.ERROR,
                        leadingIcon = Icons.Default.Close
                    )
                    OrganizeTag(
                        text = "Removable",
                        variant = TagVariant.DEFAULT,
                        trailingIcon = Icons.Default.Close
                    )
                }
            }
        }
    }
}
