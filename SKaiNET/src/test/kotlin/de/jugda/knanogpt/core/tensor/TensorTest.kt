package de.jugda.knanogpt.core.tensor

import kotlin.test.assertContentEquals
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


class TensorTest {
    @Test
    fun matmulOfScalars() {
        val scalar1 = Tensor(Shape(), floatArrayOf(3f))
        val scalar2 = Tensor(Shape(), floatArrayOf(4f))
        val result = scalar1.matmul(scalar2)
        assertContentEquals(floatArrayOf(12f), result.elements)
    }

    @Test
    fun matmulOfScalarAndVector() {
        val scalar = Tensor(Shape(), floatArrayOf(2f))
        val vector = Tensor(Shape(3), floatArrayOf(1f, 2f, 3f))
        val result = scalar.matmul(vector)
        assertContentEquals(floatArrayOf(2f, 4f, 6f), result.elements)
    }

    @Test
    fun matmulOfVectorAndMatrix() {
        val vector = Tensor(Shape(2), floatArrayOf(1f, 2f))
        val matrix = Tensor(Shape(2, 3), floatArrayOf(1f, 2f, 3f, 4f, 5f, 6f))
        val result = vector.matmul(matrix)
        assertContentEquals(floatArrayOf(9f, 12f, 15f), result.elements)
    }

    @Test
    fun matmulOfVectorSingleAndMatrix() {
        val vector = Tensor(Shape(1), floatArrayOf(2f))
        val matrix = Tensor(Shape(1, 3), floatArrayOf(1f, 2f, 3f))
        val result = vector.matmul(matrix)
        assertContentEquals(floatArrayOf(2f, 4f, 6f), result.elements)
    }


    @Test
    fun matmulOfMatrixAndMatrix() {
        val matrix1 = Tensor(Shape(2, 3), floatArrayOf(1f, 2f, 3f, 4f, 5f, 6f))
        val matrix2 = Tensor(Shape(3, 2), floatArrayOf(7f, 8f, 9f, 10f, 11f, 12f))
        val result = matrix1.matmul(matrix2)
        assertContentEquals(floatArrayOf(58f, 64f, 139f, 154f), result.elements)
    }

    @Test
    fun matmulOfMatrixAndMatrixWithWrongShaoe() {
        val matrix1 = Tensor(Shape(3, 2), floatArrayOf(1f, 2f, 3f, 4f, 5f, 6f))
        val matrix2 = Tensor(Shape(3, 2), floatArrayOf(7f, 8f, 9f, 10f, 11f, 12f))
        val exception = assertFailsWith<IllegalArgumentException> { matrix1.matmul(matrix2) }
        assertEquals("Shapes do not align.", exception.message)
    }

}
