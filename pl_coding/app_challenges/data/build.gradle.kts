plugins {
    alias(libs.plugins.allinone.android.library)
}

android {
    namespace = "com.example.allinone.pl_coding.app_challenges.data"
}

dependencies {
    with(projects) {
        implementation(core.domain)
        implementation(plCoding.appChallenges.domain)
    }
}