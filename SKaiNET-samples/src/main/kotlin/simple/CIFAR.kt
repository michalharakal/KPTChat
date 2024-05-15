package de.jugda.simple

import org.skainet.nn.Module
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.activations.relu
import org.skainet.dsl.sequential
import org.skainet.nn.NamedParameter


class CIFAR(override val name: String = "SineNN") : Module() {

    val cifar = sequential {
        input(32, 32, 3)
        /*conv2d(3,3, "layer1") {
            activation = relu
        }
        maxPool2d(2,2)
         */
        linear(16, "layer2") {
            activation = relu
        }
        linear(1, "output_layer")
    }

    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        TODO("Not yet implemented")
    }

}