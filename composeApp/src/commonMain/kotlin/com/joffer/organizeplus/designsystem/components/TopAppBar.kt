package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    containerColor: androidx.compose.ui.graphics.Color = AppColorScheme.surface,
    titleContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formText,
    navigationIconContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formIcon,
    actionIconContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formIcon
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = Typography.titleMedium,
                color = titleContentColor,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = navigationIcon ?: {},
        actions = actions ?: {},
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleContentColor,
            navigationIconContentColor = navigationIconContentColor,
            actionIconContentColor = actionIconContentColor
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBarWithBackButton(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backIcon: ImageVector? = null,
    containerColor: androidx.compose.ui.graphics.Color = AppColorScheme.surface,
    titleContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formText,
    navigationIconContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formIcon,
    actionIconContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formIcon
) {
    AppTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = backIcon ?: Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = navigationIconContentColor,
                    modifier = Modifier.size(Spacing.iconSize)
                )
            }
        },
        actions = actions,
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        navigationIconContentColor = navigationIconContentColor,
        actionIconContentColor = actionIconContentColor
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBarWithActions(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    containerColor: androidx.compose.ui.graphics.Color = AppColorScheme.surface,
    titleContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formText,
    actionIconContentColor: androidx.compose.ui.graphics.Color = AppColorScheme.formIcon
) {
    AppTopAppBar(
        title = title,
        modifier = modifier,
        actions = actions,
        containerColor = containerColor,
        titleContentColor = titleContentColor,
        actionIconContentColor = actionIconContentColor
    )
}
