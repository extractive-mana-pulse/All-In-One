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
        implementation(auth.domain)
        implementation(core.domain)
        implementation(main.domain)
    }
    implementation(libs.gson)
    implementation(libs.coil.compose)
    implementation(libs.bundles.hilt)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    ksp(libs.hilt.compiler)
}