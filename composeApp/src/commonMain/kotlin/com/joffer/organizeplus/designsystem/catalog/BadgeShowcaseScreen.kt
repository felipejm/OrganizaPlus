package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Badge Component",
                onBackClick = onNavigateBack
            )
        }
    ) { paddingValues ->
        val typography = DesignSystemTypography()
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                Text(
                    text = "Badge Component",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
            }

            items(BadgeShowcaseItem.values()) { item ->
                BadgeShowcaseItem(item = item)
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = "Real Usage Examples",
                    style = typography.headlineMedium,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }

            item {
                BadgeUsageExample()
            }
        }
    }
}

@Composable
private fun BadgeShowcaseItem(
    item: BadgeShowcaseItem,
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
                color = AppColorScheme.onSurface
            )

            Text(
                text = item.description,
                style = typography.bodyMedium,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.md))

            when (item) {
                BadgeShowcaseItem.VARIANTS -> {
                    VariantExamples()
                }
                BadgeShowcaseItem.STATES -> {
                    StateExamples()
                }
                BadgeShowcaseItem.USAGE -> {
                    UsageExamples()
                }
                BadgeShowcaseItem.POSITIONING -> {
                    PositioningExamples()
                }
            }
        }
    }
}

@Composable
private fun VariantExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Badge Variants",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Single Digit Examples
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Single Digit",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    OrganizeBadge(
                        count = 1,
                        variant = BadgeVariant.SINGLE_DIGIT
                    )
                    OrganizeBadge(
                        count = 5,
                        variant = BadgeVariant.SINGLE_DIGIT
                    )
                    OrganizeBadge(
                        count = 9,
                        variant = BadgeVariant.SINGLE_DIGIT
                    )
                }
            }

            // Double Digit Examples
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Double Digit",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    OrganizeBadge(
                        count = 10,
                        variant = BadgeVariant.DOUBLE_DIGIT
                    )
                    OrganizeBadge(
                        count = 25,
                        variant = BadgeVariant.DOUBLE_DIGIT
                    )
                    OrganizeBadge(
                        count = 99,
                        variant = BadgeVariant.DOUBLE_DIGIT
                    )
                    OrganizeBadge(
                        count = 150,
                        variant = BadgeVariant.DOUBLE_DIGIT
                    )
                }
            }

            // Dot Examples
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Dot",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
                ) {
                    OrganizeBadge(
                        variant = BadgeVariant.DOT
                    )
                    OrganizeBadge(
                        variant = BadgeVariant.DOT
                    )
                    OrganizeBadge(
                        variant = BadgeVariant.DOT
                    )
                }
            }
        }
    }
}

@Composable
private fun StateExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Badge States",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Default State
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Default",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                OrganizeBadge(
                    count = 5,
                    variant = BadgeVariant.SINGLE_DIGIT,
                    state = BadgeState.DEFAULT
                )
            }

            // Hover State
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hover",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                OrganizeBadge(
                    count = 5,
                    variant = BadgeVariant.SINGLE_DIGIT,
                    state = BadgeState.HOVER
                )
            }

            // Pressed State
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Pressed",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                OrganizeBadge(
                    count = 5,
                    variant = BadgeVariant.SINGLE_DIGIT,
                    state = BadgeState.PRESSED
                )
            }

            // Disabled State
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Disabled",
                    style = typography.caption,
                    color = AppColorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(Spacing.xs))
                OrganizeBadge(
                    count = 5,
                    variant = BadgeVariant.SINGLE_DIGIT,
                    state = BadgeState.DISABLED,
                    enabled = false
                )
            }
        }
    }
}

@Composable
private fun UsageExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Common Usage Examples",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        // Notification Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(
                text = "Notification Badge:",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurface
            )

            Box {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = AppColorScheme.onSurface
                )
                NotificationBadge(
                    count = 3,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            Box {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Messages",
                    tint = AppColorScheme.onSurface
                )
                NotificationBadge(
                    count = 12,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }

        // Status Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(
                text = "Status Badge:",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurface
            )

            Box {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = AppColorScheme.onSurface
                )
                StatusBadge(
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }

        // Counter Badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            Text(
                text = "Counter Badge:",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurface
            )

            Box {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Cart",
                    tint = AppColorScheme.onSurface
                )
                CounterBadge(
                    count = 7,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }
    }
}

@Composable
private fun PositioningExamples() {
    val typography = DesignSystemTypography()
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Positioning Examples",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Text(
            text = "Badges are typically positioned in the top-right corner of their parent element with a 2px offset.",
            style = typography.bodyMedium,
            color = AppColorScheme.onSurfaceVariant
        )

        // Example with different sized containers
        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            // Small container
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = AppColorScheme.neutral200,
                        shape = CircleShape
                    )
            ) {
                OrganizeBadge(
                    count = 3,
                    variant = BadgeVariant.SINGLE_DIGIT,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            // Medium container
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = AppColorScheme.neutral200,
                        shape = CircleShape
                    )
            ) {
                OrganizeBadge(
                    count = 15,
                    variant = BadgeVariant.DOUBLE_DIGIT,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            // Large container
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(
                        color = AppColorScheme.neutral200,
                        shape = CircleShape
                    )
            ) {
                OrganizeBadge(
                    count = 99,
                    variant = BadgeVariant.DOUBLE_DIGIT,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = "Dot badges work well for status indicators:",
            style = typography.bodyMedium,
            color = AppColorScheme.onSurfaceVariant
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = AppColorScheme.neutral200,
                        shape = CircleShape
                    )
            ) {
                StatusBadge(
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = AppColorScheme.neutral200,
                        shape = CircleShape
                    )
            ) {
                StatusBadge(
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }
    }
}

enum class BadgeShowcaseItem(
    val title: String,
    val description: String
) {
    VARIANTS(
        title = "Badge Variants",
        description = "Single digit, double digit, and dot variants"
    ),
    STATES(
        title = "Badge States",
        description = "Default, hover, pressed, and disabled states"
    ),
    USAGE(
        title = "Common Usage",
        description = "Notification, status, and counter badges"
    ),
    POSITIONING(
        title = "Positioning",
        description = "How badges are positioned relative to their parent elements"
    )
}
