package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun FormShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Form Components",
                        style = Typography.h2
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(Spacing.md),
            verticalArrangement = Arrangement.spacedBy(Spacing.lg)
        ) {
            item {
                Text(
                    text = "Form Components",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
            }
            
            items(FormShowcaseItem.values()) { item ->
                FormShowcaseItem(item = item)
            }
        }
    }
}

@Composable
private fun FormShowcaseItem(
    item: FormShowcaseItem,
    modifier: Modifier = Modifier
) {
    OrganizeCard(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = item.title,
                style = Typography.title,
                color = AppColorScheme.onSurface
            )
            
            Text(
                text = item.description,
                style = Typography.body,
                color = AppColorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(Spacing.md))
            
            when (item) {
                FormShowcaseItem.TEXT_FIELD -> {
                    TextFieldExamples()
                }
                FormShowcaseItem.DROPDOWN -> {
                    DropdownExamples()
                }
                FormShowcaseItem.RADIO_GROUP -> {
                    RadioGroupExamples()
                }
                FormShowcaseItem.DATE_PICKER -> {
                    DatePickerExamples()
                }
                FormShowcaseItem.FORM_SECTION -> {
                    FormSectionExamples()
                }
            }
        }
    }
}

@Composable
private fun TextFieldExamples() {
    var textValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var errorValue by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        FormField(
            label = "Regular Text Field",
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = "Enter text here"
        )
        
        FormField(
            label = "Password Field",
            value = passwordValue,
            onValueChange = { passwordValue = it },
            placeholder = "Enter password",
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Text(
                        text = if (showPassword) "Hide" else "Show",
                        style = Typography.caption
                    )
                }
            }
        )
        
        FormField(
            label = "Required Field",
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = "This field is required",
            isRequired = true
        )
        
        FormField(
            label = "Error Field",
            value = errorValue,
            onValueChange = { errorValue = it },
            placeholder = "This field has an error",
            isError = true,
            errorMessage = "This field is required"
        )
        
        FormTextArea(
            label = "Text Area",
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = "Enter multiple lines of text",
            rows = 4
        )
    }
}

@Composable
private fun DropdownExamples() {
    var selectedOption by remember { mutableStateOf("") }
    val options = listOf("Option 1" to "Option 1", "Option 2" to "Option 2", "Option 3" to "Option 3", "Option 4" to "Option 4")
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        DropdownField(
            label = "Dropdown Field",
            value = selectedOption,
            onValueChange = { selectedOption = it },
            options = options,
            placeholder = "Select an option"
        )
        
        DropdownField(
            label = "Required Dropdown",
            value = selectedOption,
            onValueChange = { selectedOption = it },
            options = options,
            placeholder = "Select an option",
            isRequired = true
        )
    }
}

@Composable
private fun RadioGroupExamples() {
    var selectedOption by remember { mutableStateOf("option1") }
    val options = listOf(
        "Option 1" to "option1",
        "Option 2" to "option2",
        "Option 3" to "option3"
    )
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        RadioGroup(
            label = "Radio Group",
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
        
        RadioGroup(
            label = "Required Radio Group",
            options = options,
            selectedOption = selectedOption,
            onOptionSelected = { selectedOption = it }
        )
    }
}

@Composable
private fun DatePickerExamples() {
    var dateValue by remember { mutableStateOf("") }
    var timeValue by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        DateInputField(
            label = "Date Field",
            value = dateValue,
            onValueChange = { dateValue = it },
            placeholder = "Select date"
        )
    }
}

@Composable
private fun FormSectionExamples() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        FormSection {
            FormField(
                label = "First Name",
                value = "",
                onValueChange = { },
                placeholder = "Enter first name"
            )
            
            FormField(
                label = "Last Name",
                value = "",
                onValueChange = { },
                placeholder = "Enter last name"
            )
        }
        
        FormSection {
            FormField(
                label = "Email",
                value = "",
                onValueChange = { },
                placeholder = "Enter email address"
            )
            
            FormField(
                label = "Phone",
                value = "",
                onValueChange = { },
                placeholder = "Enter phone number"
            )
        }
    }
}

enum class FormShowcaseItem(
    val title: String,
    val description: String
) {
    TEXT_FIELD(
        title = "Text Fields",
        description = "Input fields for text, password, and multiline content"
    ),
    DROPDOWN(
        title = "Dropdown Fields",
        description = "Selection fields with predefined options"
    ),
    RADIO_GROUP(
        title = "Radio Groups",
        description = "Single selection from multiple options"
    ),
    DATE_PICKER(
        title = "Date & Time Pickers",
        description = "Date and time selection components"
    ),
    FORM_SECTION(
        title = "Form Sections",
        description = "Grouped form fields with section headers"
    )
}
