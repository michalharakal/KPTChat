package org.skainnet.io.named.json

import de.jugda.knanogpt.core.tensor.Shape
import org.skainet.nn.NamedParameter
import org.skainnet.io.named.NamedParamsLoader
import java.io.File
import java.nio.file.Files
import de.jugda.knanogpt.core.tensor.Tensor
import kotlinx.serialization.json.Json


actual fun getParamsLoader(): NamedParamsLoader = StreamingJsonNamedParamsLoader()


class StreamingJsonNamedParamsLoader(
) : NamedParamsLoader {

    override fun load(
        resourceName: String,
        propertyName: String,
        shape: Shape,
        namedParameterEvent: (NamedParameter) -> Unit
    ) {

        var captureValue = false
        var capturedValue: String? = null

        val weights = mutableListOf<Double>()
        val jsonFile: File = File(resourceName)
        Files.lines(jsonFile.toPath()).use { lines ->
            lines.forEach { line ->
                if (line.contains("\"values\"")) {
                    // Extract the value after the property name
                    //capturedValue = line.substringAfter(":").trim().trim('"', ',', '}')
                    captureValue = true
                } else if (captureValue) {
                    // Check for the end of the JSON object
                    if (line.trim().endsWith("]")) {
                        namedParameterEvent(
                            NamedParameter(
                                propertyName,
                                Tensor(
                                    shape,
                                    weights.toDoubleArray()
                                )
                            )
                        )
                        captureValue = false
                    } else {
                        val weight = line.trim().trim('"', ',', '}').toDouble()
                        weights.add(weight)
                    }
                }
            }
        }
    }
}

class JsonNamedParamsLoader(private val jsonFile: File) : NamedParamsLoader {
    override fun load(
        resourceName: String,
        propertyName: String,
        shape: Shape, namedParameterEvent: (NamedParameter) -> Unit
    ) {
        // Example: Loading JSON from a file
        var jsonString = ""
        try {
            jsonString = jsonFile.readText(Charsets.UTF_8)
        } catch (oome: OutOfMemoryError) {
            //Log the info
            System.err.println("Array size too large")
            System.err.println("Max JVM memory: " + Runtime.getRuntime().maxMemory())
        }

        // Initialize Json object
        val json = Json { ignoreUnknownKeys = true }

        // Deserialize JSON to Kotlin objects
        val tensorItems: List<TensorItem> = json.decodeFromString(jsonString)

        // Emit an event for every item
        tensorItems.forEach { tensorItem ->
            namedParameterEvent(
                NamedParameter(
                    tensorItem.unique_parameter_name,
                    Tensor(
                        Shape(*tensorItem.tensor.shape.toIntArray()),
                        tensorItem.tensor.values.toDoubleArray()
                    )
                )
            )
        }
    }
}