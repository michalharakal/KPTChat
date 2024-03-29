package de.jugda.simple

import de.jugda.knanogpt.core.model.io.WeighsAndBiasesJsonLoader
import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.activations.relu
import org.skainet.dsl.network
import org.skainet.nn.Module

// https://www.hackster.io/news/easy-tinyml-on-esp32-and-arduino-a9dbc509f26c
class SineNN : Module() {

    private val sineModule = network {
        input(1)
        dense(16) {
            activation = relu
        }
        dense(16) {
            activation = relu
        }
        dense(1)
    }

    override fun forward(input: Tensor): Tensor =
        sineModule.forward(input)
}

fun SineNN.of(value: Double): Tensor = this.forward(Tensor(Shape(1), listOf(value.toFloat()).toFloatArray()))

fun main() {
    val model = SineNN()

//    val wandb = WeighsAndBiasesJsonLoader("model_weights_and_biases.json").load()

    print(model.of(2.3))
}