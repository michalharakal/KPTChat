package de.jugda.knanogpt.transformer

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.broadcast.broadcastTo
import de.jugda.knanogpt.core.tensor.ext.t
import org.skainet.nn.Linear
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import org.skainet.nn.by

class BatchedLinear(
    inFeatures: Int,
    outFeatures: Int,
    override val name: String = "Linear",
    initWeights: Tensor = Tensor(
        Shape(outFeatures, inFeatures),
        List(inFeatures * outFeatures) { 0.0 }.map { it }.toDoubleArray()
    ),
    initBias: Tensor = Tensor(
        Shape(outFeatures),
        List(outFeatures) { 0.0 }.map { it }.toDoubleArray()
    )
) : Linear(inFeatures, outFeatures, name, initWeights, initBias) {

    override fun forward(input: Tensor): Tensor {
        val weight = params.by("W")!!
        val bias = params.by("B")!!

        // Assuming Tensor has a matmul (matrix multiplication) method and a plus method for addition
        val output = input.matmul(weight.value.t()) + bias.value
        return output
    }
}