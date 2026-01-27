plugins {
    alias(libs.plugins.allinone.android.library)
}

android {
    namespace = "com.example.allinone.profile.data"
}

dependencies {

    implementation(libs.kotlinx.coroutines.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    implementation(libs.androidx.credentials)
    implementation(libs.credentials.play.services.auth)
    implementation(libs.googleid)

    implementation (libs.firebase.storage)
    implementation (libs.firebase.firestore.ktx)

    with(projects) {
        implementation(core.data)
        implementation(core.domain)
        implementation(core.presentation)
        implementation(profile.domain)
    }
}