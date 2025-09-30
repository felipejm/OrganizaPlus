package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.common.constants.CategoryConstants
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.spacing.Spacing
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.ic_company
import organizeplus.composeapp.generated.resources.ic_personal

/**
 * A circular icon component for displaying category icons
 * 
 * @param categoryName The name of the category to display
 * @param size The size of the circular container (default: design system icon size)
 * @param iconSize The size of the icon inside the container (default: design system icon size)
 * @param modifier Modifier for the component
 */
@Composable
fun CategoryIcon(
    categoryName: String,
    size: Dp = Spacing.iconSize,
    iconSize: Dp = Spacing.iconSize * 0.6f,
    modifier: Modifier = Modifier
) {
    val iconColor = when (categoryName) {
        CategoryConstants.COMPANY -> AppColorScheme.iconOrange
        else -> AppColorScheme.iconBlue
    }
    
    val iconContainerColor = when (categoryName) {
        CategoryConstants.COMPANY -> AppColorScheme.iconOrangeContainer
        else -> AppColorScheme.iconBlueContainer
    }
    
    val iconResource = when (categoryName) {
        CategoryConstants.PERSONAL -> Res.drawable.ic_personal
        CategoryConstants.COMPANY -> Res.drawable.ic_company
        else -> Res.drawable.ic_personal // Default to personal icon
    }
    
    Box(
        modifier = modifier
            .size(size)
            .background(
                color = iconContainerColor,
                shape = CircleShape
            ).padding(Spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = categoryName,
            tint = iconColor,
            modifier = Modifier.size(iconSize)
        )
    }
}
