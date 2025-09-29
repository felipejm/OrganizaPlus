package com.joffer.organizeplus.designsystem.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.joffer.organizeplus.designsystem.colors.ColorScheme as AppColorScheme
import com.joffer.organizeplus.designsystem.components.*
import com.joffer.organizeplus.designsystem.spacing.Spacing
import com.joffer.organizeplus.designsystem.typography.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputShowcaseScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Input Component",
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
                    text = "Input Component",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
            }
            
            items(InputShowcaseItem.values()) { item ->
                InputShowcaseItem(item = item)
            }
            
            item {
                Spacer(modifier = Modifier.height(Spacing.lg))
                Text(
                    text = "Real Usage Example",
                    style = Typography.h3,
                    color = AppColorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Spacing.sm))
            }
            
            item {
                InputUsageExample()
            }
        }
    }
}

@Composable
private fun InputShowcaseItem(
    item: InputShowcaseItem,
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
                InputShowcaseItem.SIZES -> {
                    SizeExamples()
                }
                InputShowcaseItem.STATES -> {
                    StateExamples()
                }
                InputShowcaseItem.ICONS -> {
                    IconExamples()
                }
                InputShowcaseItem.VALIDATION -> {
                    ValidationExamples()
                }
                InputShowcaseItem.TYPES -> {
                    TypeExamples()
                }
            }
        }
    }
}

@Composable
private fun SizeExamples() {
    var largeValue by remember { mutableStateOf("") }
    var mediumValue by remember { mutableStateOf("") }
    var smallValue by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Input Sizes",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        OrganizeInput(
            label = "Large Input",
            value = largeValue,
            onValueChange = { largeValue = it },
            placeholder = "Enter text here",
            size = InputSize.LARGE
        )
        
        OrganizeInput(
            label = "Medium Input",
            value = mediumValue,
            onValueChange = { mediumValue = it },
            placeholder = "Enter text here",
            size = InputSize.MEDIUM
        )
        
        OrganizeInput(
            label = "Small Input",
            value = smallValue,
            onValueChange = { smallValue = it },
            placeholder = "Enter text here",
            size = InputSize.SMALL
        )
    }
}

@Composable
private fun StateExamples() {
    var defaultValue by remember { mutableStateOf("") }
    var hoverValue by remember { mutableStateOf("") }
    var focusedValue by remember { mutableStateOf("") }
    var errorValue by remember { mutableStateOf("") }
    var disabledValue by remember { mutableStateOf("Disabled value") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Input States",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        OrganizeInput(
            label = "Default State",
            value = defaultValue,
            onValueChange = { defaultValue = it },
            placeholder = "Default input",
            state = InputState.DEFAULT
        )
        
        OrganizeInput(
            label = "Hover State",
            value = hoverValue,
            onValueChange = { hoverValue = it },
            placeholder = "Hover input",
            state = InputState.HOVER
        )
        
        OrganizeInput(
            label = "Focused State",
            value = focusedValue,
            onValueChange = { focusedValue = it },
            placeholder = "Focused input",
            state = InputState.FOCUSED
        )
        
        OrganizeInput(
            label = "Error State",
            value = errorValue,
            onValueChange = { errorValue = it },
            placeholder = "Error input",
            state = InputState.ERROR,
            errorText = "This field is required"
        )
        
        OrganizeInput(
            label = "Disabled State",
            value = disabledValue,
            onValueChange = { },
            placeholder = "Disabled input",
            state = InputState.DISABLED,
            enabled = false
        )
    }
}

@Composable
private fun IconExamples() {
    var leadingValue by remember { mutableStateOf("") }
    var trailingValue by remember { mutableStateOf("") }
    var bothIconsValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Input with Icons",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        OrganizeInput(
            label = "Leading Icon",
            value = leadingValue,
            onValueChange = { leadingValue = it },
            placeholder = "Enter email",
            leadingIcon = Icons.Default.Email
        )
        
        OrganizeInput(
            label = "Trailing Icon",
            value = trailingValue,
            onValueChange = { trailingValue = it },
            placeholder = "Search...",
            trailingIcon = Icons.Default.Person,
            onTrailingIconClick = { /* Handle search */ }
        )
        
        OrganizeInput(
            label = "Both Icons",
            value = bothIconsValue,
            onValueChange = { bothIconsValue = it },
            placeholder = "Username",
            leadingIcon = Icons.Default.Person,
            trailingIcon = Icons.Default.Email,
            onTrailingIconClick = { /* Handle action */ }
        )
        
        OrganizeInput(
            label = "Password Field",
            value = passwordValue,
            onValueChange = { passwordValue = it },
            placeholder = "Enter password",
            leadingIcon = Icons.Default.Lock,
            trailingIcon = null, // Using text instead of icon
            onTrailingIconClick = { showPassword = !showPassword },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
        )
    }
}

@Composable
private fun ValidationExamples() {
    var requiredValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var phoneValue by remember { mutableStateOf("") }
    var errorValue by remember { mutableStateOf("invalid") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Validation Examples",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        OrganizeInput(
            label = "Required Field",
            value = requiredValue,
            onValueChange = { requiredValue = it },
            placeholder = "This field is required",
            isRequired = true,
            helperText = "Please fill this field"
        )
        
        OrganizeInput(
            label = "Email Field",
            value = emailValue,
            onValueChange = { emailValue = it },
            placeholder = "Enter email address",
            keyboardType = KeyboardType.Email,
            leadingIcon = Icons.Default.Email,
            helperText = "We'll never share your email"
        )
        
        OrganizeInput(
            label = "Phone Field",
            value = phoneValue,
            onValueChange = { phoneValue = it },
            placeholder = "Enter phone number",
            keyboardType = KeyboardType.Phone,
            helperText = "Include country code"
        )
        
        OrganizeInput(
            label = "Error Field",
            value = errorValue,
            onValueChange = { errorValue = it },
            placeholder = "Enter valid input",
            state = InputState.ERROR,
            errorText = "This input is invalid"
        )
    }
}

@Composable
private fun TypeExamples() {
    var textValue by remember { mutableStateOf("") }
    var numberValue by remember { mutableStateOf("") }
    var multilineValue by remember { mutableStateOf("") }
    var singleLineValue by remember { mutableStateOf("") }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(Spacing.md)
    ) {
        Text(
            text = "Input Types",
            style = Typography.subtitle,
            color = AppColorScheme.onSurface
        )
        
        OrganizeInput(
            label = "Text Input",
            value = textValue,
            onValueChange = { textValue = it },
            placeholder = "Enter text",
            keyboardType = KeyboardType.Text
        )
        
        OrganizeInput(
            label = "Number Input",
            value = numberValue,
            onValueChange = { numberValue = it },
            placeholder = "Enter number",
            keyboardType = KeyboardType.Number
        )
        
        OrganizeInput(
            label = "Multiline Input",
            value = multilineValue,
            onValueChange = { multilineValue = it },
            placeholder = "Enter multiple lines of text",
            singleLine = false,
            minLines = 3,
            maxLines = 6
        )
        
        OrganizeInput(
            label = "Single Line Input",
            value = singleLineValue,
            onValueChange = { singleLineValue = it },
            placeholder = "Single line only",
            singleLine = true
        )
    }
}

enum class InputShowcaseItem(
    val title: String,
    val description: String
) {
    SIZES(
        title = "Input Sizes",
        description = "Large, Medium, and Small input variants"
    ),
    STATES(
        title = "Input States",
        description = "Default, Hover, Focused, Error, and Disabled states"
    ),
    ICONS(
        title = "Input with Icons",
        description = "Leading, trailing, and both icon configurations"
    ),
    VALIDATION(
        title = "Validation Examples",
        description = "Required fields, error states, and helper text"
    ),
    TYPES(
        title = "Input Types",
        description = "Different keyboard types and multiline inputs"
    )
}
