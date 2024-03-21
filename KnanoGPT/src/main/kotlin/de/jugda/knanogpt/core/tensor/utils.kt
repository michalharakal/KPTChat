package de.jugda.de.jugda.knanogpt.core.tensor

import de.jugda.knanogpt.core.tensor.Shape
import jp.co.qoncept.tensorkotlin.Tensor

fun Tensor.slice(from: Int, to: Int): Tensor =
    Tensor(Shape(1, to - from), elements.slice(from..to).toFloatArray())

class TrainTestSplitter(private val data: Tensor) {
    fun split(factor: Float): Pair<Tensor, Tensor> {
        val n = (factor * data.shape.volume).toInt()
        val trainData = Tensor(Shape(1, n), data.elements.slice(0..n).toFloatArray())
        val valData =
            Tensor(Shape(1, data.shape.volume - n), data.elements.slice(n..<data.shape.volume).toFloatArray())
        return Pair(trainData, valData)
    }
}