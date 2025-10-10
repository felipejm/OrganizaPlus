package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import com.joffer.organizeplus.designsystem.icons.OrganizeIcons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.colors.SemanticColors
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DesignSystemCatalogScreen(
    onNavigateBack: () -> Unit,
    onNavigateToComponent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val typography = DesignSystemTypography()
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(onBackClick = onNavigateBack)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.md)
        ) {
            item {
                Text(
                    text = "Components",
                    style = typography.headlineMedium,
                    color = SemanticColors.Foreground.primary
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }

            items(ComponentCategory.entries.toTypedArray()) { category ->
                ComponentCategoryCard(
                    category = category,
                    onNavigateToComponent = onNavigateToComponent
                )
            }

            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = "Design Tokens",
                    style = typography.headlineMedium,
                    color = SemanticColors.Foreground.primary
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }

            item {
                ComponentCategoryCard(
                    category = ComponentCategory.COLORS,
                    onNavigateToComponent = onNavigateToComponent
                )
            }

            item {
                ComponentCategoryCard(
                    category = ComponentCategory.TYPOGRAPHY,
                    onNavigateToComponent = onNavigateToComponent
                )
            }
        }
    }
}

@Composable
private fun ComponentCategoryCard(
    category: ComponentCategory,
    onNavigateToComponent: (String) -> Unit
) {
    val typography = DesignSystemTypography()
    OrganizeCard(
        onClick = { onNavigateToComponent(category.route) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = category.title,
                    style = typography.titleMedium,
                    color = SemanticColors.Foreground.primary
                )
                Text(
                    text = category.description,
                    style = typography.bodyMedium,
                    color = SemanticColors.Foreground.secondary
                )
            }

            Icon(
                imageVector = OrganizeIcons.System.ArrowBackIos,
                contentDescription = "Navigate forward",
                tint = SemanticColors.Foreground.brand,
                modifier = Modifier.size(Spacing.iconSize)
            )
        }
    }
}

enum class ComponentCategory(
    val title: String,
    val description: String,
    val route: String
) {
    BANNERS(
        title = "Banners",
        description = "Success, error, and notification banners",
        route = "banners"
    ),
    BUTTONS(
        title = "Buttons",
        description = "Primary, Secondary, Text, Icon, and FAB buttons",
        route = "buttons"
    ),
    CARDS(
        title = "Cards",
        description = "Content containers and layouts",
        route = "cards"
    ),
    CHIPS(
        title = "Chips",
        description = "Priority, category, and status chips",
        route = "chips"
    ),
    CHARTS(
        title = "Charts",
        description = "Data visualization and chart components",
        route = "charts"
    ),
    COLORS(
        title = "Colors",
        description = "Color palette and theme colors",
        route = "colors"
    ),
    LAYOUTS(
        title = "Layouts",
        description = "Empty states, sections, and containers",
        route = "layouts"
    ),
    PROGRESS(
        title = "Progress",
        description = "Loading indicators and progress components",
        route = "progress"
    ),
    RESULT(
        title = "Result",
        description = "Result and feedback components",
        route = "result"
    ),
    TYPOGRAPHY(
        title = "Typography",
        description = "Text styles and font specifications",
        route = "typography"
    )
}
