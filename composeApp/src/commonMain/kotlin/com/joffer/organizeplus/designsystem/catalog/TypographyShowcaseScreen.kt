package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypographyShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Typography",
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
            items(TypographyCategory.values()) { category ->
                TypographyCategorySection(category = category)
            }
        }
    }
}

@Composable
private fun TypographyCategorySection(
    category: TypographyCategory,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = category.title,
                style = typography.headlineMedium,
                color = SemanticColors.Foreground.primary
            )

            Text(
                text = category.description,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (category) {
                TypographyCategory.HEADINGS -> {
                    HeadingExamples()
                }
                TypographyCategory.BODY_TEXT -> {
                    BodyTextExamples()
                }
                TypographyCategory.LABELS -> {
                    LabelExamples()
                }
                TypographyCategory.FONT_WEIGHTS -> {
                    FontWeightExamples()
                }
            }
        }
    }
}

@Composable
private fun HeadingExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Heading Styles",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        TypographyExample(
            name = "H1",
            style = typography.displayLarge,
            text = "Main Page Heading",
            description = "30px / 38px line height"
        )

        TypographyExample(
            name = "H2",
            style = typography.headlineLarge,
            text = "Section Heading",
            description = "24px / 32px line height"
        )

        TypographyExample(
            name = "H3",
            style = typography.headlineMedium,
            text = "Subsection Heading",
            description = "20px / 28px line height"
        )

        TypographyExample(
            name = "Title",
            style = typography.titleMedium,
            text = "Card or Component Title",
            description = "18px / 26px line height"
        )
    }
}

@Composable
private fun BodyTextExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Body Text Styles",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        TypographyExample(
            name = "Subtitle",
            style = typography.titleSmall,
            text = "Supporting text for headings",
            description = "16px / 24px line height"
        )

        TypographyExample(
            name = "Body",
            style = typography.bodyMedium,
            text = "Regular paragraph text for content",
            description = "14px / 22px line height"
        )

        TypographyExample(
            name = "Caption",
            style = typography.caption,
            text = "Small text for captions and metadata",
            description = "12px / 19px line height"
        )
    }
}

@Composable
private fun LabelExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Label Styles",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        TypographyExample(
            name = "Label Large",
            style = typography.labelLarge,
            text = "Button Labels",
            description = "16px / 22px line height"
        )

        TypographyExample(
            name = "Label Medium",
            style = typography.labelMedium,
            text = "Form Labels",
            description = "14px / 20px line height"
        )

        TypographyExample(
            name = "Label Small",
            style = typography.labelSmall,
            text = "Small Labels",
            description = "12px / 18px line height"
        )
    }
}

@Composable
private fun FontWeightExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Font Weight Variants",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        // Regular (400) examples
        Text(
            text = "Regular (400)",
            style = typography.caption,
            color = SemanticColors.Foreground.secondary,
            fontWeight = FontWeight.Medium
        )

        TypographyExample(
            name = "H1 Regular",
            style = typography.displayLarge,
            text = "Regular Heading",
            description = "30px / 38px"
        )

        TypographyExample(
            name = "Body Regular",
            style = typography.bodyMedium,
            text = "Regular body text",
            description = "14px / 22px"
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        // Medium (500) examples
        Text(
            text = "Medium (500)",
            style = typography.caption,
            color = SemanticColors.Foreground.secondary,
            fontWeight = FontWeight.Medium
        )

        TypographyExample(
            name = "H1 Medium",
            style = typography.displayLarge,
            text = "Medium Heading",
            description = "30px / 38px"
        )

        TypographyExample(
            name = "Body Medium",
            style = typography.bodyMedium,
            text = "Medium body text",
            description = "14px / 22px"
        )
    }
}

@Composable
private fun TypographyExample(
    name: String,
    style: androidx.compose.ui.text.TextStyle,
    text: String,
    description: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Spacing.xs)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = typography.caption,
                color = SemanticColors.Foreground.secondary,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.width(120.dp)
            )

            Text(
                text = description,
                style = typography.caption,
                color = SemanticColors.Foreground.secondary,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = text,
            style = style,
            color = SemanticColors.Foreground.primary
        )
    }
}

enum class TypographyCategory(
    val title: String,
    val description: String
) {
    HEADINGS(
        title = "Headings",
        description = "Hierarchical text styles for titles and headings"
    ),
    BODY_TEXT(
        title = "Body Text",
        description = "Text styles for content and paragraphs"
    ),
    LABELS(
        title = "Labels",
        description = "Text styles for UI labels and controls"
    ),
    FONT_WEIGHTS(
        title = "Font Weights",
        description = "Different font weight variations"
    )
}
