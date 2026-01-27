plugins {
    alias(libs.plugins.allinone.android.application)
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    id ("dagger.hilt.android.plugin")
    id("androidx.room")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.allinone"

    defaultConfig {
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
    stabilityConfigurationFiles.set(
        listOf(rootProject.layout.projectDirectory.file("stability_config.conf"))
    )
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
    implementation(libs.androidx.compose.ui.graphics)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // location
    implementation(libs.play.services.location)

    // type-safe navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // bottom navigation
    implementation (libs.androidx.material)

    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.svg.compose)

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)

    // animation
    implementation(libs.androidx.compose.animation)

    // splash screen
    implementation(libs.androidx.core.splashscreen)


    with(projects){
        with(plCoding.appChallenges){
            implementation(data)
            implementation(domain)
            implementation(presentation)
        }
        with(plCoding.miniChallenges){
            implementation(data)
            implementation(domain)
            implementation(presentation)
        }
        implementation(plCoding.presentation)
        with(auth) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(blogs) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(codelabs) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(core) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(leetcode) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(main) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(profile) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(settings) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
        with(widget) {
            implementation(presentation)
            implementation(domain)
            implementation(data)
        }
    }
    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)
}