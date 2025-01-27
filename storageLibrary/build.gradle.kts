plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

apply {
    from("../lib-version.gradle")
}
android {
    namespace = "com.arnab.storage"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        /** Customized filename for generated aar file */
        val versionName = "${project.ext["major"]}.${project.ext["minor"]}.${project.ext["build"]}"
        setProperty("archivesBaseName", "storageLibrary-v$versionName")
    }
    lint {
        targetSdk = 35
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    publishing {
        // Publishes "fullRelease" build variant with "fullRelease" component created by
        // Android Gradle plugin
        singleVariant("release")
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.arnab.storage"
                artifactId = "Storage"
                version = "${project.ext["major"]}.${project.ext["minor"]}.${project.ext["build"]}"
                pom {
                    name.set("Android library for Storage system")
                    description.set("Useful library to help Android developers to work with Files in Android 10 and higher version")
                }
                afterEvaluate {
                    from(components["release"])
                }
            }
        }
    }
}