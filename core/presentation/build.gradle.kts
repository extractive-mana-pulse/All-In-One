plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.core.presentation"
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(projects.core.domain)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.datastore.preferences)
}