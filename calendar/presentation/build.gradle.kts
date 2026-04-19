plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.calendar.presentation"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.compose.material3)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)

    with(projects) {
        implementation(calendar.domain)
        implementation(calendar.data)
        implementation(core.presentation)
    }
}