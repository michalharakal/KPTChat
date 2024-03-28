package org.skainet.activations

import de.jugda.knanogpt.core.tensor.Tensor

fun relu(x: Tensor): Tensor =
    Tensor(x.shape, x.elements.map { x -> if (x > 0) x else 0f }.toFloatArray())


class ReLU : org.skainet.nn.Module() {
    override fun forward(input: Tensor): Tensor {
        return relu(input)
    }
}

val relu = ReLU()::forward

