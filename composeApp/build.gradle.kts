import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.ksp)
    alias(libs.plugins.detekt)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            
            // Android-specific dependencies
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqldelight.android.driver)
            implementation(libs.coil.compose)
            implementation(libs.androidx.work.runtime)
            implementation(libs.mlkit.text.recognition)
            
        }
        commonMain.dependencies {
            // Compose Multiplatform
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            
            // Async libraries
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            
            // UI libraries
            // Navigation library
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.0")
            implementation(libs.kamel)
            
            // DI library
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            
            // HTTP libraries
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            
            // DB library
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)
            
            // Room library
            implementation(libs.room.runtime)
            implementation(libs.room.ktx)
            implementation(libs.room.common)
            
            // Preferences library
            // implementation(libs.multiplatform.settings)
            
            // Logging library
            implementation(libs.napier)
            
            // Core module
            // implementation(project(":core"))
            
            // Flows library (commented out for now - can be added when needed)
            // implementation(libs.km.nativecoroutines)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqldelight.native.driver)
        }
        
        
    }
}

android {
    namespace = "com.joffer.organizeplus"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.joffer.organizeplus"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}


sqldelight {
    databases {
        create("OrganizePlusDatabase") {
            packageName.set("com.joffer.organizeplus.database")
        }
    }
}




dependencies {
    add("kspCommonMainMetadata", libs.room.compiler)
    add("kspAndroid", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
}

// Fix KSP task dependencies
tasks.withType<com.google.devtools.ksp.gradle.KspAATask> {
    dependsOn("generateResourceAccessorsForAndroidDebug")
    dependsOn("generateResourceAccessorsForAndroidMain")
    dependsOn("generateActualResourceCollectorsForAndroidMain")
    dependsOn("generateComposeResClass")
    dependsOn("generateResourceAccessorsForCommonMain")
    dependsOn("generateExpectResourceCollectorsForCommonMain")
    dependsOn("generateCommonMainOrganizePlusDatabaseInterface")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    config.setFrom("$projectDir/../detekt.yml")
    baseline = file("$projectDir/detekt-baseline.xml")
}
