plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.myproject"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myproject"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // ... dependensi lain seperti core-ktx, lifecycle, activity-compose, dsb.

    // Compose Dependencies
    val composeBom = platform("androidx.compose:compose-bom:2024.04.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Standard Compose Libraries
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")

    // Crucial: Material 3 and Icons Extended
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")

    // 🔥 PENTING: Tambahkan/Pastikan baris ini ada untuk ikon: Save, ContentCut, dll.
    implementation("androidx.compose.material:material-icons-extended")

    // Crucial: ViewModel integration for compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3") // Pastikan versi terbaru

    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("net.objecthunter:exp4j:0.4.8")
}