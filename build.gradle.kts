import com.netflix.gradle.plugins.rpm.Rpm

plugins {
    id("application")
    id("com.github.sherter.google-java-format") version "0.8"
    id("nebula.ospackage") version "6.1.1" apply false
    id("com.google.cloud.tools.jib") version "1.8.0"
}

apply(plugin = "nebula.rpm")

repositories {
    jcenter()
}

group = "com.johnathangilday"
version = "0.0.7"

application {
    mainClassName = "com.johnathangilday.App"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

jib.to.image = "gilday/how-to-microservice"

val jacksonVersion = "2.9.8"
val jerseyVersion = "2.28"
val jettyVersion = "9.4.12.v20180830"
val junitVersion = "5.4.0"

dependencies {

    // To avoid compiler warnings about @API annotations in JUnit code.
    implementation("org.apiguardian:apiguardian-api:1.0.0")

    // jackson
    implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:$jacksonVersion")
    // jersey
    implementation("org.glassfish.jersey.core:jersey-server:$jerseyVersion")
    implementation("org.glassfish.jersey.inject:jersey-hk2:$jerseyVersion")
    implementation("org.glassfish.jersey.containers:jersey-container-jetty-http:$jerseyVersion")
    implementation("org.glassfish.jersey.media:jersey-media-json-jackson:$jerseyVersion") {
        exclude("com.fasterxml.jackson.core")
    }
    // jetty
    runtimeOnly("org.eclipse.jetty:jetty-server:$jettyVersion")
    // slf4j
    implementation("org.slf4j:slf4j-api:1.7.22")
    runtimeOnly("ch.qos.logback:logback-classic:1.1.3")
    // typesafe config
    implementation("com.typesafe:config:1.3.1")

    // jaxb (no longer in JDK)
    implementation("org.glassfish.jaxb:jaxb-runtime:2.4.0-b180830.0438")

    // test
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.assertj:assertj-core:3.6.1")
    testImplementation("org.mockito:mockito-all:1.10.19")
    testImplementation("org.glassfish.jersey.test-framework.providers:jersey-test-framework-provider-jdk-http:$jerseyVersion") {
        exclude("javax.servlet")
        exclude("junit")
    }
    testImplementation("org.glassfish.jersey.containers:jersey-container-jdk-http:$jerseyVersion")
}

tasks.compileJava {
    // include parameter name information in the class files for Jackson to use at runtime
    options.compilerArgs = listOf("-parameters")
}

tasks.test {
    useJUnitPlatform()
}

val distTar = tasks.named<Tar>("distTar")

val rpm by tasks.registering(Rpm::class) {
    dependsOn(distTar)

    packageName = project.name
    archiveVersion.set(project.version.toString())
    release = "1.e7"
    os = org.redline_rpm.header.Os.LINUX

    requires("java-11-openjdk")

    preInstall(file("packaging/add-user.sh"))
    postInstall("systemctl preset ${project.name} > /dev/null 2>&1")
    preUninstall("""
        |systemctl disable ${project.name} > /dev/null 2>&1
        |systemctl stop ${project.name} > /dev/null 2>&1
    """.trimMargin())
    postUninstall("systemctl daemon-reload > /dev/null 2>&1")

    val tarFile = distTar.get().archiveFile // tarTree doesn't support a Provider<RegularFile>
    val zipTree = tarTree(tarFile)
    from(zipTree) {
        into("/opt")
        eachFile {
            // strip version from destination directory
            path = path.replaceFirst("${project.name}-${project.version}", project.name)
        }
        exclude("**/*.bat") // exclude Windows files
        user = "howtomicroservice"
        permissionGroup = "howtomicroservice"
    }

    from("packaging") {
        into("/etc/systemd/system")
        include("*.service")
        addParentDirs = false
        expand(project.properties)
        user = "root"
        permissionGroup = "root"
        fileMode = 0x1A4 // octal 0644
    }

    from("packaging") {
        into("/etc/${project.name}")
        include("settings.conf")
        user = "howtomicroservice"
        permissionGroup = "howtomicroservice"
        fileMode = 0x1A4 // octal 0644
    }

    from("packaging") {
        into("/etc/sysconfig")
        include(project.name)
        user = "root"
        permissionGroup = "root"
        fileMode = 0x1A4 // octal 0644
    }

    link("/opt/${project.name}/settings.conf", "/etc/${project.name}/settings.conf")
}
