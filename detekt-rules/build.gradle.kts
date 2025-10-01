plugins {
    kotlin("jvm")
}

dependencies {
    compileOnly("io.gitlab.arturbosch.detekt:detekt-api:1.23.6")

    testImplementation("io.gitlab.arturbosch.detekt:detekt-test:1.23.6")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("junit:junit:4.13.2")
}

kotlin {
    jvmToolchain(11)
}

