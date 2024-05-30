package de.jugda.knanogpt.core.tensor.broadcast

import kotlin.math.max

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.stack

internal fun broadcastShapes(shapes: List<Shape>): Shape {
    var totalDim = 0
    for (shape in shapes) {
        totalDim = max(totalDim, shape.dimensions.size)
    }

    val totalShape = IntArray(totalDim) { 0 }
    for (shape in shapes) {
        for (i in shape.dimensions.indices) {
            val curDim = shape.dimensions[i]
            val offset = totalDim - shape.dimensions.size
            totalShape[i + offset] = max(totalShape[i + offset], curDim)
        }
    }

    for (shape in shapes) {
        for (i in shape.dimensions.indices) {
            val curDim = shape.dimensions[i]
            val offset = totalDim - shape.dimensions.size
            check(curDim == 1 || totalShape[i + offset] == curDim) {
                "Shapes are not compatible and cannot be broadcast"
            }
        }
    }

    return Shape(*totalShape)
}


fun broadcastTo(tensor: Tensor, newShape: Shape): Tensor {
    val tensors = List(newShape.dimensions[0]) {
        tensor
    }
    return stack(tensors)
}
