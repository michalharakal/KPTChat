package de.jugda.knanogpt.core.tensor.broadcast

import kotlin.test.Test
import kotlin.test.assertEquals


import de.jugda.knanogpt.core.tensor.Shape

class BroadcastUtilsKtTest {

    @Test
    fun `test broadcastShapes with different dimensions`() {

        val shape1 = Shape(2, 3)
        val shape2 = Shape(1, 3)
        //val shape3 = Shape(1, 1, 1)
        val resultShape = broadcastShapes(listOf(shape1, shape2))//, shape3))
        assertEquals(resultShape, Shape(1, 2, 3))
    }

    @Test
    fun `test broadcastShapes with the same dimensions`() {

        val shape1 = Shape(64, 256, 384)
        val shape2 = Shape(65, 384)

        val shapes = listOf(shape1, shape2)

        val resultShape = broadcastShapes(shapes)

        assertEquals(Shape(64, 256, 65), resultShape)
    }

    @Test
    fun `test broadcastShapes with 2 and 3 dimensions`() {

        val shape1 = Shape(64, 256, 384)
        val shape2 = Shape(65, 384)

        val shapes = listOf(shape1, shape2)

        val resultShape = broadcastShapes(shapes)

        assertEquals(Shape(64, 256, 65), resultShape)
    }
}