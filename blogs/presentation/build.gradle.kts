plugins {
    alias(libs.plugins.allinone.android.library.compose)
}

android {
    namespace = "com.example.presentation"
}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(core.presentation)
    }
    implementation(libs.androidx.compose.material3)
}