# Navigation Provider Injection Fix

## Problem
The app was crashing with a `ClassCastException` when trying to inject navigation provider lists:
```
java.lang.ClassCastException: com.joffer.organizeplus.features.onboarding.navigation.OnboardingNavigationProvider 
cannot be cast to com.joffer.organizeplus.navigation.config.FeatureNavigationProvider
```

### Root Cause
Koin was unable to differentiate between two different `List` types when both were registered without explicit qualifiers:
- `List<FeatureNavigationProvider>` - for bottom navigation tabs
- `List<MainNavigationProvider>` - for app-level screens

When the code requested `List<FeatureNavigationProvider>`, Koin would sometimes inject the `List<MainNavigationProvider>` instead, causing the crash.

## Solution
Added **named qualifiers** to distinguish between the two lists in Koin dependency injection.

### Changes Made

#### 1. NavigationModule.kt
**Before:**
```kotlin
val navigationModule = module {
    single<List<FeatureNavigationProvider>> {
        listOf(...)
    }
    
    single<List<MainNavigationProvider>> {
        listOf(...)
    }
}
```

**After:**
```kotlin
val navigationModule = module {
    single(qualifier = named("featureProviders")) {
        listOf<FeatureNavigationProvider>(...)
    }
    
    single(qualifier = named("mainProviders")) {
        listOf<MainNavigationProvider>(...)
    }
}
```

#### 2. DashboardMainNavigationProvider.kt
**Before:**
```kotlin
val featureProviders: List<FeatureNavigationProvider> = koinInject()
```

**After:**
```kotlin
val featureProviders: List<FeatureNavigationProvider> = koinInject(named("featureProviders"))
```

#### 3. AppNavigation.kt
**Before:**
```kotlin
val mainNavigationProviders: List<MainNavigationProvider> = koinInject()
```

**After:**
```kotlin
val mainNavigationProviders: List<MainNavigationProvider> = koinInject(named("mainProviders"))
```

## Architecture Overview

### Navigation Provider Hierarchy
```
MainNavigationProvider (app-level screens)
├── OnboardingNavigationProvider (SignIn, SignUp)
├── DashboardMainNavigationProvider (Dashboard container)
├── DutyMainNavigationProvider (CreateDuty, EditDuty, DutyOccurrences, etc.)
└── DesignSystemNavigationProvider (Design system catalog)

FeatureNavigationProvider (bottom nav tabs)
├── DashboardNavigationProvider (Dashboard tab)
├── PersonalDutyNavigationProvider (Personal duties tab)
├── CompanyDutyNavigationProvider (Company duties tab)
└── SettingsNavigationProvider (Settings tab)
```

### Key Differences
- **MainNavigationProvider**: Registers top-level screens that are not part of the bottom navigation
- **FeatureNavigationProvider**: Registers screens that contribute to the bottom navigation bar

## Verification
All navigation providers were reviewed to ensure:
1. ✅ No other provider list injections without qualifiers
2. ✅ All ViewModels use standard injection (no qualifiers needed)
3. ✅ Build successful after clean rebuild
4. ✅ All providers correctly implement their respective interfaces

## Files Modified
1. `/composeApp/src/commonMain/kotlin/com/joffer/organizeplus/navigation/config/NavigationModule.kt`
2. `/composeApp/src/commonMain/kotlin/com/joffer/organizeplus/features/dashboard/navigation/DashboardMainNavigationProvider.kt`
3. `/composeApp/src/commonMain/kotlin/com/joffer/organizeplus/navigation/AppNavigation.kt`

## Prevention
To prevent this issue in the future:
- Always use **named qualifiers** when registering multiple instances of the same generic type (e.g., `List<T>`)
- When injecting, always specify the qualifier: `koinInject(named("qualifier"))`
- Document the purpose of each qualifier in the module definition

