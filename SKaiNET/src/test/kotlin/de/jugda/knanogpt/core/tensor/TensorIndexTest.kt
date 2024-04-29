package de.jugda.knanogpt.core.tensor

import org.junit.Assert.assertArrayEquals

import kotlin.test.Test


class TensorIndexGetTest {

    @Test
    fun testIndexWithScalar() {
        val embeddings = Tensor(
            Shape(5, 3), doubleArrayOf(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0,
                10.0, 11.0, 12.0,
                13.0, 14.0, 15.0
            )
        )

        // Scalar index as a tensor
        val scalarIndex = Tensor(Shape(1), doubleArrayOf(2.0)) // Indexing the third row
        val result = embeddings[scalarIndex]

        // Expected output for the third row
        val expectedOutput = doubleArrayOf(7.0, 8.0, 9.0)
        assertArrayEquals(expectedOutput, result.elements, 0.01)
    }

    @Test
    fun testIndexWithVector() {
        val embeddings = Tensor(
            Shape(5, 3), doubleArrayOf(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0,
                10.0, 11.0, 12.0,
                13.0, 14.0, 15.0
            )
        )

        // Vector index as a tensor
        val vectorIndex = Tensor(Shape(3), doubleArrayOf(0.0, 2.0, 4.0))
        val result = embeddings[vectorIndex]

        // Expected output for rows 0, 2, and 4
        val expectedOutput = doubleArrayOf(1.0, 2.0, 3.0, 7.0, 8.0, 9.0, 13.0, 14.0, 15.0)
        assertArrayEquals(expectedOutput, result.elements, 0.01)
    }

    @Test
    fun testIndexWithMatrix() {
        val embeddings = Tensor(
            Shape(5, 3), doubleArrayOf(
                1.0, 2.0, 3.0,
                4.0, 5.0, 6.0,
                7.0, 8.0, 9.0,
                10.0, 11.0, 12.0,
                13.0, 14.0, 15.0
            )
        )

        // Matrix index as a tensor
        val matrixIndex = Tensor(
            Shape(2, 3), doubleArrayOf(
                0.0, 2.0, 4.0,  // First set of indices
                1.0, 3.0, 2.0   // Second set of indices
            )
        )

        // Fetch embeddings based on matrix indices
        val result = embeddings[matrixIndex]

        // Expected output, embeddings for indices in each row of matrixIndex
        val expectedOutput = doubleArrayOf(
            1.0, 2.0, 3.0, 7.0, 8.0, 9.0, 13.0, 14.0, 15.0,
            4.0, 5.0, 6.0, 10.0, 11.0, 12.0, 7.0, 8.0, 9.0
        )
        assertArrayEquals(expectedOutput, result.elements, 0.01)
        assertArrayEquals(result.shape.dimensions, intArrayOf(2, 3, 3))
    }
}
