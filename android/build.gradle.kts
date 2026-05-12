import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.testing.Test

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

group = "io.flutter.plugins.sharedpreferences"
version = "1.0-SNAPSHOT"

repositories {
        google()
        mavenCentral()
    }

allprojects {
    gradle.projectsEvaluated {
        tasks.withType<JavaCompile> {
            options.compilerArgs.addAll(listOf("-Xlint:unchecked", "-Xlint:deprecation"))
        }
    }
}


configure<LibraryExtension> {
    namespace = "io.flutter.plugins.sharedpreferences"
    compileSdk = 37

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    sourceSets {
        getByName("main") {
            java.directories.add("src/main/kotlin")
        }
    }

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    lint {
        targetSdk = 37
        checkAllWarnings = true
        warningsAsErrors = true
        disable.addAll(listOf("AndroidGradlePluginVersion", "InvalidPackage", "GradleDependency", "NewerVersionAvailable"))
        baseline = file("lint-baseline.xml")
    }

    testOptions {
        targetSdk = 37
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
            all { test: Test ->
                test.outputs.upToDateWhen { false }
                test.testLogging {
                   events("passed", "skipped", "failed", "standardOut", "standardError")
                   showStandardStreams = true
                }
            }
        }
    }
}

 dependencies {
        implementation("androidx.datastore:datastore:1.2.1")
        implementation("androidx.datastore:datastore-preferences:1.2.1")
        implementation("androidx.preference:preference:1.2.1")
        testImplementation("junit:junit:4.13.2")
        testImplementation("androidx.test:core-ktx:1.18.0")
        testImplementation("androidx.test.ext:junit-ktx:1.3.0")
        testImplementation("org.robolectric:robolectric:4.16")
        testImplementation("org.mockito:mockito-inline:5.2.0")
        testImplementation("io.mockk:mockk:1.14.9")
    }
