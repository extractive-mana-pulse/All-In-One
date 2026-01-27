plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.presentation"
}

dependencies {
    with(projects) {
        implementation(core.presentation)
        implementation(core.domain)
        implementation(main.domain)
    }
    implementation(libs.gson)
    implementation(libs.coil.compose)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
}