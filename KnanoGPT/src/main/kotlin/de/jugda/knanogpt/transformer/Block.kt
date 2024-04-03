package de.jugda.de.jugda.knanogpt.transformer

import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.transformer.TransformerConfig
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import org.skainet.nn.LayerNorm
import de.jugda.knanogpt.transformer.MultiHeadAttention


class Block(
    config: TransformerConfig,
    override val name: String = "Block"
) : Module() {

    private val sa: Module
    private val ffwd: Module
    private val ln1: Module
    private val ln2: Module

    init {
        with(config) {
            sa = MultiHeadAttention(
                config.copy(
                    head_size = n_embd,
                    n_embd = n_embd,
                    num_heads = num_heads,
                    dropout = dropout
                )
            )
            ffwd = FeedForward(config)
            ln1 = LayerNorm(n_embd)
            ln2 = LayerNorm(n_embd)

        }
    }

    override val params: List<NamedParameter>
        get() = emptyList()
    override val modules: List<Module>
        get() = listOf(
            sa,
            ffwd,
            ln1,
            ln2
        )

    override fun forward(input: Tensor): Tensor {
        val x = input + sa(ln1(input))
        return x + ffwd(ln2(x))
    }
}
