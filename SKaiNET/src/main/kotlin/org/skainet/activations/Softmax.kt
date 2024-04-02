package org.skainet.activations

import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import de.jugda.knanogpt.core.tensor.Tensor
import jp.co.qoncept.tensorkotlin.exp

fun softmax(tensor: Tensor, dimension: Int): Tensor {
    val exps = tensor.exp
    val sum = exps.elements.fold(0.0) { r, x -> r + x }
    return exps / sum
}

fun Tensor.softmax(dimension: Int): Tensor = softmax(this, dimension)

class Softmax(private val dimension: Int, override val name: String = "Softmax") : Module() {
    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        return softmax(input, dimension)
    }
}

