package com.joffer.organizeplus.designsystem.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector? = null,
    val route: Any
)

@Composable
fun OrganizeBottomNavigationBar(
    items: List<BottomNavItem>,
    currentRoute: Any?,
    onItemClick: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier,
        containerColor = SemanticColors.Background.surface, // Dark surface background
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

@Composable
private fun RowScope.OrganizeBottomNavigationBarItem(
    item: BottomNavItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val typography = DesignSystemTypography()

    // Animate icon scale with spring physics for natural bounce
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1.3f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "icon_scale"
    )

    // Animate text alpha for smooth fade in/out
    val textAlpha by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.7f,
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "text_alpha"
    )

    // Animate icon color transition (matching image)
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) {
            SemanticColors.Legacy.personalAccent // Blue for selected
        } else {
            SemanticColors.Foreground.secondary // Light grey for unselected
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "icon_color"
    )

    // Animate text color transition (matching image)
    val textColor by animateColorAsState(
        targetValue = if (isSelected) {
            SemanticColors.Legacy.personalAccent // Blue for selected
        } else {
            SemanticColors.Foreground.secondary // Light grey for unselected
        },
        animationSpec = tween(
            durationMillis = 200,
            easing = FastOutSlowInEasing
        ),
        label = "text_color"
    )

    // Subtle rotation for playful effect
    val iconRotation by animateFloatAsState(
        targetValue = if (isSelected) 0f else 0f, // Can be adjusted for rotation effect
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "icon_rotation"
    )

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
                tint = iconColor,
                modifier = Modifier
                    .size(Spacing.NavigationBar.iconSize)
                    .graphicsLayer {
                        scaleX = iconScale
                        scaleY = iconScale
                        rotationZ = iconRotation
                    }
            )
        },
        label = {
            Text(
                text = item.label,
                style = typography.bodySmall.copy( // 12sp matching image
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                ),
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.graphicsLayer {
                    alpha = textAlpha
                }
            )
        },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = iconColor,
            selectedTextColor = textColor,
            unselectedIconColor = iconColor,
            unselectedTextColor = textColor,
            indicatorColor = SemanticColors.Background.selected
        ),
        alwaysShowLabel = true
    )
}
