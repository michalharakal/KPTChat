package org.skainnet.io.named.json

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import kotlinx.io.Source
import kotlinx.io.readByteArray
import kotlinx.serialization.json.Json
import org.skainet.nn.NamedParameter
import org.skainnet.io.named.NamedParamsLoader

class JsonNamedParamsLoader() : NamedParamsLoader {
    override fun load(
        source: Source,
        propertyName: String,
        shape: Shape, namedParameterEvent: (NamedParameter) -> Unit
    ) {
        // Example: Loading JSON from a file
        val tensorItems: List<TensorItem> = try {
            val jsonString = source.readByteArray().toString()
            // Initialize Json object
            val json = Json { ignoreUnknownKeys = true }

            // Deserialize JSON to Kotlin objects
            json.decodeFromString(jsonString)


        } catch (_: OutOfMemoryError) {
            emptyList()
        }


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