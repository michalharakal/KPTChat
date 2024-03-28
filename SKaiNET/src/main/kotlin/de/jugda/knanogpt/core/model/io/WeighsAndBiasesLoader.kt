package de.jugda.knanogpt.core.model.io

import kotlinx.serialization.Serializable


@Serializable
data class ModelParameters(
    val parameters: Map<String, List<Double>>
)

interface WeighsAndBiasesLoader {
    fun load(): ModelParameters

}
