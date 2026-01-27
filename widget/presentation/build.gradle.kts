plugins {
    alias(libs.plugins.allinone.android.library.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.widget.presentation"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation (libs.androidx.glance.material3)
    implementation (libs.androidx.glance.appwidget)
    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)

    with(projects){
        implementation(core.presentation)
        implementation(core.domain)
        implementation(widget.domain)
    }
}