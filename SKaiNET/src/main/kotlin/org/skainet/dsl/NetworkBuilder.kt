package org.skainet.dsl

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.nn.Input
import org.skainet.Dense
import org.skainet.topologies.FeedForwardNetwork
import org.skainet.nn.Module

// DSL Marker to restrict the DSL to its intended scope
@DslMarker
private annotation class NetworkDsl

@NetworkDsl
fun network(content: NETWORK.() -> Unit) = NetworkImpl()
    .apply(content)
    .create()

@NetworkDsl
interface NetworkDslItem {
}

@NetworkDsl
interface NETWORK : NetworkDslItem {
    fun input(inputSize: Int, id: String = "")

    fun dense(inputDimension: Int, outputDimension: Int, id: String = "", content: DENSE.() -> Unit = {})
}

@NetworkDsl
interface DENSE : NetworkDslItem {
    var activation: (Tensor) -> Tensor
}

private fun getDefaultName(id: String, s: String, size: Int): String {
    if (id.isNotEmpty()) return id
    return "$s-$size"
}


class DenseImpl(private val inputDimension: Int, private val outputDimension: Int, private val id: String) : DENSE {

    private var _activation: (Tensor) -> Tensor = { tensor -> tensor }
    override var activation: (Tensor) -> Tensor
        get() = _activation
        set(value) {
            _activation = value
        }

    fun create(): List<Module> {
        return listOf(
            Dense(inputDimension, outputDimension, id),
        )
    }
}

private class NetworkImpl : NETWORK {

    val modules = mutableListOf<Module>()

    fun create() = NetworkBuilder().add(*modules.toTypedArray()).build()
    override fun input(inputSize: Int, id: String) {
        modules.add(Input(Shape(inputSize), id = getDefaultName(id, "Input", modules.size)))
    }

    override fun dense(inputDimension: Int, outputDimension: Int, id: String, content: DENSE.() -> Unit) {
        val impl = DenseImpl(
            inputDimension = inputDimension,
            outputDimension = outputDimension,
            id = getDefaultName(id, "Linear", modules.size)
        )
        impl.content()
        // dense layer consinst from linear module and activation function module (2 modules)
        modules += impl.create()
    }
}


@NetworkDsl
class NetworkBuilder {
    private val modules = mutableListOf<Module>()

    fun add(vararg modules: Module): NetworkBuilder {
        this.modules += modules.toList()
        return this
    }

    fun build(): FeedForwardNetwork = FeedForwardNetwork(*modules.toTypedArray())
}
