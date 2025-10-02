package com.joffer.organizeplus.designsystem.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.localTypography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
    title: String?,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    containerColor: Color = AppColorScheme.surface,
    titleContentColor: Color = AppColorScheme.formText,
    navigationIconContentColor: Color = AppColorScheme.formIcon,
    actionIconContentColor: Color = AppColorScheme.formIcon
) {
    val typography = localTypography()
    TopAppBar(
        title = {
            title?.let {
                Text(
                    text = title,
                    style = typography.titleMedium,
                    color = titleContentColor,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
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
    title: String? = null,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    backIcon: ImageVector? = null,
    containerColor: Color = AppColorScheme.background,
    titleContentColor: Color = AppColorScheme.formText,
    navigationIconContentColor: Color = AppColorScheme.formIcon,
    actionIconContentColor: Color = AppColorScheme.formIcon
) {
    AppTopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = backIcon ?: Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = navigationIconContentColor,
                    modifier = Modifier.size(Spacing.Icon.xs)
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
    title: String? = null,
    modifier: Modifier = Modifier,
    actions: @Composable (RowScope.() -> Unit)? = null,
    containerColor: Color = AppColorScheme.background,
    titleContentColor: Color = AppColorScheme.formText,
    actionIconContentColor: Color = AppColorScheme.formIcon
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
