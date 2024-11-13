plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.replica"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.replica"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.room:room-runtime:2.4.0")
    annotationProcessor("androidx.room:room-compiler:2.4.0") // Necesario para Room en Java

    // AndroidX Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RecyclerView for displaying list of products
    implementation("androidx.recyclerview:recyclerview:1.2.1")

    // Lifecycle components for ViewModel and LiveData (MVVM)
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.4.0")
    implementation("androidx.lifecycle:lifecycle-livedata:2.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.4.0")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}