package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.components.AppTopAppBarWithBackButton
import com.joffer.organizeplus.designsystem.components.OrganizeCard
import com.joffer.organizeplus.designsystem.components.OrganizeRadio
import com.joffer.organizeplus.designsystem.components.OrganizeRadioGroup
import com.joffer.organizeplus.designsystem.components.RadioOption
import com.joffer.organizeplus.designsystem.components.RadioOrientation
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography
import org.jetbrains.compose.resources.stringResource
import organizeplus.composeapp.generated.resources.Res
import organizeplus.composeapp.generated.resources.radio_showcase_title

@Composable
fun RadioShowcaseScreen(
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopAppBarWithBackButton(
            title = stringResource(Res.string.radio_showcase_title),
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
                            text = "Basic Radio Group",
                            style = Typography.titleMedium
                        )
                        
                        val options = listOf(
                            RadioOption("1", "Option 1"),
                            RadioOption("2", "Option 2"),
                            RadioOption("3", "Option 3")
                        )
                        
                        var selectedValue by remember { mutableStateOf<String?>(null) }
                        
                        OrganizeRadioGroup(
                            options = options,
                            selectedValue = selectedValue,
                            onValueChange = { selectedValue = it },
                            label = "Choose an option"
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
                            text = "Horizontal Layout",
                            style = Typography.titleMedium
                        )
                        
                        val options = listOf(
                            RadioOption("a", "A"),
                            RadioOption("b", "B"),
                            RadioOption("c", "C")
                        )
                        
                        var selectedValue by remember { mutableStateOf<String?>(null) }
                        
                        OrganizeRadio(
                            options = options,
                            selectedValue = selectedValue,
                            onValueChange = { selectedValue = it },
                            orientation = RadioOrientation.HORIZONTAL
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
                            text = "With Disabled Options",
                            style = Typography.titleMedium
                        )
                        
                        val options = listOf(
                            RadioOption("1", "Enabled Option 1"),
                            RadioOption("2", "Disabled Option", disabled = true),
                            RadioOption("3", "Enabled Option 2")
                        )
                        
                        var selectedValue by remember { mutableStateOf<String?>(null) }
                        
                        OrganizeRadioGroup(
                            options = options,
                            selectedValue = selectedValue,
                            onValueChange = { selectedValue = it },
                            label = "Options with disabled state"
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
                            text = "Disabled Group",
                            style = Typography.titleMedium
                        )
                        
                        val options = listOf(
                            RadioOption("1", "Option 1"),
                            RadioOption("2", "Option 2")
                        )
                        
                        OrganizeRadioGroup(
                            options = options,
                            selectedValue = "1",
                            onValueChange = { },
                            label = "Disabled radio group",
                            enabled = false
                        )
                    }
                }
            }
        }
    }
}
