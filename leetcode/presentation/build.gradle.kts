plugins {
    alias(libs.plugins.allinone.android.library.compose )
}

android {
    namespace = "com.example.allinone.leetcode.presentation"

}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(leetcode.domain)
        implementation(core.presentation)
    }
}