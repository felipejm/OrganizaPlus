package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.OrganizeSelect
import com.joffer.organizeplus.designsystem.components.SelectOption
import com.joffer.organizeplus.designsystem.components.SelectSize
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.DesignSystemTypography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.select_showcase_title

@Composable
fun SelectShowcaseScreen(
    onNavigateBack: () -> Unit
) {
    val typography = DesignSystemTypography()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopAppBarWithBackButton(
            title = stringResource(Res.string.select_showcase_title),
            onBackClick = onNavigateBack
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Basic Select",
                            style = typography.titleMedium
                        )

                        val options = listOf(
                            SelectOption("1", "Option 1"),
                            SelectOption("2", "Option 2"),
                            SelectOption("3", "Option 3"),
                            SelectOption("4", "Option 4")
                        )

                        var selectedValue by remember { mutableStateOf<String?>(null) }

                        OrganizeSelect(
                            options = options,
                            selectedValue = selectedValue,
                            placeholder = "Choose an option",
                            onValueChange = { selectedValue = it }
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Different Sizes",
                            style = typography.titleMedium
                        )

                        val options = listOf(
                            SelectOption("1", "Small Option"),
                            SelectOption("2", "Medium Option"),
                            SelectOption("3", "Large Option")
                        )

                        var smallSelected by remember { mutableStateOf<String?>(null) }
                        var mediumSelected by remember { mutableStateOf<String?>(null) }
                        var largeSelected by remember { mutableStateOf<String?>(null) }

                        OrganizeSelect(
                            options = options,
                            selectedValue = smallSelected,
                            placeholder = "Small Select",
                            onValueChange = { smallSelected = it },
                            size = SelectSize.SMALL
                        )

                        OrganizeSelect(
                            options = options,
                            selectedValue = mediumSelected,
                            placeholder = "Medium Select",
                            onValueChange = { mediumSelected = it },
                            size = SelectSize.MEDIUM
                        )

                        OrganizeSelect(
                            options = options,
                            selectedValue = largeSelected,
                            placeholder = "Large Select",
                            onValueChange = { largeSelected = it },
                            size = SelectSize.LARGE
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "With Error State",
                            style = typography.titleMedium
                        )

                        val options = listOf(
                            SelectOption("1", "Valid Option"),
                            SelectOption("2", "Another Valid Option")
                        )

                        var selectedValue by remember { mutableStateOf<String?>(null) }

                        OrganizeSelect(
                            options = options,
                            selectedValue = selectedValue,
                            placeholder = "Select with error",
                            onValueChange = { selectedValue = it },
                            error = "This field is required"
                        )
                    }
                }
            }

            item {
                OrganizeCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(Spacing.md),
                        verticalArrangement = Arrangement.spacedBy(Spacing.md)
                    ) {
                        Text(
                            text = "Disabled State",
                            style = typography.titleMedium
                        )

                        val options = listOf(
                            SelectOption("1", "Option 1"),
                            SelectOption("2", "Option 2")
                        )

                        OrganizeSelect(
                            options = options,
                            selectedValue = "1",
                            placeholder = "Disabled select",
                            onValueChange = { },
                            enabled = false
                        )
                    }
                }
            }
        }
    }
}
