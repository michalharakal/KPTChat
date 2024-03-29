package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import jp.co.qoncept.tensorkotlin.Tensor

class Input(private val inputShape: Shape, val id: String = "Input") : Module() {
    override fun forward(input: Tensor): Tensor {
        println(id)
        println(input)
        println("===========")

        return input
    }
}