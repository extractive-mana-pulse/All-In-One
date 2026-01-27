plugins {
    alias(libs.plugins.allinone.android.library.compose)
}

android {
    namespace = "com.example.allinone.pl_coding.presentation"
}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(core.presentation)
    }
}