pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Apple Music Replica"
include(":app")
include(":core:model")
include(":core:common")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":feature:library")
include(":feature:player")
include(":feature:lyrics")
