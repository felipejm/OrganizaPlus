package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Layouts",
                        style = Typography.h2
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
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
            
            when (item) {
                LayoutShowcaseItem.EMPTY_STATE -> {
                    EmptyStateExamples()
                }
                LayoutShowcaseItem.FORM_SECTION -> {
                    FormSectionExamples()
                }
                LayoutShowcaseItem.LOADING -> {
                    LoadingExamples()
                }
            }
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
private fun FormSectionExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Form Sections",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        FormSection {
            FormField(
                label = "First Name",
                value = "",
                onValueChange = { },
                placeholder = "Enter your first name"
            )
            
            FormField(
                label = "Last Name",
                value = "",
                onValueChange = { },
                placeholder = "Enter your last name"
            )
            
            FormField(
                label = "Email",
                value = "",
                onValueChange = { },
                placeholder = "Enter your email address"
            )
        }
        
        FormSection {
            FormField(
                label = "Theme",
                value = "",
                onValueChange = { },
                placeholder = "Select theme preference"
            )
            
            FormField(
                label = "Notifications",
                value = "",
                onValueChange = { },
                placeholder = "Configure notifications"
            )
        }
    }
}

@Composable
private fun LoadingExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Loading States",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        // Loading card
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = AppColorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                Text(
                    text = "Loading...",
                    style = Typography.body,
                    color = AppColorScheme.onSurfaceVariant
                )
            }
        }
        
        // Skeleton loading
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                // Skeleton for title
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(20.dp)
                        .background(
                            color = AppColorScheme.neutral200,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                        )
                )
                
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                // Skeleton for content
                repeat(3) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(
                                color = AppColorScheme.neutral200,
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(4.dp)
                            )
                    )
                    Spacer(modifier = Modifier.height(Spacing.xs))
                }
            }
        }
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
    FORM_SECTION(
        title = "Form Sections",
        description = "Grouped form fields with section headers"
    ),
    LOADING(
        title = "Loading States",
        description = "Components for showing loading and skeleton states"
    )
}
