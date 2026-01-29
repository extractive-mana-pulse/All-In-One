plugins {
    alias(libs.plugins.allinone.android.library)
}

android {
    namespace = "com.example.allinone.onboarding.data"
}

dependencies {
    implementation(projects.onboarding.domain)
}