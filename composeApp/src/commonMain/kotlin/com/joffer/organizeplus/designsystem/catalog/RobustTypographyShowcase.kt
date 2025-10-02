package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.*
import com.joffer.organizeplus.designsystem.typography.Typography

/**
 * Showcase screen for the robust typography system.
 * This demonstrates all available typography styles with SF Pro Text fonts.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RobustTypographyShowcase(
    onNavigateBack: () -> Unit
) {
    ProvideSfProTypography {
        val typography = DesignSystemTypography()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Typography System",
                            style = typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        TextButton(onClick = onNavigateBack) {
                            Text(
                                text = "← Back",
                                style = typography.labelLarge
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.lg)
            ) {
                // Font Family Info
                item {
                    TypographySection(
                        title = "Font Family",
                        typography = typography
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = SemanticColors.Background.brandContainer
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(Spacing.md)
                            ) {
                                Text(
                                    text = "SF Pro Text",
                                    style = typography.titleMedium,
                                    color = SemanticColors.OnBackground.onBrandContainer
                                )
                                Spacer(modifier = Modifier.height(Spacing.sm))
                                Text(
                                    text = "A comprehensive font family with Regular, Medium, Semibold, and Bold weights",
                                    style = typography.bodyMedium,
                                    color = SemanticColors.OnBackground.onBrandContainer
                                )
                            }
                        }
                    }
                }

                // Display Typography
                item {
                    TypographySection(
                        title = "Display Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Display Large",
                            style = typography.displayLarge,
                            sampleText = "Display Large Text"
                        )
                        TypographyExample(
                            label = "Display Medium",
                            style = typography.displayMedium,
                            sampleText = "Display Medium Text"
                        )
                        TypographyExample(
                            label = "Display Small",
                            style = typography.displaySmall,
                            sampleText = "Display Small Text"
                        )
                    }
                }

                // Headline Typography
                item {
                    TypographySection(
                        title = "Headline Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Headline Large",
                            style = typography.headlineLarge,
                            sampleText = "Headline Large Text"
                        )
                        TypographyExample(
                            label = "Headline Medium",
                            style = typography.headlineMedium,
                            sampleText = "Headline Medium Text"
                        )
                        TypographyExample(
                            label = "Headline Small",
                            style = typography.headlineSmall,
                            sampleText = "Headline Small Text"
                        )
                    }
                }

                // Title Typography
                item {
                    TypographySection(
                        title = "Title Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Title Large",
                            style = typography.titleLarge,
                            sampleText = "Title Large Text"
                        )
                        TypographyExample(
                            label = "Title Medium",
                            style = typography.titleMedium,
                            sampleText = "Title Medium Text"
                        )
                        TypographyExample(
                            label = "Title Small",
                            style = typography.titleSmall,
                            sampleText = "Title Small Text"
                        )
                    }
                }

                // Body Typography
                item {
                    TypographySection(
                        title = "Body Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Body Large",
                            style = typography.bodyLarge,
                            sampleText = "Body Large Text - This is used for main content and readable text."
                        )
                        TypographyExample(
                            label = "Body Medium",
                            style = typography.bodyMedium,
                            sampleText = "Body Medium Text - This is the standard body text size."
                        )
                        TypographyExample(
                            label = "Body Small",
                            style = typography.bodySmall,
                            sampleText = "Body Small Text - Used for secondary information."
                        )
                    }
                }

                // Label Typography
                item {
                    TypographySection(
                        title = "Label Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Label Large",
                            style = typography.labelLarge,
                            sampleText = "Label Large"
                        )
                        TypographyExample(
                            label = "Label Medium",
                            style = typography.labelMedium,
                            sampleText = "Label Medium"
                        )
                        TypographyExample(
                            label = "Label Small",
                            style = typography.labelSmall,
                            sampleText = "Label Small"
                        )
                    }
                }

                // Semantic Typography
                item {
                    TypographySection(
                        title = "Semantic Typography",
                        typography = typography
                    ) {
                        TypographyExample(
                            label = "Button",
                            style = typography.button,
                            sampleText = "Button Text"
                        )
                        TypographyExample(
                            label = "Caption",
                            style = typography.caption,
                            sampleText = "Caption Text"
                        )
                        TypographyExample(
                            label = "Overline",
                            style = typography.overline,
                            sampleText = "OVERLINE TEXT"
                        )
                    }
                }

                // Font Weight Examples
                item {
                    TypographySection(
                        title = "Font Weight Examples",
                        typography = typography
                    ) {
                        FontWeightExample(
                            text = "SF Pro Text Regular",
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Normal)
                        )
                        FontWeightExample(
                            text = "SF Pro Text Medium",
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
                        )
                        FontWeightExample(
                            text = "SF Pro Text Semibold",
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                        FontWeightExample(
                            text = "SF Pro Text Bold",
                            style = typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TypographySection(
    title: String,
    typography: Typography,
    content: @Composable () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = title,
            style = typography.titleLarge,
            color = SemanticColors.Foreground.primary
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = SemanticColors.Background.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(Spacing.md),
                verticalArrangement = Arrangement.spacedBy(Spacing.sm)
            ) {
                content()
            }
        }
    }
}

@Composable
private fun TypographyExample(
    label: String,
    style: androidx.compose.ui.text.TextStyle,
    sampleText: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Text(
            text = label,
            style = style.copy(fontSize = 12.sp),
            color = SemanticColors.Foreground.secondary
        )
        Text(
            text = sampleText,
            style = style,
            color = SemanticColors.Foreground.primary
        )
        Text(
            text = "${style.fontSize.value.toInt()}sp / ${style.lineHeight.value.toInt()}sp / ${style.fontWeight}",
            style = style.copy(fontSize = 10.sp),
            color = SemanticColors.Border.outline
        )
    }
}

@Composable
private fun FontWeightExample(
    text: String,
    style: androidx.compose.ui.text.TextStyle
) {
    Text(
        text = text,
        style = style,
        color = SemanticColors.Foreground.primary
    )
}
