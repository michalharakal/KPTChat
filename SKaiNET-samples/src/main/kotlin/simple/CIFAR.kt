package de.jugda.simple

import org.skainet.nn.Module
import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.Summary
import org.skainet.summary
import org.skainet.activations.relu
import org.skainet.dsl.network
import org.skainet.nn.NamedParameter


class CIFAR(override val name: String = "SineNN") : Module() {

    val cifar = network {
        input(32, 32, 3)
        conv2d(3,3, "layer1") {
            activation = relu
        }
        dense(16, "layer2") {
            activation = relu
        }
        dense(1, "output_layer")
    }

    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        TODO("Not yet implemented")
    }

}