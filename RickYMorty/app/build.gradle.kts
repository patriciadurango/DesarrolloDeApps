plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.rickymorty"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.rickymorty"
        minSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("de.hdodenhof:circleimageview:3.1.0")

    // Retrofit y Gson para peticiones de red
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // Glide para carga de imágenes
    implementation(libs.glide)
    annotationProcessor(libs.glide.compiler) // 'annotationProcessor' es el comando correcto para el compilador de Glide

    // Dependencias de Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
