package de.jugda.knanogpt.core.model.io

import kotlinx.serialization.json.Json
import java.io.BufferedReader

class WeighsAndBiasesJsonLoader(private val resourceName: String) : WeighsAndBiasesLoader {

    private fun loadResourceText(): String {
        // Use the current thread's context class loader to find the resource
        val inputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(resourceName)
        return inputStream!!.bufferedReader().use(BufferedReader::readText)
    }

    override fun load(): ModelParameters {
        val jsonContent = loadResourceText()
        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString<ModelParameters>(jsonContent)
    }
}