plugins {
    application
    id("com.gradleup.shadow") version "9.0.0"
}

dependencies {
    implementation(project(":connect4-core"))
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
    mainClass = "no.lukew.connect4.cli.Main"
}

tasks.shadowJar {
    archiveBaseName.set("connect4-cli")
    archiveVersion.set(project.version.toString())

    manifest {
        attributes(
            "Main-Class" to "no.lukew.connect4.cli.Main"
        )
    }
}