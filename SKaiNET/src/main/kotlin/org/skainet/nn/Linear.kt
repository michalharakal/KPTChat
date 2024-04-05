package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.ext.t

class Linear(inFeatures: Int, outFeatures: Int, override val name: String = "Linear") :
    Module() {
    private val bias: NamedParameter = NamedParameter("bias",
        Tensor(
            Shape(outFeatures),
            List(outFeatures) { 0.0 }.map { it }.toDoubleArray()
        )
    )
    private val weight: NamedParameter = NamedParameter("weight",
        Tensor(
            Shape(outFeatures, inFeatures),
            List(inFeatures * outFeatures) { 0.0 }.map { it }.toDoubleArray()
        )
    )
    override val params: List<NamedParameter>
        get() = listOf(weight, bias)


    override val modules: List<Module>
        get() = emptyList()


    override fun forward(input: Tensor): Tensor {
        val weight = params.by("W")!!
        val bias = params.by("B")!!

        // Assuming Tensor has a matmul (matrix multiplication) method and a plus method for addition
        val output = input.matmul(weight.value.t()) + bias.value
        return output
    }
}