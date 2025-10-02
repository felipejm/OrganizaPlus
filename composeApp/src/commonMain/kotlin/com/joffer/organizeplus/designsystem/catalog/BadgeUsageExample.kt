package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@Composable
fun BadgeUsageExample() {
    val typography = DesignSystemTypography()
    OrganizeCard {
        Column(
            modifier = Modifier.padding(Spacing.lg)
        ) {
            Text(
                text = "Badge Usage Examples",
                style = typography.headlineMedium,
                color = AppColorScheme.onSurface
            )

            Text(
                text = "Real-world examples of badges in different UI contexts",
                style = typography.bodyMedium,
                color = AppColorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Navigation Bar Example
            NavigationBarExample()

            Spacer(modifier = Modifier.height(Spacing.lg))

            // List Items Example
            ListItemsExample()

            Spacer(modifier = Modifier.height(Spacing.lg))

            // Action Buttons Example
            ActionButtonsExample()
        }
    }
}

@Composable
private fun NavigationBarExample() {
    val typography = DesignSystemTypography()
    Column {
        Text(
            text = "Navigation Bar",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = AppColorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(Spacing.md),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationItem(
                icon = Icons.Default.Home,
                label = "Home",
                badgeCount = null
            )

            NavigationItem(
                icon = Icons.Default.Notifications,
                label = "Notifications",
                badgeCount = 5
            )

            NavigationItem(
                icon = Icons.Default.Email,
                label = "Messages",
                badgeCount = 12
            )

            NavigationItem(
                icon = Icons.Default.ShoppingCart,
                label = "Cart",
                badgeCount = 3
            )

            NavigationItem(
                icon = Icons.Default.Person,
                label = "Profile",
                badgeCount = null,
                hasStatus = true
            )
        }
    }
}

@Composable
private fun NavigationItem(
    icon: ImageVector,
    label: String,
    badgeCount: Int?,
    hasStatus: Boolean = false
) {
    val typography = DesignSystemTypography()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = AppColorScheme.onSurface,
                modifier = Modifier.size(24.dp)
            )

            when {
                badgeCount != null && badgeCount > 0 -> {
                    NotificationBadge(
                        count = badgeCount,
                        modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                    )
                }
                hasStatus -> {
                    StatusBadge(
                        modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(Spacing.xs))

        Text(
            text = label,
            style = typography.caption,
            color = AppColorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ListItemsExample() {
    val typography = DesignSystemTypography()
    Column {
        Text(
            text = "List Items",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Column(
            verticalArrangement = Arrangement.spacedBy(Spacing.sm)
        ) {
            ListItem(
                icon = Icons.Default.Email,
                title = "Inbox",
                subtitle = "5 unread messages",
                badgeCount = 5
            )

            ListItem(
                icon = Icons.Default.Star,
                title = "Favorites",
                subtitle = "12 saved items",
                badgeCount = 12
            )

            ListItem(
                icon = Icons.Default.Settings, // Using a different icon that exists
                title = "Scheduled",
                subtitle = "3 pending tasks",
                badgeCount = 3
            )

            ListItem(
                icon = Icons.Default.Settings,
                title = "Settings",
                subtitle = "No updates available",
                badgeCount = null,
                hasStatus = true
            )
        }
    }
}

@Composable
private fun ListItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    badgeCount: Int?,
    hasStatus: Boolean = false
) {
    val typography = DesignSystemTypography()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
            .padding(Spacing.sm),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = AppColorScheme.onSurface,
                modifier = Modifier.size(20.dp)
            )

            when {
                badgeCount != null && badgeCount > 0 -> {
                    NotificationBadge(
                        count = badgeCount,
                        modifier = Modifier.offset(x = 6.dp, y = (-6).dp)
                    )
                }
                hasStatus -> {
                    StatusBadge(
                        modifier = Modifier.offset(x = 6.dp, y = (-6).dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(Spacing.sm))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = typography.bodyMedium,
                color = AppColorScheme.onSurface
            )
            Text(
                text = subtitle,
                style = typography.caption,
                color = AppColorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ActionButtonsExample() {
    val typography = DesignSystemTypography()
    Column {
        Text(
            text = "Action Buttons",
            style = typography.titleSmall,
            color = AppColorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Row(
            horizontalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            // FAB with badge
            Box {
                FloatingActionButton(
                    onClick = { },
                    containerColor = AppColorScheme.primary,
                    contentColor = AppColorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add"
                    )
                }
                NotificationBadge(
                    count = 2,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            // Icon button with badge
            Box {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorites",
                        tint = AppColorScheme.onSurface
                    )
                }
                CounterBadge(
                    count = 7,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }

            // Regular button with badge
            Box {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColorScheme.secondary
                    )
                ) {
                    Color.White
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(Spacing.xs))
                    Text("Cart")
                }
                NotificationBadge(
                    count = 4,
                    modifier = Modifier.offset(x = 8.dp, y = (-8).dp)
                )
            }
        }
    }
}
