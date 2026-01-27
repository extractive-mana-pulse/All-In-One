pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AllInOne"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":auth:data")
include(":auth:domain")
include(":auth:presentation")
include(":settings:data")
include(":settings:domain")
include(":settings:presentation")
include(":core:presentation")
include(":core:domain")
include(":core:data")
include(":codelabs:presentation")
include(":codelabs:domain")
include(":codelabs:data")
include(":widget:data")
include(":widget:domain")
include(":widget:presentation")
include(":pl_coding:mini_challenges:data")
include(":pl_coding:mini_challenges:domain")
include(":pl_coding:mini_challenges:presentation")
include(":pl_coding:app_challenges:data")
include(":pl_coding:app_challenges:domain")
include(":pl_coding:app_challenges:presentation")
include(":pl_coding:presentation")
include(":profile:data")
include(":profile:domain")
include(":profile:presentation")
include(":main:data")
include(":main:domain")
include(":main:presentation")
include(":blogs:presentation")
include(":blogs:domain")
include(":blogs:data")
include(":leetcode:data")
include(":leetcode:domain")
include(":leetcode:presentation")