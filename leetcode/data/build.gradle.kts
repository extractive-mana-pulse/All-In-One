plugins {
    alias(libs.plugins.allinone.android.library)
}

android {
    namespace = "com.example.allinone.leetcode.data"
}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(leetcode.domain)
    }
}