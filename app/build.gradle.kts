@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.internal.api.BaseVariantOutputImpl

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("io.gitlab.arturbosch.detekt").version("1.22.0")
}

android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.radiusagent.assignment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            isDefault = true
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            isCrunchPngs = false
            enableUnitTestCoverage = true
            extra["enableCrashlytics"] = false
            extra["alwaysUpdateBuildId"] = false
            buildConfigField(
                "String",
                "BASE_URL",
                project.properties["base_url_development"].toString()
            )
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            versionNameSuffix = "-${System.currentTimeMillis()}"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            applicationVariants.all {
                outputs.forEach { output ->
                    if (output is BaseVariantOutputImpl) {
                        output.outputFileName =
                            "earn_money.$name-v$versionName.${output.outputFile.extension}"
                    }
                }
            }
            buildConfigField(
                "String",
                "BASE_URL",
                project.properties["base_url_production"].toString()
            )
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
        kotlinOptions {
            jvmTarget = "17"
        }
        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                freeCompilerArgs = freeCompilerArgs + "-Xopt-in=kotlin.RequiresOptIn"
            }
        }
        buildFeatures {
            buildConfig = true
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.4.7"
        }
        packaging {
            resources.excludes.addAll(
                listOf(
                    "META-INF/DEPENDENCIES",
                    "META-INF/AL2.0",
                    "META-INF/LGPL2.1"
                )
            )
        }
        namespace = "com.radiusagent.assignment"
    }

    dependencies {
        implementation("androidx.core:core-ktx:1.10.1")
        implementation("androidx.appcompat:appcompat:1.6.1")

        implementation(platform("androidx.compose:compose-bom:2023.05.01"))
        androidTestImplementation(platform("androidx.compose:compose-bom:2023.05.01"))
        implementation("androidx.compose.material3:material3")
        implementation("androidx.compose.ui:ui")
        implementation("androidx.compose.ui:ui-util")
        implementation("androidx.compose.ui:ui-tooling-preview")
        debugImplementation("androidx.compose.ui:ui-tooling")
        androidTestImplementation("androidx.compose.ui:ui-test-junit4")
        debugImplementation("androidx.compose.ui:ui-test-manifest")
        implementation("androidx.compose.foundation:foundation:1.4.3")
        implementation("androidx.activity:activity-compose:1.7.2")
        implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
        implementation("com.google.accompanist:accompanist-systemuicontroller:0.31.3-beta")

        implementation("androidx.navigation:navigation-compose:2.6.0")
        implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.14.0")
        kapt("com.squareup.moshi:moshi-kotlin-codegen:1.14.0")
        implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
        implementation("com.squareup.okhttp3:okhttp")
        implementation("com.squareup.okhttp3:logging-interceptor")
        testImplementation("com.squareup.okhttp3:mockwebserver:4.10.0")

        implementation("androidx.room:room-runtime:2.5.2")
        implementation("androidx.room:room-ktx:2.5.2")
        kapt("androidx.room:room-compiler:2.5.2")
        implementation("net.zetetic:android-database-sqlcipher:4.5.3")

        implementation("androidx.datastore:datastore-preferences:1.0.0")

        implementation("com.jakewharton.timber:timber:5.0.1")

        implementation("com.google.dagger:hilt-android:2.44")
        kapt("com.google.dagger:hilt-android-compiler:2.44")

        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0")
        detektPlugins("ru.kode:detekt-rules-compose:1.2.2")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    }
}

detekt {
    buildUponDefaultConfig = true
    allRules = false
    autoCorrect = true
    config = files("$projectDir/config/detekt.yml")
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        html.required.set(true)
        xml.required.set(false)
        txt.required.set(false)
        sarif.required.set(false)
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
    jvmTarget = "1.8"
}
tasks.withType<Test> { useJUnitPlatform() }

tasks {
    create<Copy>(name = "InstallGitHook") {
        this.group = "help"
        this.from("${rootProject.rootDir}/scripts/")
            .into("${rootProject.rootDir}/.git/hooks/").eachFile {
                fileMode = 1100000111
            }
    }
}

tasks.getByPath(":app:preBuild").dependsOn("InstallGitHook")