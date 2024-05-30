package de.jugda.knanogpt.core.tensor

import de.jugda.knanogpt.core.tensor.broadcast.broadcastOuterTensors
import de.jugda.knanogpt.core.tensor.ext.matrices
import de.jugda.knanogpt.core.tensor.ext.plus
import de.jugda.knanogpt.core.tensor.ext.view
import java.io.Serializable

public infix fun Tensor.matmulKMath(other: Tensor): Tensor {
    if (shape.dimensions.size == 1 && other.shape.dimensions.size == 1) {
        return Tensor(Shape(1), times(other).sum())
    }

    var penultimateDim = false
    var lastDim = false

    var newThis = this
    var newOther = other

    if (shape.dimensions.size == 1) {
        penultimateDim = true
        newThis = newThis.view(Shape(1) + shape)
    }

    if (other.shape.dimensions.size == 1) {
        lastDim = true
        newOther = newOther.view(Shape(*(other.shape.dimensions.toList() + intArrayOf(1).toList()).toIntArray()))
    }

    val broadcastTensors = broadcastOuterTensors(newThis, newOther)
    newThis = broadcastTensors[0]
    newOther = broadcastTensors[1]

    val l = newThis.shape.dimensions[newThis.shape.dimensions.size - 2]
    val m1 = newThis.shape.dimensions[newThis.shape.dimensions.size - 1]
    val m2 = newOther.shape.dimensions[newOther.shape.dimensions.size - 2]
    val n = newOther.shape.dimensions[newOther.shape.dimensions.size - 1]
    check(m1 == m2) {
        "Tensors dot operation dimension mismatch: ($l, $m1) x ($m2, $n)"
    }

    val resShape =
        newThis.shape.slice(0..(newThis.shape.dimensions.size - 2)) + Shape(*intArrayOf(newOther.shape.dimensions.last()))
    val resSize = resShape.volume
    val resTensor = Tensor(resShape, DoubleArray(resSize))

    val resMatrices = resTensor.matrices
    val newThisMatrices = newThis.matrices
    val newOtherMatrices = newOther.matrices

    for (i in resMatrices.indices) {
        dotTo(newThisMatrices[i], newOtherMatrices[i], resMatrices[i], l, m1, n)
    }

    return if (penultimateDim) {
        resTensor.view(resTensor.shape.first(resTensor.shape.dimensions.size - 2) + Shape(resTensor.shape.dimensions.last()))
    } else if (lastDim) {
        resTensor.view(resTensor.shape.first(resTensor.shape.dimensions.size - 1))
    } else {
        resTensor
    }
}

private fun Shape.slice(range: IntRange): Shape = Shape(*dimensions.sliceArray(range))


internal fun dotTo(
    a: Tensor,
    b: Tensor,
    res: Tensor,
    l: Int, m: Int, n: Int,
) {
    val aBuffer = a.elements
    val bBuffer = b.elements
    val resBuffer = res.elements

    for (i in 0 until l) {
        for (j in 0 until n) {
            var curr = 0.0
            for (k in 0 until m) {
                curr += aBuffer[i * m + k] * bBuffer[k * n + j]
            }
            resBuffer[i * n + j] = curr
        }
    }
}
