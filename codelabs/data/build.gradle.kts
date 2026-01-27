plugins {
    alias(libs.plugins.allinone.android.library)
}

android {
    namespace = "com.example.allinone.codelabs.data"

}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(codelabs.domain)
    }
}