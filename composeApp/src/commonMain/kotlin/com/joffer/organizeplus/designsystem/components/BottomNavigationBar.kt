package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

/**
 * Data class representing a bottom navigation item
 *
 * @param label The text label for the navigation item
 * @param icon The icon to display (used when unselected)
 * @param selectedIcon Optional alternative icon for selected state
 * @param route The navigation route associated with this item
 */
data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val route: Any
)

/**
 * OrganizePlus bottom navigation bar component with design system styling
 *
 * Features:
 * - Consistent semantic colors from design system
 * - Proper typography and spacing
 * - Selected/unselected icon states
 * - Brand color highlighting for active items
 * - Accessibility support
 *
 * @param items List of navigation items to display
 * @param currentRoute The currently active route for selection state
 * @param onItemClick Callback when a navigation item is clicked
 * @param modifier Optional modifier for customization
 */
@Composable
fun OrganizeBottomNavigationBar(
    items: List<BottomNavItem>,
    currentRoute: Any?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(Spacing.NavigationBar.height),
        containerColor = SemanticColors.Background.surface,
        contentColor = SemanticColors.Background.surface,
        tonalElevation = Spacing.Elevation.md
    ) {
        items.forEach { item ->
            OrganizeBottomNavigationBarItem(
                item = item,
                isSelected = currentRoute == item.route,
                onClick = { onItemClick(item) }
            )
        }
    }
}

/**
 * Individual navigation bar item with design system styling
 */
@Composable
private fun RowScope.OrganizeBottomNavigationBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val typography = DesignSystemTypography()
    
    NavigationBarItem(
        selected = isSelected,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = if (isSelected) {
                    item.selectedIcon ?: item.icon
                } else {
                    item.icon
                },
                contentDescription = item.label,
                modifier = Modifier.size(Spacing.NavigationBar.iconSize)
            )
        },
        label = {
            Text(
                text = item.label,
                style = typography.labelSmall.copy(
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Medium
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = SemanticColors.Foreground.brand,
            selectedTextColor = SemanticColors.Foreground.brand,
            unselectedIconColor = SemanticColors.Foreground.secondary,
            unselectedTextColor = SemanticColors.Foreground.secondary,
            indicatorColor = SemanticColors.Background.selected
        ),
        alwaysShowLabel = true
    )
}

