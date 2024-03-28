package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.ext.t
import jp.co.qoncept.tensorkotlin.Tensor
import jp.co.qoncept.tensorkotlin.pow

class Linear(inFeatures: Int, outFeatures: Int, val name: String) : Module() {
    var weight: Tensor = Tensor(
        Shape(inFeatures, outFeatures),
        List(inFeatures * outFeatures) { 0 }.map { it.toFloat() }.toFloatArray()
    )
    var bias: Tensor = Tensor(
        Shape(outFeatures),
        List(outFeatures) { 0 }.map { it.toFloat() }.toFloatArray()
    )

    override fun forward(input: Tensor): Tensor {
        println(name)
        println("inp = $input")
        println("weight = $weight")
        println("===========")

        val trans = weight.t()
        // Assuming Tensor has a matmul (matrix multiplication) method and a plus method for addition
        val output = input.matmul(weight) + bias
        return output
    }
}