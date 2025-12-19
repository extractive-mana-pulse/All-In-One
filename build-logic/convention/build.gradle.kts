plugins {
    `kotlin-dsl`
}

group = "com.allinone.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "allinone.android.application"
            implementationClass = "id.AndroidAppConventionPlugin"
        }

        register("androidLibrary") {
            id = "allinone.android.library"
            implementationClass = "id.AndroidLibConventionPlugin"
        }

        register("androidLibraryCompose") {
            id = "allinone.android.library.compose"
            implementationClass = "id.AndroidLibComposeConventionPlugin"
        }

        register("jvmLibrary") {
            id = "allinone.jvm.library"
            implementationClass = "id.JvmLibraryConventionPlugin"
        }
    }
}
