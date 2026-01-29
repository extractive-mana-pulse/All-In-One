plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.profile.presentation"
}

dependencies {

    implementation(libs.coil.compose)
    implementation(libs.coil.svg.compose)
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation(libs.lottie.compose)
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)
    implementation(libs.bundles.firebase)

    with(projects) {
        implementation(core.presentation)
        implementation(core.domain)
        implementation(profile.domain)
        implementation(auth.domain)
    }
}