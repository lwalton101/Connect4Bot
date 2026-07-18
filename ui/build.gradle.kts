plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

javafx {
    version = "26.0.1"
    modules = listOf("javafx.controls")
}

application {
    mainClass = "no.lukew.connect4.ui.Main"
}