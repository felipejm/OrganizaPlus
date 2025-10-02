package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.spacing.Spacing
import org.jetbrains.compose.resources.painterResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.ic_company
import organizeplus.composeapp.generated.resources.ic_personal
import com.joffer.organizeplus.designsystem.colors.SemanticColors

private const val CATEGORY_ICON_SIZE_RATIO = 0.6f

/**
 * A circular icon component for displaying category icons
 * Used consistently across dashboard, duty list, and duty review components
 *
 * @param categoryName The name of the category to display
 * @param size The size of the circular container (default: design system icon size)
 * @param iconSize The size of the icon inside the container (default: 60% of container size)
 * @param modifier Modifier for the component
 */
@Composable
fun CategoryIcon(
    categoryName: String,
    size: Dp = Spacing.iconSize,
    iconSize: Dp = size * CATEGORY_ICON_SIZE_RATIO,
    modifier: Modifier = Modifier
) {
    val iconColor = when (categoryName) {
        CategoryConstants.COMPANY -> SemanticColors.Legacy.iconOrange
        else -> SemanticColors.Legacy.iconBlue
    }

    val iconContainerColor = when (categoryName) {
        CategoryConstants.COMPANY -> SemanticColors.Legacy.iconOrangeContainer
        else -> SemanticColors.Legacy.iconBlueContainer
    }

    val iconResource = when (categoryName) {
        CategoryConstants.COMPANY -> Icons.Default.Home
        else -> Icons.Default.Person
    }

    Box(
        modifier = modifier
            .size(size)
            .background(
                color = iconContainerColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = iconResource,
            contentDescription = categoryName,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }
}

/**
 * Predefined size variants for CategoryIcon to ensure consistency across components
 */
object CategoryIconSize {
    val Small = Spacing.iconSize
    val Medium = Spacing.iconSize * 1.2f
    val Large = Spacing.iconSize * 1.5f
}
