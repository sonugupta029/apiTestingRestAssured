plugins {
    kotlin("jvm") version "1.6.10"
    java
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.rest-assured:json-path:4.4.0")
    implementation("org.testng:testng:7.4.0")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation("io.rest-assured:xml-path:4.4.0")
    testImplementation("io.rest-assured:json-schema-validator:4.4.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.test {
    dependsOn("cleanTest")
    useTestNG() {
        useDefaultListeners = true
        val systemTestNgFile = System.getProperty("testngfile")
        println("System testNg file name : $systemTestNgFile")
        val suiteFiles: String? = systemTestNgFile ?: run {
            val projectTestNgFile: String? = "/src/test/resources/testng.xml"
            println("Project testNg file name : $projectTestNgFile")
            projectTestNgFile
        }
        println("Setting test suite files : $suiteFiles")
        suites(suiteFiles)
    }
}