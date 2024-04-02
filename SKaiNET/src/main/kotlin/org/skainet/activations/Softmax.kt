package org.skainet.activations

import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import de.jugda.knanogpt.core.tensor.Tensor

fun softmax(tensor: Tensor): Tensor {
    val expValues = DoubleArray(tensor.elements.size) { i -> Math.exp(tensor.elements[i]) }
    val sumOfExpValues = expValues.sum()
    val softmaxValues = DoubleArray(tensor.elements.size) { i -> expValues[i] / sumOfExpValues }
    return Tensor(tensor.shape, softmaxValues)

}

class Softmax(override val name: String = "Softmax") : Module() {
    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        return softmax(input)
    }
}

