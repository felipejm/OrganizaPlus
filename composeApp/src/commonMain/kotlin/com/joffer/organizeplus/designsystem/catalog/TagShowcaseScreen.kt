package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TagShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
                Text(
                    text = "A generic, presentational tag component for labeling and categorization.",
                    style = Typography.body,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.md))
            }
            
            item {
                Text(
                    text = "Sizes",
                    style = Typography.h3,
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
                            style = Typography.caption,
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
                            style = Typography.caption,
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
                            style = Typography.caption,
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
                    style = Typography.h3,
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
                    style = Typography.h3,
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
                        text = "Pending",
                        variant = TagVariant.WARNING,
                        leadingIcon = Icons.Default.Refresh
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
            
            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = "Real Usage Examples",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }
            
            item {
                TagUsageExample()
            }
        }
    }
}