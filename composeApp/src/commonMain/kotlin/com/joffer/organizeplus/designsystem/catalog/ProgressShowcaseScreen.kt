package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
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
                    style = typography.displayLarge,
                    color = SemanticColors.Foreground.primary
                )
            }

            item {
                Text(
                    text = "Different types of progress indicators for various use cases",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
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
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(Spacing.xs))

                        Text(
                            text = "Standard progress indicator with customizable size and color",
                            style = typography.bodyMedium,
                            color = SemanticColors.Foreground.secondary
                        )

                        Spacer(modifier = Modifier.height(Spacing.md))

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(Spacing.md)
                        ) {
                            OrganizeProgressIndicator()

                            Text(
                                text = "Default (40dp, Primary color)",
                                style = typography.caption,
                                color = SemanticColors.Foreground.secondary
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
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(Spacing.xs))

                        Text(
                            text = "Centered progress indicator for loading states",
                            style = typography.bodyMedium,
                            color = SemanticColors.Foreground.secondary
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
                            style = typography.titleMedium,
                            color = SemanticColors.Foreground.primary,
                            fontWeight = FontWeight.SemiBold
                        )

                        Spacer(modifier = Modifier.height(Spacing.xs))

                        Text(
                            text = "Small progress indicator for buttons and compact spaces",
                            style = typography.bodyMedium,
                            color = SemanticColors.Foreground.secondary
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
                                        style = typography.caption,
                                        color = SemanticColors.Foreground.secondary
                                    )
                                }
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    OrganizeProgressIndicatorInline(
                                        size = Spacing.lg, // 16.dp equivalent
                                        color = SemanticColors.OnBackground.onBrand
                                    )
                                    Text(
                                        text = "16dp",
                                        style = typography.caption,
                                        color = SemanticColors.Foreground.secondary
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
