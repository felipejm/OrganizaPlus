package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DesignSystemCatalogScreen(
    onNavigateBack: () -> Unit,
    onNavigateToComponent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            AppTopAppBarWithBackButton(
                title = "Design System Catalog",
                onBackClick = onNavigateBack
            )
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
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }
            
            items(ComponentCategory.values()) { category ->
                ComponentCategoryCard(
                    category = category,
                    onNavigateToComponent = onNavigateToComponent
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = "Design Tokens",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
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
            Column {
                Text(
                    text = category.title,
                    style = Typography.title,
                    color = AppColorScheme.onSurface
                )
                Text(
                    text = category.description,
                    style = Typography.body,
                    color = AppColorScheme.onSurfaceVariant
                )
            }
            
            Text(
                text = "→",
                style = Typography.title,
                color = AppColorScheme.primary
            )
        }
    }
}


enum class ComponentCategory(
    val title: String,
    val description: String,
    val route: String
) {
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
    FORMS(
        title = "Forms",
        description = "Input fields, dropdowns, and form components",
        route = "forms"
    ),
    INPUTS(
        title = "Inputs",
        description = "New input component with multiple sizes and states",
        route = "inputs"
    ),
    CHIPS(
        title = "Chips",
        description = "Priority, category, and status chips",
        route = "chips"
    ),
    BADGES(
        title = "Badges",
        description = "Notification, status, and counter badges",
        route = "badges"
    ),
    TAGS(
        title = "Tags",
        description = "Categorization, labeling, and status tags",
        route = "simple_tags"
    ),
    BANNERS(
        title = "Banners",
        description = "Success, error, and notification banners",
        route = "banners"
    ),
    LAYOUTS(
        title = "Layouts",
        description = "Empty states, sections, and containers",
        route = "layouts"
    ),
    COLORS(
        title = "Colors",
        description = "Complete color palette and usage examples",
        route = "colors"
    ),
    TYPOGRAPHY(
        title = "Typography",
        description = "Text styles, font weights, and usage examples",
        route = "typography"
    )
}
