package com.jinxin.flavor

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

class FlavorEnumPlugin : Plugin<Project> {

    abstract class GenerateFlavorEnumTask : DefaultTask() {

        @get:OutputDirectory
        abstract val outputDirectory: DirectoryProperty

        @TaskAction
        fun generateEnum() {
            outputDirectory.get().asFile.mkdirs()
            project.extensions.findByType(ApplicationExtension::class.java)?.let {
                // 获取flavors
                val flavorValues = it.productFlavors?.map {
                    it.name.replaceFirstChar { it.uppercaseChar() }
                } ?: emptyList()
                // 生成 FlavorEnum.kt 文件
                val enumFile = File(outputDirectory.get().asFile, "FlavorEnum.kt")
                enumFile.writeText(
                    """
            package ${it.namespace}

            enum class Flavor(val value: String) {
                UnKnow("UnKnow"),
                ${flavorValues.joinToString(",\n                ") { "$it(\"$it\")" }};
                
                companion object {
                    fun from(value: String): Flavor = values().find { it.value.equals(value, ignoreCase = true) } ?: UnKnow
                }
            }
        """.trimIndent()
                )
            }

        }
    }


    override fun apply(project: Project) {
        // 注册生成枚举的任务
        // 使用 AndroidComponentsExtension 来访问 productFlavors
        project.plugins.withId("com.android.application") {
            val androidComponents =
                project.extensions.getByType(AndroidComponentsExtension::class.java)
            val currentTask = project.gradle.startParameter.taskNames.firstOrNull()
            val matchResult = Regex("assemble(\\w+)(Debug|Release)").find(currentTask ?: "")
            val currentFlavor = matchResult?.groups?.get(1)?.value ?: ""
            val currentBuildType = matchResult?.groups?.get(2)?.value?.lowercase() ?: ""
            val applicationExtension = project.extensions.findByType(ApplicationExtension::class.java)
//            val nameSpace =
//                project.extensions.findByType(ApplicationExtension::class.java)?.namespace
//                    ?: throw IllegalArgumentException("?????")

            // 确保有 productFlavors 配置
            androidComponents.beforeVariants { variant ->
                // 将生成的目录添加到 Android 构建配置
                if (variant.flavorName == currentFlavor && variant.buildType == currentBuildType) {
                    val packagePath = (applicationExtension?.namespace ?: "com.jinxin.flavor").replace(".","/")
                    val taskName = "generateFlavorEnum"
                    val generateFlavorEnum: TaskProvider<GenerateFlavorEnumTask> =
                        project.tasks.register(
                            taskName,
                            GenerateFlavorEnumTask::class.java
                        ) { task ->
                            task.outputDirectory.set(project.layout.buildDirectory.dir("generated/source/buildConfig/${currentFlavor}/${currentBuildType}/${packagePath}"))
                        }
//                    generateZuoyebangDebugBuildConfig
//                    project.tasks.named("preBuild") {

                    project.afterEvaluate {
                        project.tasks.named("generate${currentFlavor}${currentBuildType.replaceFirstChar { it.uppercaseChar() }}BuildConfig").configure {
                            it.finalizedBy(generateFlavorEnum)
                        }

                        project.tasks.named("compile${currentFlavor}${currentBuildType.replaceFirstChar { it.uppercaseChar() }}Kotlin").configure {
                            it.dependsOn(generateFlavorEnum)  // 确保在 compileZuoyebangDebugKotlin 执行之前，generateFlavorEnum 会执行
                        }
                    }
                }
            }
        }

    }

}

