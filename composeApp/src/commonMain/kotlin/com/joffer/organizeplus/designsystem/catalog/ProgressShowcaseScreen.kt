package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Progress Indicators",
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
                    text = "Progress Indicators",
                    style = Typography.h1,
                    color = AppColorScheme.onSurface
                )
            }
            
            item {
                Text(
                    text = "Different types of progress indicators for various use cases",
                    style = Typography.body,
                    color = AppColorScheme.onSurfaceVariant
                )
            }
            
            // Basic Progress Indicator
            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md)
                    ) {
                        Text(
                            text = "Basic Progress Indicator",
                            style = Typography.titleMedium,
                            color = AppColorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        
                        Text(
                            text = "Standard progress indicator with customizable size and color",
                            style = Typography.body,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.md))
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            OrganizeProgressIndicator()
                            
                            Text(
                                text = "Default (40dp, Primary color)",
                                style = Typography.caption,
                                color = AppColorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
            
            // Full Screen Progress
            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md)
                    ) {
                        Text(
                            text = "Full Screen Progress",
                            style = Typography.titleMedium,
                            color = AppColorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        
                        Text(
                            text = "Centered progress indicator for loading states",
                            style = Typography.body,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.md))
                        
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(Spacing.xxl * 5) // 120.dp equivalent
                        ) {
                            OrganizeProgressIndicatorFullScreen()
                        }
                    }
                }
            }
            
            // Inline Progress
            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md)
                    ) {
                        Text(
                            text = "Inline Progress",
                            style = Typography.titleMedium,
                            color = AppColorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.xs))
                        
                        Text(
                            text = "Small progress indicator for buttons and compact spaces",
                            style = Typography.body,
                            color = AppColorScheme.onSurfaceVariant
                        )
                        
                        Spacer(modifier = Modifier.height(Spacing.md))
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    OrganizeProgressIndicatorInline()
                                    Text(
                                        text = "Default",
                                        style = Typography.caption,
                                        color = AppColorScheme.onSurfaceVariant
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    OrganizeProgressIndicatorInline(
                                        size = Spacing.lg, // 16.dp equivalent
                                        color = AppColorScheme.onPrimary
                                    )
                                    Text(
                                        text = "16dp",
                                        style = Typography.caption,
                                        color = AppColorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

