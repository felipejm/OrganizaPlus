# Project Rules - OrganizePlus

## String Resources Rule

### 🚫 NEVER USE HARDCODED STRINGS

**Rule**: All user-facing text MUST be defined in string resources files.

### ✅ Correct Usage

```kotlin
// ✅ GOOD - Using string resources
Text(
    text = stringResource(Res.string.settings_title),
    style = Typography.h1
)

Button(
    onClick = { /* action */ }
) {
    Text(text = stringResource(Res.string.save))
}
```

### ❌ Incorrect Usage

```kotlin
// ❌ BAD - Hardcoded strings
Text(
    text = "Settings",
    style = Typography.h1
)

Button(
    onClick = { /* action */ }
) {
    Text(text = "Save")
}
```

### 📁 String Resource Files

All strings must be defined in:
- `composeApp/src/commonMain/composeResources/values/strings.xml` (English)
- `composeApp/src/commonMain/composeResources/values-pt/strings.xml` (Portuguese)

### 📝 String Naming Convention

Use descriptive, hierarchical names:

```xml
<!-- Settings -->
<string name="settings_title">Settings</string>
<string name="settings_design_system">Design System</string>
<string name="settings_design_system_catalog">Design System Catalog</string>

<!-- Buttons -->
<string name="button_save">Save</string>
<string name="button_cancel">Cancel</string>
<string name="button_delete">Delete</string>

<!-- Form Fields -->
<string name="form_user_name">User Name</string>
<string name="form_user_name_hint">Enter your name</string>
<string name="form_email">Email</string>
<string name="form_email_hint">Enter your email</string>

<!-- Error Messages -->
<string name="error_network">Network error</string>
<string name="error_validation">Validation error</string>
<string name="error_generic">Something went wrong</string>
```

### 🔍 Code Review Checklist

Before submitting any code, verify:

- [ ] No hardcoded strings in UI components
- [ ] All user-facing text uses `stringResource()`
- [ ] String resources are defined in both English and Portuguese
- [ ] String names follow the naming convention
- [ ] No strings in comments that should be user-facing

### 🛠️ Implementation Steps

1. **Identify hardcoded strings** in your code
2. **Add string resources** to both language files
3. **Import the string resource** in your Kotlin file
4. **Replace hardcoded string** with `stringResource(Res.string.your_string_name)`
5. **Test both languages** to ensure proper translation

### 📋 Common String Categories

- **Navigation**: `nav_dashboard`, `nav_settings`, `nav_back`
- **Actions**: `action_save`, `action_cancel`, `action_delete`, `action_edit`
- **Labels**: `label_name`, `label_email`, `label_phone`
- **Messages**: `message_success`, `message_error`, `message_loading`
- **Placeholders**: `placeholder_search`, `placeholder_enter_text`
- **Titles**: `title_dashboard`, `title_settings`, `title_profile`
- **Descriptions**: `description_feature`, `description_help`

### ⚠️ Exceptions

Only these cases are allowed to use hardcoded strings:

1. **Debug logs** (not user-facing)
2. **Technical identifiers** (API endpoints, database fields)
3. **Development-only text** (temporary placeholders during development)

### 🎯 Benefits

- **Internationalization**: Easy to add new languages
- **Consistency**: Centralized text management
- **Maintainability**: Single source of truth for all text
- **Quality**: Ensures all text is reviewed and translated
- **Accessibility**: Better support for screen readers and accessibility tools

---

**Remember**: Every hardcoded string is a potential bug and maintenance issue. Always use string resources!
