rootProject.name = "connect-4"

include("core")
include("cli")
include("ui")

project(":core").name = "connect4-core"
project(":cli").name = "connect4-cli"
project(":ui").name = "connect4-ui"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}
