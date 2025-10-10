package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Cards",
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
                    text = "Card Components",
                    style = typography.headlineMedium,
                    color = SemanticColors.Foreground.primary
                )
            }

            items(CardShowcaseItem.values()) { item ->
                CardShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun CardShowcaseItem(
    item: CardShowcaseItem,
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
                text = item.title,
                style = typography.titleMedium,
                color = SemanticColors.Foreground.primary
            )

            Text(
                text = item.description,
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.secondary
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (item) {
                CardShowcaseItem.BASIC -> {
                    BasicCardExamples()
                }

                CardShowcaseItem.INTERACTIVE -> {
                    InteractiveCardExamples()
                }

                CardShowcaseItem.CONTENT -> {
                    ContentCardExamples()
                }
            }
        }
    }
}

@Composable
private fun BasicCardExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Basic Cards",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        OrganizeCard {
            Text(
                text = "Simple card with just text content",
                style = typography.bodyMedium,
                color = SemanticColors.Foreground.primary,
                modifier = Modifier.padding(Spacing.md)
            )
        }

        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                Text(
                    text = "Card with Title",
                    style = typography.titleMedium,
                    color = SemanticColors.Foreground.primary
                )
                Text(
                    text = "This card has a title and description",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }
        }
    }
}

@Composable
private fun InteractiveCardExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Interactive Cards",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        OrganizeCard(
            onClick = { /* Handle click */ }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Clickable Card",
                        style = typography.titleMedium,
                        color = SemanticColors.Foreground.primary
                    )
                    Text(
                        text = "Tap to interact",
                        style = typography.bodyMedium,
                        color = SemanticColors.Foreground.secondary
                    )
                }
                Icon(
                    imageVector = OrganizeIcons.System.ArrowBackIos,
                    contentDescription = "Navigate forward",
                    tint = SemanticColors.Foreground.brand
                )
            }
        }
    }
}

@Composable
private fun ContentCardExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Content Cards",
            style = typography.titleSmall,
            color = SemanticColors.Foreground.primary
        )

        // Stats Card
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                Text(
                    text = "Statistics",
                    style = typography.titleSmall,
                    color = SemanticColors.Foreground.secondary
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
                ) {
                    StatItem(
                        value = "24",
                        label = "Completed"
                    )
                    StatItem(
                        value = "8",
                        label = "Pending"
                    )
                    StatItem(
                        value = "2",
                        label = "Overdue"
                    )
                }
            }
        }

        // Feature Card
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = OrganizeIcons.System.StarFilled,
                        contentDescription = null,
                        tint = SemanticColors.Foreground.warning,
                        modifier = Modifier.size(Spacing.iconSize)
                    )

                    Spacer(modifier = Modifier.width(Spacing.sm))

                    Text(
                        text = "Featured Item",
                        style = typography.titleMedium,
                        color = SemanticColors.Foreground.primary
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.sm))

                Text(
                    text = "This is a featured card with an icon and special styling to highlight important content.",
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.primary
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    PriorityChip(text = "HIGH", priority = ObligationPriority.HIGH)
                    CategoryChip(
                        category = "Important",
                        color = SemanticColors.Foreground.brand
                    )
                }
            }
        }
    }
}

@Composable
private fun StatItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = typography.headlineLarge,
            color = SemanticColors.Foreground.brand
        )
        Text(
            text = label,
            style = typography.caption,
            color = SemanticColors.Foreground.secondary
        )
    }
}

enum class CardShowcaseItem(
    val title: String,
    val description: String
) {
    BASIC(
        title = "Basic Cards",
        description = "Simple cards for displaying content"
    ),
    INTERACTIVE(
        title = "Interactive Cards",
        description = "Clickable cards with hover and tap states"
    ),
    CONTENT(
        title = "Content Cards",
        description = "Complex cards with multiple content types"
    )
}
