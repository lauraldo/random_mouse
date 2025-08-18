import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrainsKotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.dropshots)
}

android {
    namespace = "com.niolasdev.randommouse"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.niolasdev.randommouse"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // version for compiler
        targetCompatibility = JavaVersion.VERSION_17 // bytecode compatibility (run-time)
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material)
    implementation(libs.androidx.material3)

    implementation(libs.kotlinx.serialization.json)

    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.navigation)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.ui.test.junit4.android)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(project(":network"))
    implementation(project(":storage"))

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    testImplementation(libs.dropshots)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.compose.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

tasks {
    register<Exec>(name = "checkKotlinInstalled") {
        description = "Check if Kotlin is installed"
        commandLine = listOf("which", "kotlinc")
    }

    register<Exec>(name = "generateGithubWorkflow") {
        dependsOn("checkKotlinInstalled")

        description = "Generate Github Workflow YAML file from Kotlin Script"
        workingDir = rootDir
        commandLine = listOf(
            "${rootDir}/.github/workflows/github_workflow.main.kts"
        )
    }

    configureEach {
        if (name.startsWith("assemble") && state.failure == null) {
            finalizedBy("generateGithubWorkflow")
        }
    }
}