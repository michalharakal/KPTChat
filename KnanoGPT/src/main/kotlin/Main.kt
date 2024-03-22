package de.jugda

import de.jugda.de.jugda.knanogpt.core.data.ResourcesDataProvider
import de.jugda.de.jugda.knanogpt.core.tensor.TrainTestSplitter

fun main() {
    // Step 1. Import ..

    // Step 2. Initialisierung-Block
    // TBD

    // Step 3. 4.  Load & tokenize data
    val dataProvider = ResourcesDataProvider("input.txt")
    val data = dataProvider.load()
    // Step 5. Train & Test Spilt
    val splitter = TrainTestSplitter(data)
    val (train, test) = splitter.split(0.9f)
    println(train.shape.volume)
    println(test.shape.volume)
}

