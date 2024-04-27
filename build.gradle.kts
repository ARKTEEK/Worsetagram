buildscript {
  dependencies {
    classpath(libs.google.services)
    classpath(libs.hilt.android.gradle.plugin)
  }
  repositories {
    mavenCentral()
    google()
  }
}

plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.jetbrainsKotlin) apply false
  alias(libs.plugins.googleGms) apply false
  alias(libs.plugins.daggerHilt) apply false
}
