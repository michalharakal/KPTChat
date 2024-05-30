package de.jugda

import de.jugda.knanogpt.gpt.GPTLanguageModel
import de.jugda.knanogpt.core.data.BatchProvider
import de.jugda.knanogpt.core.data.ResourcesDataProvider
import de.jugda.knanogpt.core.tensor.Tensor
import de.jugda.knanogpt.core.tensor.TrainTestSplitter
import de.jugda.knanogpt.transformer.TransformerConfig
import org.skainnet.io.named.json.JsonNamedParamsLoader
import java.nio.file.Paths


fun main() {
    // Step 1. Import ..

    // Step 1.1. Read the data
    val urlEmbedding = JsonNamedParamsLoader::class.java.classLoader.getResource("full_gpt_model_parameters.json")
    val uriEmbedding = urlEmbedding?.toURI() ?: throw IllegalArgumentException("File not found in resources.")
    val pathEmbedding = Paths.get(uriEmbedding)


    val embeddingTensors = mutableListOf<Tensor>()
    with(JsonNamedParamsLoader(pathEmbedding.toFile())) {
        load { namedParameter ->
            if (namedParameter.name.contains("token_embedding_table.weight")) {
                embeddingTensors.add(namedParameter.value)
            }
        }
    }

    println(embeddingTensors[0].shape)

    // Step 2. Initialisierung-Block
    //how many independent sequences will we process in parallel?
    val batchSize = 64
    // what is the maximum context length for predictions?
    val blockSize = 256


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
    val batch = BatchProvider(trainData, testData, blockSize, batchSize)
    val (xb, yb) = batch.getRandomBatch(true)

    print("inputs:")
    print(xb.shape)
    print(xb)
    print("targets:")
    print(yb.shape)
    print(yb)

    repeat(batchSize) { b ->
        repeat(blockSize) { t ->
            val context = xb[b..b, 0..t]
            val target = yb[b, t]
            println("when input is $context the target: $target")
        }
    }

    val model = GPTLanguageModel(
        TransformerConfig(
            head_size = 64,
            n_embd = 384,
            num_heads = 8,
            dropout = 0.2,
            vocab_size = dataProvider.vocabSize.toInt(),
            block_size = 256,
            n_layer = 8
        ), "GPT"
    )
    val y =  model.forward(xb)
    print(y.shape)
}
