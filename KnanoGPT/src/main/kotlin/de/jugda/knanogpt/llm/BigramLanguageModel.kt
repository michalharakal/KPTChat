package de.jugda.de.jugda.knanogpt.llm


import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.transformer.TransformerConfig
import jp.co.qoncept.tensorkotlin.Shape
import org.skainet.nn.Module
import org.skainet.nn.NamedParameter
import org.skainet.nn.Embedding


class Block(
    config: TransformerConfig,
    override val name: String = "Block"
) : Module() {
    private val token_embedding_table: Module

    init {
        with(config) {
            // each token directly reads off the logits for the next token from a lookup table
            token_embedding_table = Embedding(vocab_size, vocab_size)
        }

    }

    override val params: List<NamedParameter>
        get() = TODO("Not yet implemented")
    override val modules: List<Module>
        get() = listOf(token_embedding_table)

    override fun forward(input: Tensor): Tensor {

        //# idx and targets are both (B,T) tensor of integers
        return token_embedding_table(input) // # (B,T,C)
    }

    fun generate(input: Tensor, maxNewTokens: Int): Tensor {
        val idx = input
        val targets = input
        val B = idx.shape.dimensions[0]
        val T = idx.shape.dimensions[1]
        val C = idx.shape.dimensions[2]
        val newTokens = mutableListOf<Int>()
        for (i in 0 until maxNewTokens) {
            val logits = forward(idx)
            val nextToken = targets.elements[i].toInt()
            newTokens.add(nextToken)
        }
        return Tensor(Shape(*intArrayOf(B, maxNewTokens)), newTokens.map { it.toDouble() }.toDoubleArray())
    }
}
