package de.jugda.simple

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.summary
import org.skainet.activations.relu
import org.skainet.dsl.network
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter

// https://www.hackster.io/news/easy-tinyml-on-esp32-and-arduino-a9dbc509f26c
class SineNN(override val name: String = "SineNN") : Module() {

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
    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = sineModule.modules

    override fun forward(input: Tensor): Tensor =
        sineModule.forward(input)
}


fun SineNN.of(value: Double): Tensor = this.forward(Tensor(Shape(1), listOf(value).toDoubleArray()))

fun SineNN.of(vararg values: Double): Tensor =
    this.forward(
        Tensor(
            Shape(values.size, 1), values
        )
    )


fun main() {
    val model = SineNN()
    println(model.summary(Shape(1), 1))
}