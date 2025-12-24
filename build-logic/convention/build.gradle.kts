plugins {
    `kotlin-dsl`
}

group = "dev.allinone.build-logic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:8.7.3")
    implementation("com.android.tools:common:31.7.3")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.10")
    implementation("androidx.room:room-gradle-plugin:2.7.2")
}

gradlePlugin {
    plugins {
        register("androidApp") {
            id = "allinone.android.application"
            implementationClass = "AndroidAppConventionPlugin"
        }
        register("androidLib") {
            id = "allinone.android.library"
            implementationClass = "AndroidLibConventionPlugin"
        }
        register("androidLibCompose") {
            id = "allinone.android.library.compose"
            implementationClass = "AndroidLibComposeConventionPlugin"
        }
        register("jvmLibrary") {
            id = "allinone.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
    }
}