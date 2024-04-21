package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import kotlin.random.Random

/**
 * A simple lookup table that stores embeddings of a fixed dictionary and size.
 *
 *     This module is often used to store word embeddings and retrieve them using indices.
 *     The input to the module is a list of indices, and the output is the corresponding
 *     word embeddings.
 *
 *     Args:
 *         num_embeddings (int): size of the dictionary of embeddings
 *         embedding_dim (int): the size of each embedding vector
 */
class Embedding(
    private val numEmbeddings: Int,
    private val embeddingDim: Int,
    private val random: Random = Random.Default,
    override val name: String = "Embedding"
) : Module() {
    private val embeddings: Array<DoubleArray> =
        Array(numEmbeddings) { DoubleArray(embeddingDim) { random.nextDouble() - 0.5 } }
    override val params: List<NamedParameter>
        get() = listOf(
            NamedParameter("numEmbeddings", numEmbeddings.toTensor()),
            NamedParameter("embeddingDim", embeddingDim.toTensor())
        )
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        // Flatten the input tensor to work with indices
        val flatInput = input.elements.map { it.toInt() }
        val outputElements = DoubleArray(flatInput.size * embeddingDim)

        for ((index, value) in flatInput.withIndex()) {
            if (value !in 0 until numEmbeddings) throw IllegalArgumentException("Index out of bounds: $value")
            System.arraycopy(embeddings[value], 0, outputElements, index * embeddingDim, embeddingDim)
        }

        return Tensor(Shape(flatInput.size, embeddingDim), outputElements)
    }
}

private fun Int.toTensor(): Tensor = Tensor(Shape(), doubleArrayOf(this.toDouble()))
