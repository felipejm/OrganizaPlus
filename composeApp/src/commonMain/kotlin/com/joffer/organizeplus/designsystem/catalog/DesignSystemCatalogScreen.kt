package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme

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
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
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

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Navigate forward",
                tint = AppColorScheme.primary,
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
    BADGES(
        title = "Badges",
        description = "Notification, status, and counter badges",
        route = "badges"
    ),
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
    CHARTS(
        title = "Charts",
        description = "Data visualization and chart components",
        route = "charts"
    ),
    CHIPS(
        title = "Chips",
        description = "Priority, category, and status chips",
        route = "chips"
    ),
    COLORS(
        title = "Colors",
        description = "Color palette and theme colors",
        route = "colors"
    ),
    FORM(
        title = "Form",
        description = "Form layout and field components",
        route = "form"
    ),
    INPUTS(
        title = "Inputs",
        description = "New input component with multiple sizes and states",
        route = "inputs"
    ),
    LAYOUTS(
        title = "Layouts",
        description = "Empty states, sections, and containers",
        route = "layouts"
    ),
    MESSAGE(
        title = "Message",
        description = "Toast messages and inline prompts",
        route = "message"
    ),
    PROGRESS(
        title = "Progress",
        description = "Loading indicators and progress components",
        route = "progress"
    ),
    RADIO(
        title = "Radio",
        description = "Radio button selection components",
        route = "radio"
    ),
    RESULT(
        title = "Result",
        description = "Result and feedback components",
        route = "result"
    ),
    SELECT(
        title = "Select",
        description = "Dropdown selection components",
        route = "select"
    ),
    TAGS(
        title = "Tags",
        description = "Categorization, labeling, and status tags",
        route = "simple_tags"
    ),
    TYPOGRAPHY(
        title = "Typography",
        description = "Text styles and font specifications",
        route = "typography"
    )
}
