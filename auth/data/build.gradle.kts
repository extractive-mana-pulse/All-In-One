plugins {
    alias(libs.plugins.allinone.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.auth.data"
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.androidx.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation (libs.firebase.storage)
    implementation (libs.firebase.firestore.ktx)
    implementation(libs.play.services.auth)

    with(projects) {
        implementation(core.presentation)
        implementation(core.domain)
        implementation(core.data)
        implementation(auth.domain)
    }
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)
}