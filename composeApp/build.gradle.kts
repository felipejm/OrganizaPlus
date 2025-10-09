import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

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
    alias(libs.plugins.buildkonfig)
}

// Load secret.properties
val secretProperties = Properties().apply {
    val secretPropertiesFile = rootProject.file("secret.properties")
    if (secretPropertiesFile.exists()) {
        secretPropertiesFile.inputStream().use { load(it) }
    }
}

val apiKey = secretProperties.getProperty("API_KEY", "")
val baseUrl = secretProperties.getProperty("API_BASE_URL", "")
val userPoolId = secretProperties.getProperty("USER_POOL_ID", "")
val userPoolClientId = secretProperties.getProperty("USER_POOL_CLIENT_ID", "")

buildkonfig {
    packageName = "com.joffer.organizeplus"
    objectName = "BuildConfig"
    exposeObjectWithName = "BuildConfig"
    
    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, "VERSION", "1.0.0")
        buildConfigField(FieldSpec.Type.STRING, "ENVIRONMENT", "Prod")
        buildConfigField(FieldSpec.Type.STRING, "API_BASE_URL", baseUrl)
        buildConfigField(FieldSpec.Type.STRING, "API_KEY", apiKey)
        buildConfigField(FieldSpec.Type.STRING, "USER_POOL_ID", userPoolId)
        buildConfigField(FieldSpec.Type.STRING, "USER_POOL_CLIENT_ID", userPoolClientId)
    }
}

// Fix task dependencies
tasks.matching { it.name.startsWith("ksp") }.configureEach {
    dependsOn("generateBuildKonfig")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            freeCompilerArgs.addAll(
                "-Xexpect-actual-classes",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
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
            
            // Android-specific Room dependencies
            implementation(libs.room.ktx)
            
            // Security library
            implementation(libs.androidx.security.crypto)
            
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
            
            // Chart library
            implementation(libs.koalaplot.core)
            
            // Async libraries
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            
            // UI libraries
            // Navigation library
            implementation(libs.compose.navigation)
            implementation(libs.kamel)
            implementation(libs.material.icons.extended)

            // DI library
            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            // HTTP libraries
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // DB library
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)

            // Room library (KMP compatible)
            implementation(libs.room.runtime)
            implementation(libs.room.common)
            implementation(libs.sqlite.bundled)

            // Logging library
            implementation(libs.napier)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
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
        jniLibs {
            useLegacyPackaging = false
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
    detektPlugins(libs.detekt.formatting)
    detektPlugins(project(":detekt-rules"))
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
    
    // iOS-specific resource generation tasks
    dependsOn("generateResourceAccessorsForIosArm64Main")
    dependsOn("generateActualResourceCollectorsForIosArm64Main")
    dependsOn("generateResourceAccessorsForIosSimulatorArm64Main")
    dependsOn("generateActualResourceCollectorsForIosSimulatorArm64Main")
    dependsOn("generateResourceAccessorsForIosMain")
    dependsOn("generateResourceAccessorsForAppleMain")
    dependsOn("generateResourceAccessorsForNativeMain")
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = true
    config.setFrom("$projectDir/../detekt.yml")
    
    // Explicitly specify source sets
    source.setFrom(
        "src/commonMain/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin"
    )
}


