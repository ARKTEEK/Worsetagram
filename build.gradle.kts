buildscript {
  dependencies {
    classpath("com.google.gms:google-services:4.4.1")
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.50")
  }
}

plugins {
  id("com.android.application") version "8.1.1" apply false
  id("org.jetbrains.kotlin.android") version "1.8.10" apply false
  id("com.google.gms.google-services") version "4.4.1" apply false
  id("com.google.dagger.hilt.android") version "2.50" apply false
}
