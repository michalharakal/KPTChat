package de.jugda.knanogpt.core.tensor

import org.junit.Assert.*

import kotlin.test.Test
import kotlin.test.assertEquals


class FactoriesTest {
    @Test
    fun testArangeNormal() {
        val result = arange(0.0, 5.0, 1.0)
        val expectedArray = doubleArrayOf(0.0, 1.0, 2.0, 3.0, 4.0)
        assertArrayEquals("Arange with step 1.0 from 0.0 to 5.0", expectedArray, result.elements, 0.001)
    }

    @Test
    fun testArangeZeroStep() {
        try {
            arange(0.0, 5.0, 0.0)
            fail("Expected an IllegalArgumentException to be thrown")
        } catch (e: IllegalArgumentException) {
            assertEquals("Step must not be zero.", e.message)
        }
    }

    @Test
    fun testArangeNegativeStep() {
        val result = arange(5.0, 0.0, -1.0)
        val expectedArray = doubleArrayOf(5.0, 4.0, 3.0, 2.0, 1.0)
        assertArrayEquals("Arange with step -1.0 from 5.0 to 0.0", expectedArray, result.elements, 0.001)
    }

    @Test
    fun testArangeDecimalStep() {
        val result = arange(0.0, 1.0, 0.2)
        val expectedArray = doubleArrayOf(0.0, 0.2, 0.4, 0.6, 0.8)
        assertArrayEquals("Arange with step 0.2 from 0.0 to 1.0", expectedArray, result.elements, 0.001)
    }

    @Test
    fun testArangeEmptyRange() {
        val result = arange(0.0, 0.0, 1.0)
        val expectedArray = doubleArrayOf()
        assertArrayEquals("Arange with same start and end should return empty array",
            expectedArray, result.elements, 0.001)
    }
}