plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id ("dagger.hilt.android.plugin")
    id("androidx.room")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.allinone"
    compileSdk = 35

    room {
        schemaDirectory("$projectDir/schemas")
    }


    defaultConfig {
        applicationId = "com.example.allinone"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.sunrisesunset.io/\"")
        }
        release {
            /* this 2 block of code, prepare application for release.
             Basically optimize code and remove unused elements. */
            isShrinkResources = true
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://api.sunrisesunset.io/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        buildConfig = true
        // buildConfig itself is deprecated and will be removed in version 10.0 of gradle.
        // so use code below instead to keep using this feature.
        android.buildFeatures.buildConfig = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/main/assets")
            }
        }
    }
}

composeCompiler {
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
    stabilityConfigurationFile = rootProject.layout.projectDirectory.file("stability_config.conf")
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.androidx.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation (libs.firebase.storage)
    implementation (libs.firebase.firestore.ktx)

    // lottie files
    implementation(libs.lottie.compose)

    // type-safe navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // bottom navigation
    implementation (libs.androidx.material)

    // paging
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.paging.compose)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // icons extension
    implementation(libs.androidx.material.icons.extended)

    // gson
    implementation (libs.gson)

    // mvvm
    implementation (libs.androidx.lifecycle.viewmodel.compose)
    implementation (libs.androidx.lifecycle.livedata.ktx)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg.compose)
    implementation(libs.coil.network.okhttp)

    // animation
    implementation(libs.androidx.compose.animation)

    // this gradle needed to implement Lazy Vertical Grid inside Lazy Column with right behavior FlowLayout
    implementation (libs.accompanist.flowlayout)

    // splash screen
    implementation(libs.androidx.core.splashscreen)

    // app compat for language changer
    implementation(libs.androidx.appcompat)

    // data store
    implementation(libs.androidx.datastore.preferences)

    // location
    implementation (libs.play.services.location)

    // glance
    implementation (libs.androidx.glance.material3)
    implementation (libs.androidx.glance.appwidget)

    // work manager + coroutine
    implementation(libs.androidx.work.runtime.ktx)

    // material 3 expressive
    implementation("androidx.compose.material3:material3-android:1.4.0")

    // New modules for app_challenges
    implementation(project(":pl_coding:app_challenges:data"))
    implementation(project(":pl_coding:app_challenges:domain"))
    implementation(project(":pl_coding:app_challenges:presentation"))

    // New modules for mini_challenges
    implementation(project(":pl_coding:mini_challenges:data"))
    implementation(project(":pl_coding:mini_challenges:presentation"))

    implementation(project(":settings:presentation"))
    implementation(project(":settings:data"))

    implementation(project(":auth:presentation"))
    implementation(project(":auth:data"))

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
}