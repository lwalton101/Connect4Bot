plugins {
    id("java")
}

allprojects {
    group = "no.lukew.connect4"
    version = "0.2.0-prerelease"
}
subprojects {
    apply(plugin = "java")

    repositories {
        mavenCentral()
    }
    tasks.test {
        useJUnitPlatform()
    }

}

