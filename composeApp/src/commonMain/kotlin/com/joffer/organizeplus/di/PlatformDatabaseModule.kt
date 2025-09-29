package com.joffer.organizeplus.di

// Platform-specific database module
// This will be overridden by actual implementations in platform-specific modules
expect val platformDatabaseModule: org.koin.core.module.Module
