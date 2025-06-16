plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.huseyinkiran.favuniversities"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.huseyinkiran.favuniversity"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.huseyinkiran.favuniversities.HiltTestRunner"
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

val roomVersion = "2.6.1"
val navVersion = "2.8.4"
val lifecycleVersion = "2.8.7"
val androidXTestVersion = "1.6.1"
val mockitoVersion = "4.8.1"
val mockitoKotlinVersion = "4.1.0"
val mockkVersion = "1.13.5"
val pagingVersion = "3.3.6"

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.mockito:mockito-core:4.8.1")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.1.0")
    testImplementation("androidx.test.ext:truth:1.6.0")
    // Optional -- Robolectric environment
    testImplementation("androidx.test:core:$androidXTestVersion")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    // Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    // Optional -- Mockk framework
    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("net.bytebuddy:byte-buddy:1.14.4")
    testImplementation("com.google.truth:truth:1.4.2")
    testImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.51.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("com.google.truth:truth:1.4.2")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.test.ext:truth:1.6.0")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")
    androidTestImplementation("androidx.fragment:fragment-testing:1.8.5")
    debugImplementation("androidx.fragment:fragment-testing-manifest:1.8.5")
    androidTestImplementation("androidx.navigation:navigation-testing:$navVersion")
    androidTestImplementation("org.mockito:mockito-android:$mockitoVersion")
    // Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    // Navigation
    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    // Splash
    implementation("androidx.core:core-splashscreen:1.0.0")
    // Mockito
    implementation("org.mockito:mockito-android:$mockitoVersion")
    // Paging3
    implementation("androidx.paging:paging-runtime:$pagingVersion")
}