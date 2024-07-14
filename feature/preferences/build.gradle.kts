plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    val composeCompilerVersion: String by project
    compileSdk = 33

    defaultConfig {
        minSdk = 23
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = composeCompilerVersion
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    namespace = "fr.boitakub.preferences"
}

dependencies {
    val androidCoreVersion: String by project
    val lifecycleVersion: String by project
    val daggerVersion: String by project
    val hiltVersion: String by project
    val composeBomVersion: String by project
    val navigationVersion: String by project
    val dataStoreVersion: String by project
    val junitVersion: String by project
    val espressoVersion: String by project

    implementation(project(":common"))

    //region Core & Lifecycle

    implementation("androidx.core:core-ktx:$androidCoreVersion")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:$lifecycleVersion")

    //endregion

    //region Dependency Injection

    implementation("com.google.dagger:hilt-android:$daggerVersion")
    kapt("com.google.dagger:hilt-compiler:$daggerVersion")
    implementation("androidx.hilt:hilt-work:$hiltVersion")
    kapt("androidx.hilt:hilt-compiler:$hiltVersion")

    //endregion

    //region UI

    val composeBom = platform("androidx.compose:compose-bom:$composeBomVersion")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.ui:ui")

    // UI - Tooling
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    //endregion

    // Navigation

    implementation("androidx.navigation:navigation-compose:$navigationVersion")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltVersion")

    //endregion

    // DataStore (SharedPreferences)

    implementation("androidx.datastore:datastore-preferences:$dataStoreVersion")

    //endregion

    //region Test

    testImplementation("junit:junit:$junitVersion")

    //endregion

    //region AndroidTest

    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion")

    //endregion
}
