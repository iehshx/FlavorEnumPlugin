import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
plugins {
    `java-gradle-plugin`
    alias(libs.plugins.kotlin.jvm)
    id("com.gradle.plugin-publish") version "1.3.1" // 插件发布插件
}
//
//plugins {
//    `java-gradle-plugin`
//    alias(libs.plugins.kotlin.jvm)
//}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

//tasks.withType<KotlinCompile>().configureEach {
//    kotlinOptions {
//        freeCompilerArgs += "-Xskip-metadata-version-check"
//    }
//}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs += "-Xskip-metadata-version-check"
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin.api)
    implementation(gradleKotlinDsl())
}
group = "com.jinxin.flavor"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("flavorEnum") {
            id = "com.jinxin.flavor"
            implementationClass = "com.jinxin.flavor.FlavorEnumPlugin"
            displayName = "Flavor Enum Plugin"
            description = "根据productFlavors生成FlavorEnum枚举"
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("flavorEnumPlugin") {
            from(components["java"]) // 将插件组件打包
            groupId = "com.jinxin.flavor" // 插件发布的 groupId
            artifactId = "flavorEnumPlugin" // 插件发布的 artifactId
            version = "1.0.0" // 插件的版本
        }
    }
    repositories {
        maven {
            name = "GradlePluginPortal"
//            url = uri("https://plugins.gradle.org/m2/") // Gradle Plugin Portal 仓库 URL
            url = uri("file://${project.rootDir}/localMaven") // Gradle Plugin Portal 仓库 URL
        }
    }
}