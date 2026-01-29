plugins {
    alias(libs.plugins.allinone.android.library.compose)
}

android {
    namespace = "com.example.allinone.onboarding.presentation"
}

dependencies {
    implementation(libs.foundation.pager)
    with(projects) {
        implementation(core.domain)
        implementation(core.presentation)
        implementation(onboarding.domain)
    }
}