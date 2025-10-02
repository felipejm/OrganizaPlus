package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = localTypography()
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
                    color = AppColorScheme.onSurface
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
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Basic Cards",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        OrganizeCard {
            Text(
                text = "Simple card with just text content",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurface,
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
                    color = AppColorScheme.onSurface
                )
                Text(
                    text = "This card has a title and description",
                    style = typography.bodyMedium,
                    color = AppColorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun InteractiveCardExamples() {
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Interactive Cards",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
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
                        color = AppColorScheme.onSurface
                    )
                    Text(
                        text = "Tap to interact",
                        style = typography.bodyMedium,
                        color = AppColorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Navigate forward",
                    tint = AppColorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun ContentCardExamples() {
    val typography = localTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.sm)
    ) {
        Text(
            text = "Content Cards",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        // Stats Card
        OrganizeCard {
            Column(
                modifier = Modifier.padding(Spacing.md)
            ) {
                Text(
                    text = "Statistics",
                    style = typography.titleSmall,
                    color = AppColorScheme.onSurfaceVariant
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
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = AppColorScheme.warning500,
                        modifier = Modifier.size(Spacing.iconSize)
                    )

                    Spacer(modifier = Modifier.width(Spacing.sm))

                    Text(
                        text = "Featured Item",
                        style = typography.titleMedium,
                        color = AppColorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(Spacing.sm))

                Text(
                    text = "This is a featured card with an icon and special styling to highlight important content.",
                    style = typography.bodyMedium,
                    color = AppColorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(Spacing.sm))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    PriorityChip(text = "HIGH", priority = ObligationPriority.HIGH)
                    CategoryChip(
                        category = "Important",
                        color = AppColorScheme.primary
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
    val typography = localTypography()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = typography.headlineLarge,
            color = AppColorScheme.primary
        )
        Text(
            text = label,
            style = typography.caption,
            color = AppColorScheme.onSurfaceVariant
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
