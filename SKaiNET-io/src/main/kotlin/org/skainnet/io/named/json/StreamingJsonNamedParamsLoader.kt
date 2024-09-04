package org.skainnet.io.named.json

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.nn.NamedParameter
import org.skainnet.io.named.NamedParamsLoader
import java.io.File
import java.nio.file.Files

class StreamingJsonNamedParamsLoader(
    private val jsonFile: File,
    private val propertyName: String,
    private val shape: Shape
) : NamedParamsLoader {
    override fun load(namedParameterEvent: (NamedParameter) -> Unit) {
        var captureValue = false
        var capturedValue: String? = null

        val weights = mutableListOf<Double>()
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