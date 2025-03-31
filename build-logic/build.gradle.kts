buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal() // 确保 Kotlin 插件可用
    }
//    dependencies {
//        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.32") // 适配 Gradle 6.8.3
//    }
}

//plugins {
//    `kotlin-dsl`
//}
//tasks.withType<Wrapper> {
//    gradleVersion = "6.8.3"
//}