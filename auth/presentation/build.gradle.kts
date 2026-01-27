plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.auth.presentation"
}

dependencies {
    with(projects) {
        implementation(core.presentation)
        implementation(core.domain)
        implementation(auth.domain)
        implementation(core.data)
    }
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)
}