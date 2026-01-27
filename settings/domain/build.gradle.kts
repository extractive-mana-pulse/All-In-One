plugins {
    alias(libs.plugins.allinone.jvm.library)
}

dependencies {
    implementation(libs.gson)
    implementation(libs.kotlinx.coroutines.core)
    implementation(projects.core.domain)
}