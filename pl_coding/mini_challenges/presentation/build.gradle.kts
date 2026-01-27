plugins {
    alias(libs.plugins.allinone.android.library.compose)
}

android {
    namespace = "com.example.allinone.pl_coding.mini_challenges.presentation"
}

dependencies {
    // material 3 expressive
    implementation(libs.androidx.compose.material3.android)
    implementation(libs.material3)
    with(projects) {
        implementation(plCoding.miniChallenges.domain)
        implementation(core.presentation)
        implementation(core.domain)
    }
}