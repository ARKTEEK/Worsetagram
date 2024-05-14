plugins {
  alias(libs.plugins.androidApplication)
  alias(libs.plugins.jetbrainsKotlin)
  alias(libs.plugins.googleGms)

  kotlin("kapt")
  alias(libs.plugins.daggerHilt)
}

android {
  namespace = "me.arkteek.worsetagram"
  compileSdk = 34

  defaultConfig {
    applicationId = "me.arkteek.worsetagram"
    minSdk = 24
    targetSdk = 33
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    vectorDrawables { useSupportLibrary = true }
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions { jvmTarget = "17" }
  buildFeatures { compose = true }
  composeOptions { kotlinCompilerExtensionVersion = "1.4.3" }
  packaging { resources { excludes += "/META-INF/{AL2.0,LGPL2.1}" } }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.ui)
  implementation(platform(libs.firebase.bom))
  implementation(libs.coil.compose.v250)
  implementation(libs.firebase.analytics)
  implementation(libs.accompanist.permissions)
  implementation(libs.accompanist.systemuicontroller.v0270)
  implementation(libs.androidx.compose.ui.graphics)
  implementation(libs.androidx.compose.ui.tooling.preview)
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.navigation.runtime.ktx)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.firebase.auth)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.hilt.android)
  implementation(libs.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.lifecycle.runtime.compose)
  implementation(libs.androidx.constraintlayout.compose)
  implementation(libs.firebase.firestore)
  kapt(libs.hilt.android.compiler)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.tooling)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
}

// Allow references to generated code
kapt { correctErrorTypes = true }
