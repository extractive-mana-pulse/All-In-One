import androidx.room.gradle.RoomExtension
import com.android.build.api.dsl.ApplicationExtension
import dev.allinone.convention.ExtensionType
import dev.allinone.convention.configureAndroidCompose
import dev.allinone.convention.configureBuildTypes
import dev.allinone.convention.configureKotlinAndroid
import dev.allinone.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidAppConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(libs.findPlugin("android.application").get().get().pluginId)
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
                apply(libs.findPlugin("kotlin.compose").get().get().pluginId)
                apply(libs.findPlugin("kotlin.serialization").get().get().pluginId)
                apply(libs.findPlugin("room").get().get().pluginId)
            }

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig {
                    applicationId = libs.findVersion("projectAppId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureKotlinAndroid(this)
                configureAndroidCompose(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }
        }
    }
}