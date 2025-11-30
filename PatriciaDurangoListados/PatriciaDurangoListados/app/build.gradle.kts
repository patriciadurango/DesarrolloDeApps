plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.patriciadurangolistados"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.patriciadurangolistados"
        minSdk = 26
        targetSdk = 35
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
}

dependencies {

    implementation(libs.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(libs.activity.v182)
    implementation(libs.constraintlayout.v214)
    implementation(libs.recyclerview.v132)
    implementation(libs.coordinatorlayout.v120)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit.v115)
    androidTestImplementation(libs.espresso.core.v351)
}