package de.jugda.knanogpt.core.data

import org.junit.Assert.*

import de.jugda.de.jugda.knanogpt.core.CharTokenizer
import de.jugda.de.jugda.knanogpt.core.data.ResourcesDataProvider
import kotlin.test.Test
import kotlin.test.assertEquals


class CharTokenizerTest {

    @Test
    fun `encode test`() {
        val provider = ResourcesDataProvider("input.txt")
        val tensor = provider.load()
        assertEquals(1, tensor.shape.dimensions.size)
    }
}