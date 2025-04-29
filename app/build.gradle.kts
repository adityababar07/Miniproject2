plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.miniproject"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.miniproject"
        minSdk = 30
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

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
//    The first line is for the core MQTT client library.
//    The second line is the Android Service implementation
    implementation("org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.4")
    implementation("org.eclipse.paho:org.eclipse.paho.android.service:1.1.1")
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}