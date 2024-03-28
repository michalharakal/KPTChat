package de.jugda.knanogpt.core.tensor.ext

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor

fun Tensor.t(): Tensor {
    // Ensure the tensor is 2D
    if (this.shape.dimensions.size != 2) {
        throw IllegalArgumentException("Transpose is only implemented for 2D tensors.")
    }

    // New shape with dimensions swapped
    val newShape = Shape(this.shape.dimensions[1], this.shape.dimensions[0])

    // Create a new elements array to hold the transposed elements
    val newElements = FloatArray(this.elements.size)

    // Populate the new elements array with the transposed elements
    for (i in 0 until shape.dimensions[0]) { // Original rows
        for (j in 0 until shape.dimensions[1]) { // Original columns
            // Calculate the index in the original flat array and the new index in the transposed array
            val originalIndex = i * shape.dimensions[1] + j
            val newIndex = j * shape.dimensions[0] + i
            // Assign the transposed value
            newElements[newIndex] = this.elements[originalIndex]
        }
    }

    // Return a new tensor with the transposed shape and elements
    return Tensor(newShape, newElements)
}
