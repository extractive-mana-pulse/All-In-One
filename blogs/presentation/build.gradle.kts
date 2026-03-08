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
    implementation(libs.androidx.xr.compose.material3.material3)
}