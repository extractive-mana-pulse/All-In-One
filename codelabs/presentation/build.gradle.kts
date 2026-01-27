plugins {
    alias(libs.plugins.allinone.android.library.compose)
}

android {
    namespace = "com.example.allinone.codelabs.presentation"
}

dependencies {
    with(projects) {
        implementation(core.data)
        implementation(core.domain)
        implementation(core.presentation)
        implementation(codelabs.domain)
    }
}