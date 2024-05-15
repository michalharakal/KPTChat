package de.jugda.knanogpt.transformer

import de.jugda.knanogpt.core.tensor.Tensor
import org.skainet.activations.relu
import org.skainet.dsl.sequential
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter

class FeedForward(
    config: TransformerConfig,
    override val name: String = "FeedForward"
) : Module() {

    private val sequential: Module

    init {
        with(config) {
            sequential = sequential {
                input(n_embd)
                linear(4 * n_embd) {
                    activation = relu

                }
                linear(n_embd)
                dropout(dropout)
            }
        }
    }

    override val params: List<NamedParameter>
        get() = sequential.params
    override val modules: List<Module>
        get() = sequential.modules

    override fun forward(input: Tensor): Tensor {
        return sequential.forward(input)
    }
}
