plugins {
    alias(libs.plugins.allinone.android.library)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.allinone.calendar.data"
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences)

    implementation(libs.bundles.hilt)
    ksp(libs.bundles.hilt.compiler)

    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    implementation(projects.calendar.domain)
}