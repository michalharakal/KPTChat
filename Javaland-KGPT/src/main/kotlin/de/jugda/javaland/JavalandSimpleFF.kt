package de.jugda.javaland

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.Shape
import org.skainet.nn.Linear
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import org.skainet.summary

class Netz : Module() {
    private var input: Module = Linear(1, 1)
    private var layer1: Linear = Linear(1, 16)
    private var layer2: Linear = Linear(16, 16)
    private var output: Linear = Linear(16, 1)

    override val name: String
        get() = "Netz"

    override val params: List<NamedParameter>
        get() = emptyList()

    override val modules: List<Module>
        get() = listOf(input, layer1, layer2, output)

    override fun forward(input: Tensor): Tensor =
        modules.fold(input) { a, x -> x.forward(a) }
}

fun main() {
    val netz = Netz()
    val x = Tensor(Shape(1), doubleArrayOf(1.0))
    print(netz.summary(x.shape))
}
