package org.skainet.tensor

import org.skainet.activations.relu
import kotlin.test.assertEquals


import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.init.normalInit
import kotlin.test.Test

import kotlin.test.assertContentEquals

class TensorMatmulTest {

    @Test
    fun `test multiplication of 3d with 2 d`() {
        val shape3d = Shape(64, 256, 384)
        val shape2d = Shape(65, 384)
        val tensor3d = normalInit(shape3d)
        val tensor2d = normalInit(shape2d)
        val result = tensor3d.matmul(tensor2d)
        assertEquals(result.shape, Shape(64, 256, 65), "ReLU should correctly apply to 3D tensor with mixed values")
    }

}

