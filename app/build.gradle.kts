import com.google.protobuf.gradle.*

plugins {
    id("com.android.application")
//    id("dagger.hilt.android.plugin")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf") version "0.9.4"
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    kotlin("android")
}

android {
    compileSdk = 34
    defaultConfig {
        applicationId = "com.jkuester.unlauncher"
        minSdk = 21
        targetSdk = 34
        versionName = "2.1.0"
        versionCode = 19
        vectorDrawables { useSupportLibrary = true }
//        signingConfigs {
//            if (project.extra.has("RELEASE_STORE_FILE")) {
//                register("release") {
//                    storeFile = file(project.extra["RELEASE_STORE_FILE"] as String)
//                    storePassword = project.extra["RELEASE_STORE_PASSWORD"] as String
//                    keyAlias = project.extra["RELEASE_KEY_ALIAS"] as String
//                    keyPassword = project.extra["RELEASE_KEY_PASSWORD"] as String
//                }
//            }
//        }
    }

    buildTypes {
        named("release").configure {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
//            signingConfig = signingConfigs.maybeCreate("release")
        }
        named("debug").configure {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    buildFeatures {
        this.viewBinding = true
    }
    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    namespace = "com.sduduzog.slimlauncher"
    applicationVariants.all{
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName = "${applicationId}.apk"
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Kotlin Libraries
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.10")

    // Support Libraries
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.datastore:datastore:1.0.0")
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("com.google.protobuf:protobuf-javalite:3.10.0")

    // Arch Components
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.10-1.0.13")
    ksp("androidx.room:room-compiler:2.6.0")

    //3rd party libs
    implementation("com.intuit.sdp:sdp-android:1.1.0")
    implementation("com.intuit.ssp:ssp-android:1.1.0")
    implementation("com.google.dagger:hilt-android-compiler:2.48.1")
    implementation("com.google.dagger:hilt-android:2.48.1")
    ksp("com.google.dagger:dagger-compiler:2.48.1")
    ksp("com.google.dagger:hilt-compiler:2.48.1")
//    ksp("com.google.dagger:hilt-android:2.48.1")
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.24.4"
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}