package de.jugda

import de.jugda.de.jugda.knanogpt.core.data.ResourcesDataProvider
import de.jugda.de.jugda.knanogpt.core.tensor.TrainTestSplitter
import jp.co.qoncept.tensorkotlin.Tensor

fun getBatch(train: Tensor, test: Tensor, take_train: Boolean, block_size: Int) {
    val data = if (take_train) train else test
    val x = data[0..block_size]
    val y = data[1..block_size + 1]
    for (i in 0..<block_size) { // `end` is included
        val context = x[0..<i + 1]
        val target = y[i]
        println("when input is $context the target: $target")
    }
}

fun main() {
    // Step 1. Import ..

    // Step 2. Initialisierung-Block
    // TBD

    // Step 3. 4.  Load & tokenize data
    val dataProvider = ResourcesDataProvider("input.txt")
    val data = dataProvider.load()
    assert(
        data.shape.dimensions.size == 1
    ) { "`shape.dimensions.size` must be ${data.shape.dimensions.size}" }

    // Step 5. Train & Test Spilt
    val splitter = TrainTestSplitter(data)
    val (trainData, testData) = splitter.split(0.9f)
    println(trainData.shape.volume)
    println(testData.shape.volume)
    assert(
        trainData.shape.dimensions.size == 1
    ) { "`shape.dimensions.size` must be ${trainData.shape.dimensions.size}" }
    // Step 6. get batch
    val blockSize = 8
    trainData[0..<blockSize + 1]
    getBatch(trainData, testData, true, blockSize)
}

