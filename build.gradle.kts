plugins {
    id("java")
}

allprojects {
    group = "no.lukew.connect4"
    version = "0.1.0"
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

