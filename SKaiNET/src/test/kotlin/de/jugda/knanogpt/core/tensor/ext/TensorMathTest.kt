package de.jugda.knanogpt.core.tensor.ext

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.junit.jupiter.api.assertThrows

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TensorTest {

    @Test
    fun testTransposeOnScalar() {
        val scalarTensor = Tensor(Shape(), floatArrayOf(5.0f))
        val exception = assertFailsWith<IllegalArgumentException> { scalarTensor.t() }
        assertEquals("Transpose is only implemented for 2D tensors.", exception.message)
    }

    @Test
    fun testTransposeOn1D() {
        val vectorTensor = Tensor(Shape(3), floatArrayOf(1.0f, 2.0f, 3.0f))
        val exception = assertFailsWith<IllegalArgumentException> { vectorTensor.t() }
        assertEquals("Transpose is only implemented for 2D tensors.", exception.message)
    }

    @Test
    fun testTransposeOn2D() {
        val matrixTensor = Tensor(Shape(2, 3), floatArrayOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f))
        val transposedTensor = matrixTensor.t()
        assertEquals(Shape(3, 2), transposedTensor.shape)
        assertContentEquals(floatArrayOf(1.0f, 4.0f, 2.0f, 5.0f, 3.0f, 6.0f), transposedTensor.elements)
    }
}
