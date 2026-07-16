rootProject.name = "connect-4"

include("core")
include("cli")

project(":core").name = "connect4-core"
project(":cli").name = "connect4-cli"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}