package org.skainet

import org.skainet.nn.NamedParameter
import org.skainnet.io.named.json.JsonNamedParamsLoader
import java.io.File
import java.nio.file.Paths

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class TensorsAdditionTest {

    private fun readFromResources(fileName: String): File {
        val url = TensorsAdditionTest::class.java.classLoader.getResource(fileName)
        val uri = url?.toURI() ?: throw IllegalArgumentException("File not found in resources.")

        return Paths.get(uri).toFile()
    }

    private fun areDoublesEqual(a: Double, b: Double, epsilon: Double = 0.0001): Boolean {
        return Math.abs(a - b) < epsilon
    }


    fun assertDoubleArraysEqual(expected: DoubleArray, actual: DoubleArray, epsilon: Double = 0.0001) {
        assertEquals(
            expected.size,
            actual.size,
            "Arrays have different sizes: expected ${expected.size}, but was ${actual.size}"
        )
        for (i in expected.indices) {
            assertTrue(
                areDoublesEqual(expected[i], actual[i], epsilon),
                "Arrays differ at index $i: expected ${expected[i]}, but was ${actual[i]}"
            )
        }
    }


    @Test
    fun `test embeddings tensors addition from nanGPT lecture`() {
        val params = mutableMapOf<String, NamedParameter>()
        JsonNamedParamsLoader(readFromResources("pos_embedding.json")).load { namedParameter ->
            params[namedParameter.name] = namedParameter
        }
        JsonNamedParamsLoader(readFromResources("token_embedding.json")).load { namedParameter ->
            params[namedParameter.name] = namedParameter
        }

        JsonNamedParamsLoader(readFromResources("token_plus_pos_embedding.json")).load { namedParameter ->
            params[namedParameter.name] = namedParameter
        }
        val posEmbedding = params["pos_embedding"]!!.value
        val tokenEmbedding = params["token_embedding"]!!.value
        val realTokenPosEmbedding = params["tok_emb + pos_emb"]!!.value


        val calcTokenPos = posEmbedding + tokenEmbedding
        assertDoubleArraysEqual(realTokenPosEmbedding.elements, calcTokenPos.elements)
        assertContentEquals(realTokenPosEmbedding.shape.dimensions, calcTokenPos.shape.dimensions)
    }
}