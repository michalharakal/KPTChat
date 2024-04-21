package de.jugda.knanogpt.core.tensor.ext

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import jp.co.qoncept.tensorkotlin.conv2d

import kotlin.test.Test
import kotlin.test.assertContentEquals


class TensorConv2dTest {
    @Test
    fun testConv2dSimple() {
        val inputTensor = Tensor(
            Shape(1, 4, 4), doubleArrayOf(
                1.0, 2.0, 3.0, 4.0,
                5.0, 6.0, 7.0, 8.0,
                9.0, 10.0, 11.0, 12.0,
                13.0, 14.0, 15.0, 16.0
            )
        )

        val kernelTensor = Tensor(
            Shape(1, 2, 2), doubleArrayOf(
                1.0, 0.0,
                0.0, -1.0
            )
        )

        val expectedOutput = Tensor(
            Shape(1, 3, 3), doubleArrayOf(
                4.0, 4.0, 4.0,
                8.0, 8.0, 8.0,
                12.0, 12.0, 12.0
            )
        )

        val inChannels = kernelTensor.shape.dimensions[0]
        val outChannels = inputTensor.shape.dimensions[0]

        val outputTensor = inputTensor.conv2d(
            inChannels = inChannels,
            outChannels = outChannels,
            kernel = kernelTensor,
            stride = 1,
            padding = 0,
            bias = false
        )

        assertContentEquals(outputTensor.elements, expectedOutput.elements)
        // Check if the elements in the output tensor match the expected values
        assertContentEquals(
            outputTensor.shape.dimensions,
            expectedOutput.shape.dimensions,
            "Output tensor elements do not match the expected values."
        )
    }
}