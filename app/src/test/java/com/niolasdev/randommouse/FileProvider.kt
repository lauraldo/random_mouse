package com.niolasdev.randommouse

import org.junit.runner.Description
import java.io.File

object FileProvider {

    private val testMap = mutableMapOf<Int, Int>()

    fun get(
        description: Description,
        directory: File,
        fileExtension: String
    ): File {
        val clazzWithoutPackage = description.className.substringAfterLast(".")
        val rootDir = File(directory.absolutePath, clazzWithoutPackage).apply(File::mkdirs)
        val filename = generateCountableOutputNameWithDescription(description)

        return File(rootDir, "$filename.$fileExtension")
    }

    private fun generateCountableOutputNameWithDescription(description: Description): String {
        val testHash = "${description.className}_${description.methodName}".hashCode()

        val counter = testMap
            .getOrPut(testHash) { INITIAL_COUNT_VALUE }
            .also(Int::inc)

        return if (counter == INITIAL_COUNT_VALUE) description.methodName else "${description.methodName}_$counter"
    }
}
private const val INITIAL_COUNT_VALUE = 1