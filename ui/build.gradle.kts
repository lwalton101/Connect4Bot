import org.gradle.internal.os.OperatingSystem

val platform = when {
    OperatingSystem.current().isWindows -> "windows"
    OperatingSystem.current().isLinux -> "linux"
    OperatingSystem.current().isMacOsX -> "macos"
    else -> "unknown"
}

plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.1.3"
}

javafx {
    version = "26.0.1"
    modules = listOf("javafx.controls", "javafx.fxml")
}

jlink {
    imageZip = file("build/${project.name}-${project.version}-$platform.zip")

    launcher {
        name = "connect4-ui"
    }
}

application {
    mainClass = "no.lukew.connect4.ui.Main"
}