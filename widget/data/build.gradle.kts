plugins {
    alias(libs.plugins.allinone.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.widget.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    with(projects) {
        implementation(core.data)
        implementation(core.domain)
        implementation(widget.domain)
    }
}