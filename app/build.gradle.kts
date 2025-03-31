plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.jinxin.flavor")
}

android {
    namespace = "com.example.customtheme"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.customtheme"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    flavorDimensions += "diandu"

    buildFeatures.buildConfig = true

    productFlavors {
        create("Default") {
            dimension = "diandu"
            applicationIdSuffix = ".zuoyebang"
        }
        create("Zuoyebang") {
            dimension = "diandu"
            applicationIdSuffix = ".zuoyebang"
        }
        create("ZuoyebangInk") {
            dimension = "diandu"
            applicationIdSuffix = ".zuoyebangInk"
        }
        create("ZuoyebangInk2") {
            dimension = "diandu"
            applicationIdSuffix = ".zuoyebangInk2"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

//val generateFlavorEnum = tasks.register("generateFlavorEnum") {
//    doLast {
//        val packagePath = android.namespace?.replace(".","/")
//
////        val outputDir = File("${buildDir}/generated/source/${packagePath}/flavors")
//        val outputDir = File("${buildDir}/generated/source/flavors")
//        val enumFile = File(outputDir,"FlavorsEnum.kt")
//
//        val sb: StringBuilder = StringBuilder()
//        project.android.productFlavors.forEach {
//            sb.append("${it.name}(\"${it.name}\"),")
//        }
//        sb.deleteAt(sb.length - 1).append(";")
//
//
//        val flavorValues = android.productFlavors.map { it.name.capitalize() }
//        enumFile.parentFile.mkdirs()
//        enumFile.createNewFile()
//
//        enumFile.writeText("""
//            package ${android.namespace}.flavors
//
//            /**
//             * 枚举类：表示不同的 Product Flavor,请不要修改！！！
//             */
//            enum class Flavor(val value: String) {
//                ${flavorValues.joinToString(",\n                ") { "$it(\"$it\")" }};
//
//                companion object {
//                    fun from(value: String): Flavor? = values().find { it.value == value }
//                }
//            }
//        """.trimIndent())
//    }
//}
//
////tasks.matching { it.name.startsWith("compile") && it.name.endsWith("Kotlin") }
////    .all { dependsOn("generateFlavorEnum") }
//tasks.named("preBuild") {
//    dependsOn(generateFlavorEnum)
//}

//variant.sources.kotlin!!.addGeneratedSourceDirectory(
//    project.tasks.register("${variant.name}GenerateBackendsEnum", GenerateBackendsTask::class.java) {
//        it.jniDir.set(project.layout.projectDirectory.dir("src/main/jni"))
//        it.outputFolder.set(project.layout.buildDirectory.dir("generated/backendsEnum/${variant.name}"))
//    },
//    GenerateBackendsTask::outputFolder
//)

//androidComponents {
//    onVariants { variant ->
//        val generatedDir = layout.buildDirectory.dir("generated/source/flavors").get()  // ✅ 直接使用 Provider<Directory>
//        variant.sources.java?.addGeneratedSourceDirectory(generateFlavorEnum, generatedDir)
//    }
//}