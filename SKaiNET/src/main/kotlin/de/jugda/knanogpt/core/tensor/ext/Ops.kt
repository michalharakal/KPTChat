package de.jugda.knanogpt.core.tensor.ext

import de.jugda.knanogpt.core.tensor.Shape
import de.jugda.knanogpt.core.tensor.Tensor
import kotlin.random.Random

fun DoubleArray.scan(initial: Double, operation: (acc: Double, Double) -> Double): List<Double> {
    val result = ArrayList<Double>(size + 1)
    result.add(initial)
    for (element in this) result.add(operation(result.last(), element))
    return result
}

fun Tensor.multinomial(numSamples: Int, replacement: Boolean = false): Tensor {
    val probabilities = elements
    val resultShape = Shape(numSamples)
    val resultElements = DoubleArray(numSamples)

    val cumulativeProbs = probabilities.scan(0.0) { acc, p -> acc + p }.drop(1).toDoubleArray()
    val random = Random.Default

    if (replacement) {
        for (i in 0 until numSamples) {
            val r = random.nextDouble(0.0, 1.0)
            val idx = cumulativeProbs.indexOfFirst { it > r }
            resultElements[i] = idx.toDouble()
        }
    } else {
        val availableIndices = probabilities.indices.toMutableList()
        for (i in 0 until numSamples) {
            val totalProb = availableIndices.sumByDouble { probabilities[it] }
            val r = random.nextDouble(0.0, totalProb)
            var cumulative = 0.0
            val selectedIndex = availableIndices.first { idx ->
                cumulative += probabilities[idx]
                cumulative >= r
            }
            resultElements[i] = selectedIndex.toDouble()
            availableIndices.remove(selectedIndex)
        }
    }

    return Tensor(resultShape, resultElements)}
