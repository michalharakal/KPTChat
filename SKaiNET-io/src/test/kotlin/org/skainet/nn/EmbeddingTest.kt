package org.skainet.nn

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import org.skainnet.io.named.json.JsonNamedParamsLoader
import org.skainnet.io.named.json.StreamingJsonNamedParamsLoader
import java.nio.file.Paths

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

import kotlin.test.assertTrue

class EmbeddingTest {

    private fun assertTensorEquals(expected: Tensor, actual: Tensor, epsilon: Double = 1e-9) {
        assertTrue(expected.shape.dimensions contentEquals actual.shape.dimensions, "Shape mismatch")
        assertEquals(expected.elements.size, actual.elements.size, "Size mismatch")
        for (i in expected.elements.indices) {
            assertTrue(Math.abs(expected.elements[i] - actual.elements[i]) < epsilon, "Value mismatch at index $i")
        }
    }

    @Test
    fun testEmbeddingForward() {
        val embedding = Embedding(10, 4) // 10 embeddings, each of dimension 4
        val input = Tensor(Shape(3), doubleArrayOf(0.0, 1.0, 3.0)) // Indices to retrieve embeddings for

        val output = embedding.forward(input)

        assertTrue(output.shape.dimensions contentEquals intArrayOf(3, 4), "Output shape should be (3, 4)")
    }

    @Test
    fun testEmbeddingIndexOutOfBounds() {
        val embedding = Embedding(5, 3) // 5 embeddings, each of dimension 3
        val input = Tensor(Shape(1), doubleArrayOf(5.0)) // Index out of bounds

        val exception = assertFailsWith<IllegalArgumentException> { embedding.forward(input) }
        assertEquals("Index out of bounds: 5", exception.message)
    }

    @Test
    fun `test big embeddings`() {

        val urlInput = this::class.java.classLoader.getResource("input_for_embedding.json")
        val uriInput = urlInput?.toURI() ?: throw IllegalArgumentException("File not found in resources.")
        val inputPathUri = Paths.get(uriInput)

        val urlEmbedding = this::class.java.classLoader.getResource("full_gpt_model_parameters.json")
        val uriEmbedding = urlEmbedding?.toURI() ?: throw IllegalArgumentException("File not found in resources.")
        val pathEmbedding = Paths.get(uriEmbedding)


        val inputTensors = mutableListOf<Tensor>()
        with(JsonNamedParamsLoader(inputPathUri.toFile())) {
            load { namedParameter ->
                if (namedParameter.name.contains("input_for_embedding")) {
                    inputTensors.add(namedParameter.value)
                }
            }
        }

        val embeddingTensors = mutableListOf<Tensor>()
        with(StreamingJsonNamedParamsLoader(pathEmbedding.toFile(), "token_embedding_table.weight", Shape(65, 384) ) ) {
            load { namedParameter ->
                if (namedParameter.name.contains("token_embedding_table.weight")) {
                    embeddingTensors.add(namedParameter.value)
                }
            }
        }

        // B, T, C = 64, 256, 386
        val tokenEmbeddingTable =
            from_pretrained(embeddingTensors[0])


        val tok_emb = tokenEmbeddingTable(inputTensors[0])  //# (B,T,C)

        assertEquals(3, tok_emb.shape.dimensions.size)
    }
}
