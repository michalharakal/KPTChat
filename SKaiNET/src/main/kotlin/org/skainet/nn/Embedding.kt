package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor

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
    override val name: String = "Embedding",
    private val weights: Tensor = Tensor(
        Shape(numEmbeddings, embeddingDim),
        List(numEmbeddings * embeddingDim) { 0.0 }.map { it }.toDoubleArray()
    )
) : Module() {
    override val params: List<NamedParameter>
        get() = listOf(
            NamedParameter("weight", weights)
        )
    override val modules: List<Module>
        get() = emptyList()

    override fun forward(input: Tensor): Tensor {
        val weight = params.by("weight")!!
        return weight.value[input]
    }
}

fun from_pretrained(
    weights: Tensor
): Embedding {
    assert(weights.shape.dimensions.size == 2)
    return Embedding(weights.shape.dimensions[0], weights.shape.dimensions[0], weights = weights)
}