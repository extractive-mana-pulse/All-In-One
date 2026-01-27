plugins {
    alias(libs.plugins.allinone.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.settings.data"
    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"https://api.sunrisesunset.io/\"")
        }
        release {
            /* this 2 block of code, prepare application for release.
             Basically optimize code and remove unused elements. */
            buildConfigField("String", "BASE_URL", "\"https://api.sunrisesunset.io/\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(core.presentation)
        implementation(settings.domain)
    }

    // hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    // data store
    implementation(libs.androidx.datastore.preferences)

    // location
    implementation(libs.play.services.location)

    // app compat
    implementation(libs.androidx.appcompat)
}