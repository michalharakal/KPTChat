package org.skainet.tensor

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.broadcast.slices
import kotlin.test.Test
import kotlin.test.assertContentEquals

class TensorSlicing {

    @Test
    fun `slice vector 1`() {
        val tensor = Tensor(Shape(3), doubleArrayOf(1.0, 2.0, 3.0))
        val slice = slices(tensor) {
            // from second to the last
            slice {
                from(1) to (-1)
            }
        }
        val select = tensor.get(*slice.toTypedArray())
        val expected = doubleArrayOf(2.0, 3.0)
        assertContentEquals(
            select.shape.dimensions,
            Shape(2).dimensions,
            "Sliced vector has only 2 elements"
        )
        assertContentEquals(
            select.elements,
            expected
        )
    }

    @Test
    fun `slice vector all with up`() {
        val tensor = Tensor(Shape(3), doubleArrayOf(1.0, 2.0, 3.0))
        val slice = slices(tensor) {
            // from second to the last
            slice {
                up(-1)
            }
        }
        val select = tensor.get(*slice.toTypedArray())
        val expected = doubleArrayOf(1.0, 2.0, 3.0)
        assertContentEquals(
            select.shape.dimensions,
            Shape(3).dimensions,
            "Sliced vector has only 2 elements"
        )
        assertContentEquals(
            select.elements,
            expected
        )
    }

    @Test
    fun `slice vector with all`() {
        val tensor = Tensor(Shape(3), doubleArrayOf(1.0, 2.0, 3.0))
        val slice = slices(tensor) {
            // from second to the last
            slice {
                all()
            }
        }
        val select = tensor.get(*slice.toTypedArray())
        val expected = doubleArrayOf(1.0, 2.0, 3.0)
        assertContentEquals(
            select.shape.dimensions,
            Shape(3).dimensions,
            "Sliced vector has only 2 elements"
        )
        assertContentEquals(
            select.elements,
            expected
        )
    }

    @Test
    fun `slice matrix 1`() {
        val tensor = Tensor(
            Shape(3, 3),
            doubleArrayOf(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0)
        )
        val slice = slices(tensor) {
            // from second to the last
            slice {
                from(1) to (-1)
            }
            slice {
                all()
            }
        }
        val select = tensor.get(*slice.toTypedArray())
        val expected = doubleArrayOf(2.0, 3.0, 5.0, 6.0, 8.0, 9.0)
        assertContentEquals(
            select.shape.dimensions,
            Shape(2, 3).dimensions,
            "Sliced vector has only 2 elements"
        )
        assertContentEquals(
            select.elements,
            expected
        )
    }
}
