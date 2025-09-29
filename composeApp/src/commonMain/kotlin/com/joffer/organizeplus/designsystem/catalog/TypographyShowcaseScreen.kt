package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TypographyShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
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
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = category.title,
                style = Typography.h3,
                color = AppColorScheme.onSurface
            )
            
            Text(
                text = category.description,
                style = Typography.body,
                color = AppColorScheme.onSurfaceVariant
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
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Heading Styles",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        TypographyExample(
            name = "H1",
            style = Typography.h1,
            text = "Main Page Heading",
            description = "30px / 38px line height"
        )
        
        TypographyExample(
            name = "H2",
            style = Typography.h2,
            text = "Section Heading",
            description = "24px / 32px line height"
        )
        
        TypographyExample(
            name = "H3",
            style = Typography.h3,
            text = "Subsection Heading",
            description = "20px / 28px line height"
        )
        
        TypographyExample(
            name = "Title",
            style = Typography.title,
            text = "Card or Component Title",
            description = "18px / 26px line height"
        )
    }
}

@Composable
private fun BodyTextExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Body Text Styles",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        TypographyExample(
            name = "Subtitle",
            style = Typography.subtitle,
            text = "Supporting text for headings",
            description = "16px / 24px line height"
        )
        
        TypographyExample(
            name = "Body",
            style = Typography.body,
            text = "Regular paragraph text for content",
            description = "14px / 22px line height"
        )
        
        TypographyExample(
            name = "Caption",
            style = Typography.caption,
            text = "Small text for captions and metadata",
            description = "12px / 19px line height"
        )
    }
}

@Composable
private fun LabelExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Label Styles",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        TypographyExample(
            name = "Label Large",
            style = Typography.labelLarge,
            text = "Button Labels",
            description = "16px / 22px line height"
        )
        
        TypographyExample(
            name = "Label Medium",
            style = Typography.labelMedium,
            text = "Form Labels",
            description = "14px / 20px line height"
        )
        
        TypographyExample(
            name = "Label Small",
            style = Typography.labelSmall,
            text = "Small Labels",
            description = "12px / 18px line height"
        )
    }
}

@Composable
private fun FontWeightExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Font Weight Variants",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        // Regular (400) examples
        Text(
            text = "Regular (400)",
            style = Typography.caption,
            color = AppColorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        
        TypographyExample(
            name = "H1 Regular",
            style = Typography.h1Regular,
            text = "Regular Heading",
            description = "30px / 38px"
        )
        
        TypographyExample(
            name = "Body Regular",
            style = Typography.bodyRegular,
            text = "Regular body text",
            description = "14px / 22px"
        )
        
        Spacer(modifier = Modifier.height(Spacing.sm))
        
        // Medium (500) examples
        Text(
            text = "Medium (500)",
            style = Typography.caption,
            color = AppColorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium
        )
        
        TypographyExample(
            name = "H1 Medium",
            style = Typography.h1Medium,
            text = "Medium Heading",
            description = "30px / 38px"
        )
        
        TypographyExample(
            name = "Body Medium",
            style = Typography.bodyMedium,
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
                style = Typography.caption,
                color = AppColorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.width(120.dp)
            )
            
            Text(
                text = description,
                style = Typography.caption,
                color = AppColorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
        }
        
        Text(
            text = text,
            style = style,
            color = AppColorScheme.onSurface
        )
    }
}

@Composable
private fun TypographyUsageExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.lg)
    ) {
        Text(
            text = "Usage Examples",
            style = Typography.h3,
            color = AppColorScheme.onSurface
        )
        
        // Card example
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                Text(
                    text = "Card Title",
                    style = Typography.title,
                    color = AppColorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(Spacing.xs))
                
                Text(
                    text = "This is a subtitle that provides additional context for the card content.",
                    style = Typography.subtitle,
                    color = AppColorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                Text(
                    text = "This is the main body text that contains the primary information. It should be easy to read and provide clear details about the content.",
                    style = Typography.body,
                    color = AppColorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(Spacing.sm))
                
                Text(
                    text = "Last updated 2 hours ago",
                    style = Typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
            }
        }
        
        // Button example
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColorScheme.primary
                )
            ) {
                Text(
                    text = "Primary Action",
                    style = Typography.labelLarge,
                    color = AppColorScheme.onPrimary
                )
            }
            
            OutlinedButton(
                onClick = { },
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = AppColorScheme.primary
                )
            ) {
                Text(
                    text = "Secondary",
                    style = Typography.labelLarge
                )
            }
        }
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
