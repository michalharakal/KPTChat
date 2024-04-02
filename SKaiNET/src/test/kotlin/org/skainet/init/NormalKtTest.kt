package org.skainet.init

import org.skainet.utils.calculateMean
import org.skainet.utils.calculateVariance

import de.jugda.knanogpt.core.tensor.Shape
import kotlin.test.Test

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NormalInitTest {
    @Test
    fun testNormalInit() {
        val normalTensor = normalInit(Shape(3), mu = 0.0, sigma = 1.0)
        val elements = normalTensor.elements
        val expectedOutput = doubleArrayOf(
            0.0, 0.0, 0.0
        )

        assertContentEquals(expectedOutput, elements)
    }

    @Test
    fun testNormalInitWith100Items() {
        val shape = Shape(100)
        val tensor = normalInit(shape, mu = 0.0, sigma = 1.0)

        // Check if the tensor has exactly 100 elements
        assertEquals(100, tensor.elements.size, "Tensor should have 100 elements")

        // Check for randomness (this is a very basic check)
        val firstElement = tensor.elements[0]
        var allSame = true
        for (element in tensor.elements) {
            if (element != firstElement) {
                allSame = false
                break
            }
        }

        assertFalse(allSame, "Elements should not all be the same")

        // Calculate mean and variance
        val mean = calculateMean(tensor.elements)
        val variance = calculateVariance(tensor.elements, mean)

        // Check mean and variance
        assertTrue(Math.abs(mean) < 0.1, "Mean should be close to 0")
        assertTrue(Math.abs(variance - 1.0) < 0.3, "Variance should be close to 1")
    }
}