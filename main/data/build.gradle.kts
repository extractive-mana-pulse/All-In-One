plugins {
    alias(libs.plugins.allinone.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.bundles.hilt)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.firestore)
    ksp(libs.hilt.compiler)
    implementation(libs.gson)

    with(projects) {
        implementation(main.domain)
        implementation(core.domain)
        implementation(core.data)
    }
}