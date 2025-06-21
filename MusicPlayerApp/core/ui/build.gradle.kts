plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.applemusicreplica.core.ui"
    compileSdk = 34

    defaultConfig {
        minSdk = 28
        targetSdk = 34
    }

    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:model"))
    implementation("androidx.compose.ui:ui:1.5.10")
    implementation("androidx.compose.material3:material3:1.1.2")
}
