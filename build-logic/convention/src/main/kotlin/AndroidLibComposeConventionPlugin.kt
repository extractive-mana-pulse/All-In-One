import com.android.build.api.dsl.LibraryExtension
import dev.allinone.convention.configureAndroidCompose
import dev.allinone.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidLibComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        target.run {
            pluginManager.run {
                apply(libs.findPlugin("allinone.android.library").get().get().pluginId)
                apply(libs.findPlugin("kotlin.compose").get().get().pluginId)
            }

            val extension = extensions.getByType(LibraryExtension::class.java)
            configureAndroidCompose(extension)
        }
    }
}