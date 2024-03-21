package de.jugda

import de.jugda.de.jugda.knanogpt.core.data.ResourcesDataProvider
import de.jugda.de.jugda.knanogpt.core.tensor.TrainTestSplitter
import de.jugda.de.jugda.knanogpt.core.tensor.slice
import de.jugda.knanogpt.core.tensor.Shape
import jp.co.qoncept.tensorkotlin.Tensor
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    // Load & tokenize data
    val dataProvider = ResourcesDataProvider("input.txt")
    val data = dataProvider.load()
    println(data.slice(0, 999))
    // slice data
    val splitter = TrainTestSplitter(data)
    val (train, test) = splitter.split(0.9f)
    println(train.shape.volume)
    println(test.shape.volume)
}

