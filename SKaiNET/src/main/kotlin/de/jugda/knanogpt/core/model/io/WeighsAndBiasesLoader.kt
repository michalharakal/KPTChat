package de.jugda.knanogpt.core.model.io

import org.skainet.nn.NamedParameter


data class ModelParameters(
    val parameters: List<NamedParameter>
)

interface WeighsAndBiasesLoader {
    fun load(): ModelParameters

}
